package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ClienteCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.UsuarioRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.ClienteRepository;
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

    private final ServiceUsuario serviceUsuario;
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;



    public ClienteDTO adicionar(ClienteCreateDTO cliente) throws Exception {

        var usuarioCriado = objectMapper.convertValue(cliente, UsuarioRequestDTO.class);
        var usuario = serviceUsuario.adicionarUsuario(usuarioCriado);

        cliente.setId(usuario.getId());

        var clienteCriado = objectMapper.convertValue(cliente, Cliente.class);

        clienteRepository.adicionar(clienteCriado);

        return objectMapper.convertValue(clienteCriado, ClienteDTO.class);

    }

    public ClienteDTO editar(Integer idCliente, ClienteCreateDTO clienteEditado) throws Exception {
        var cliente = objectMapper.convertValue(clienteEditado, Cliente.class);
        var clienteEncontrado = clienteRepository.procurarPorIdCliente(idCliente);

        var usuarioEditado = objectMapper.convertValue(cliente, UsuarioRequestDTO.class);

        serviceUsuario.editar(clienteEncontrado.getId(), usuarioEditado);

        clienteRepository.editar(idCliente, cliente);

        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }

    public void remover(Integer idCliente) throws Exception {
        var clienteEncontrado = clienteRepository.procurarPorIdCliente(idCliente);

        serviceUsuario.remover(clienteEncontrado.getId());
    }

    public List<ClienteDTO> listarTodos() throws Exception {
        var clientes =  clienteRepository.listarTodos();

        return clientes.stream()
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    public ClienteDTO procurarPorIdCliente(Integer idCliente) throws Exception {
        Cliente cliente = clienteRepository.procurarPorIdCliente(idCliente);

        var clienteEncontrado = objectMapper.convertValue(cliente, ClienteDTO.class);


        return clienteEncontrado;
    }

    public void procurarClientesAtivos(Integer idCliente) throws Exception {
        var cliente = clienteRepository.procurarPorIdCliente(idCliente);
        var usuario = objectMapper.convertValue(cliente, UsuarioRequestDTO.class);

        var usuariosAtivos = serviceUsuario.procurarUsuariosAtivos();
    }

//    public ClienteDTO listrPorEmail(String email) {
//
//    }

    public void inserirMudasEntregues(Integer idCliente,Integer idMuda) throws Exception {
        clienteRepository.InserirMudaEmCliente(idCliente,idMuda);

    }
}
