package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ClienteCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ServiceCliente  {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    private final PasswordEncoder encoder;



    public ClienteDTO adicionar(ClienteCreateDTO clienteCreateDTO) throws Exception {
        var cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        cliente.setSenha(encoder.encode(clienteCreateDTO.getSenha()));
        cliente.setAtivo(Ativo.A);
        cliente.setTipoUsuario(TipoUsuario.CLIENTE);
        clienteRepository.save(cliente);

        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }

    public ClienteDTO editar(Integer idCliente, ClienteCreateDTO clienteEditado) throws Exception {

        var clienteEncontrado = clienteRepository.getById(idCliente);


        clienteEncontrado.setNome(clienteEditado.getNome());
        clienteEncontrado.setEmail(clienteEditado.getEmail());
        clienteEncontrado.setSenha(encoder.encode(clienteEditado.getSenha()));

        clienteRepository.save(clienteEncontrado);

        return objectMapper.convertValue(clienteEncontrado, ClienteDTO.class);
    }

    public void remover(Integer idCliente) throws Exception {
        var clienteEncontrado = clienteRepository.getById(idCliente);
        if (clienteEncontrado.getAtivo() == Ativo.A)
            clienteEncontrado.setAtivo(Ativo.D);
    }

    public Page<ClienteDTO> listarTodos(Pageable paginacao) throws Exception {
        var clientesPaginados = clienteRepository.findAll(paginacao);

        return clientesPaginados.map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class));
    }

    public ClienteDTO procurarPorId(Integer id) throws Exception {
        var clienteEncontrado = clienteRepository.findAll().stream()
                .filter(cliente -> cliente.getId().equals(id)).findFirst().orElseThrow(() -> new RegraDeNegocioException("Cliente não existe."));


        return objectMapper.convertValue(clienteEncontrado, ClienteDTO.class);
    }

    public ClienteDTO procurarClienteAtivo(Integer idCliente) throws Exception {
        var cliente = clienteRepository.getById(idCliente);


        var usuarioAtivo = usuarioRepository.findAllUsuariosAtivos().stream()
                .filter(usuario -> usuario.getId().equals(cliente.getId()))
                .findFirst().orElseThrow(() -> new RegraDeNegocioException("Cliente inativo"));

        return objectMapper.convertValue(usuarioAtivo, ClienteDTO.class);
    }

    public List<ClienteDTO> listarClientesAtivos() {
        return usuarioRepository.findAllUsuariosAtivos()
                .stream().filter(cliente -> cliente.getTipoUsuario().equals(TipoUsuario.CLIENTE)).map(usuarios -> objectMapper.convertValue(usuarios, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    public Cliente buscarPorIdEntidade(Integer id) throws Exception{
        Cliente cliente = this.clienteRepository.findByAtivoAndId(Ativo.A,id).orElseThrow(() -> new RegraDeNegocioException("Não foi possível buscar usuario"));
        return cliente;
    }
}
