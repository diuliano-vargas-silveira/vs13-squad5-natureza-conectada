package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ClienteCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.IClienteRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.IUsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ServiceCliente  {

    private final IClienteRepository clienteRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;



    public ClienteDTO adicionar(ClienteCreateDTO clienteCreateDTO) throws Exception {
        var cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        cliente.setAtivo(Ativo.A);
        cliente.setTipoUsuario(TipoUsuario.CLIENTE);
        clienteRepository.save(cliente);

        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }

    public ClienteDTO editar(Integer idCliente, ClienteCreateDTO clienteEditado) throws Exception {

        var clienteEncontrado = clienteRepository.getById(idCliente);


        clienteEncontrado.setNome(clienteEditado.getNome());
        clienteEncontrado.setEmail(clienteEditado.getEmail());
        clienteEncontrado.setSenha(clienteEditado.getSenha());

        clienteRepository.save(clienteEncontrado);

        return objectMapper.convertValue(clienteEncontrado, ClienteDTO.class);
    }

    public void remover(Integer idCliente) throws Exception {
        var clienteEncontrado = clienteRepository.getById(idCliente);
        if (clienteEncontrado.getAtivo() == Ativo.A)
            clienteEncontrado.setAtivo(Ativo.D);
    }

    public List<ClienteDTO> listarTodos() throws Exception {
        var clientes =  clienteRepository.findAll();

        return clientes.stream()
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
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
