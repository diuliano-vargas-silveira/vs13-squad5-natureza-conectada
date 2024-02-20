package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ClienteRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.ClienteRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ServiceCliente {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder encoder;

    public ClienteResponseDTO adicionar(ClienteRequestDTO clienteCreateDTO) throws Exception {
        var cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        cliente.setSenha(encoder.encode(clienteCreateDTO.getSenha()));
        cliente.setAtivo(Ativo.A);
        cliente.setTipoUsuario(TipoUsuario.CLIENTE);
        clienteRepository.save(cliente);

        return objectMapper.convertValue(cliente, ClienteResponseDTO.class);
    }

    public ClienteResponseDTO editar(Integer idCliente, ClienteRequestDTO clienteEditado) throws Exception {

        var clienteEncontrado = clienteRepository.getById(idCliente);


        clienteEncontrado.setNome(clienteEditado.getNome());
        clienteEncontrado.setEmail(clienteEditado.getEmail());
        clienteEncontrado.setSenha(encoder.encode(clienteEditado.getSenha()));

        clienteRepository.save(clienteEncontrado);

        return objectMapper.convertValue(clienteEncontrado, ClienteResponseDTO.class);
    }

    public void remover(Integer idCliente) throws Exception {
        var clienteEncontrado = clienteRepository.getById(idCliente);
        if (clienteEncontrado.getAtivo() == Ativo.A)
            clienteEncontrado.setAtivo(Ativo.D);

        clienteRepository.save(clienteEncontrado);
    }

    public Page<ClienteResponseDTO> listarTodos(Pageable paginacao) throws Exception {
        var clientesPaginados = clienteRepository.findAll(paginacao);

        return clientesPaginados.map(cliente -> objectMapper.convertValue(cliente, ClienteResponseDTO.class));
    }

    public ClienteResponseDTO procurarPorId(Integer id) throws Exception {
        var clienteEncontrado = clienteRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Cliente não existe"));


        return objectMapper.convertValue(clienteEncontrado, ClienteResponseDTO.class);
    }


    public ClienteResponseDTO procurarClienteAtivo(Integer idCliente) throws RegraDeNegocioException {
        var clienteEncontrado = clienteRepository.getById(idCliente);

        if (clienteEncontrado.getAtivo() != Ativo.A)
            throw new RegraDeNegocioException("Cliente inativo");

        return objectMapper.convertValue(clienteEncontrado, ClienteResponseDTO.class);
    }

    public List<ClienteResponseDTO> listarClientesAtivos() {
        return usuarioRepository.findAllUsuariosAtivos()
                .stream().filter(cliente -> cliente.getTipoUsuario().equals(TipoUsuario.CLIENTE)).map(usuarios -> objectMapper.convertValue(usuarios, ClienteResponseDTO.class))
                .collect(Collectors.toList());
    }

    public Cliente buscarPorIdEntidade(Integer id) throws Exception {
        Cliente cliente = this.clienteRepository.findByAtivoAndId(Ativo.A, id).orElseThrow(() -> new RegraDeNegocioException("Não foi possível buscar usuario"));
        return cliente;
    }

    public Cliente getUsuarioLogado() throws Exception {
        Integer id = getIdLoggedUser();
        Cliente cliente = buscarPorIdEntidade(id);
        return cliente;
    }

    public Integer getIdLoggedUser() {
        Integer findUserId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return findUserId;
    }
}
