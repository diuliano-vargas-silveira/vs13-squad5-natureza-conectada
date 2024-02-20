package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ClienteRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.ClienteRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ServiceCliente - Test")
public class ServiceClienteTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ServiceCliente serviceCliente;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ServiceLog serviceLog;

    @Test
    @DisplayName("Deveria criar um cliente com sucesso")
    public void deveriaAdicionarClienteComSucesso() throws Exception {
        var clienteRequest = gerarClienteRequest();
        var clienteResponse = gerarClienteResponse();
        var cliente = gerarCliente();

        when(objectMapper.convertValue(clienteRequest, Cliente.class)).thenReturn(cliente);
        when(passwordEncoder.encode(cliente.getSenha())).thenReturn("oaihfoisahfsh");
        when(clienteRepository.save(any())).thenReturn(cliente);
        when(objectMapper.convertValue(cliente, ClienteResponseDTO.class)).thenReturn(clienteResponse);

        var clienteCriadoDTO = serviceCliente.adicionar(clienteRequest);

        assertNotNull(clienteCriadoDTO);
        assertEquals(clienteCriadoDTO, clienteResponse);

    }

    @Test
    @DisplayName("Deveria lançar exceção ao adicionar cliente")
    public void deveriaLancarExcecaoAoAdicionarCliente() throws Exception {

        var clienteRequest = gerarClienteRequest();
        var cliente = gerarCliente();

        when(objectMapper.convertValue(clienteRequest, Cliente.class)).thenReturn(cliente);
        when(passwordEncoder.encode(cliente.getSenha())).thenReturn("oaihfoisahfsh");
        when(clienteRepository.save(any())).thenThrow(new RuntimeException("Erro ao salvar cliente"));

        assertThrows(RuntimeException.class, () -> {
            serviceCliente.adicionar(clienteRequest);
        });
    }

    @Test
    @DisplayName("Deveria editar um cliente com sucesso")
    public void deveriaEditarClienteComSucesso() throws Exception {
        var idCliente = 1;
        var clienteRequest = gerarClienteRequest();
        var cliente = gerarCliente();

        when(clienteRepository.getById(idCliente)).thenReturn(cliente);
        when(passwordEncoder.encode(cliente.getSenha())).thenReturn("oaihfoisahfsh");
        when(clienteRepository.save(any())).thenReturn(cliente);
        when(objectMapper.convertValue(cliente, ClienteResponseDTO.class)).thenReturn(gerarClienteResponse());

        var clienteEditadoDTO = serviceCliente.editar(idCliente, clienteRequest);

        assertEquals(clienteRequest.getNome(), cliente.getNome());
        assertEquals(clienteRequest.getEmail(), cliente.getEmail());
        assertEquals(clienteRequest.getCpf(), cliente.getCpf());
        assertEquals(Ativo.A, cliente.getAtivo());

    }

    @Test
    @DisplayName("Deveria lançar exceção ao editar cliente")
    public void deveriaLancarExcecaoAoEditarCliente() throws Exception {

        var idCliente = 1;
        var clienteRequest = gerarClienteRequest();
        var cliente = gerarCliente();

        when(clienteRepository.getById(idCliente)).thenReturn(cliente);
        when(passwordEncoder.encode(cliente.getSenha())).thenReturn("oaihfoisahfsh");

        when(clienteRepository.save(any())).thenThrow(new RuntimeException("Erro ao salvar cliente"));

        assertThrows(RuntimeException.class, () -> {
            serviceCliente.editar(idCliente, clienteRequest);
        });
    }

    @Test
    @DisplayName("Deveria remover um cliente com sucesso")
    public void deveriaRemoverClienteComSucesso() throws Exception {
        var idCliente = 1;
        var cliente = gerarCliente();
        when(clienteRepository.getById(idCliente)).thenReturn(cliente);

        serviceCliente.remover(idCliente);

        verify(clienteRepository, times(1)).getById(idCliente);
        verify(clienteRepository, times(1)).save(cliente);

        assertEquals(Ativo.D, cliente.getAtivo());
    }

    @Test
    @DisplayName("Deveria lançar exceção ao remover cliente")
    public void deveriaLancarExcecaoAoRemoverCliente() throws Exception {
        var idCliente = 1;
        var cliente = gerarCliente();
        when(clienteRepository.getById(idCliente)).thenReturn(cliente);
        doThrow(new RuntimeException("Erro ao salvar cliente após remoção")).when(clienteRepository).save(cliente);

        assertThrows(RuntimeException.class, () -> {
            serviceCliente.remover(idCliente);
        });

        verify(clienteRepository, times(1)).getById(idCliente);
        verify(clienteRepository, times(1)).save(cliente);
        assertEquals(Ativo.D, cliente.getAtivo());
    }

    @Test
    @DisplayName("Deveria listar todos os clientes com sucesso")
    public void deveriaListarTodosClientesComSucesso() throws Exception {
        Pageable paginacao = Pageable.unpaged();
        List<Cliente> clientes = gerarListaClientes();
        Page<Cliente> clientesPaginados = new PageImpl<>(clientes);
        when(clienteRepository.findAll(paginacao)).thenReturn(clientesPaginados);

        List<ClienteResponseDTO> clientesResponse = new ArrayList<>();
        for (Cliente cliente : clientes) {
            ClienteResponseDTO clienteResponse = gerarClienteResponse();
            clienteResponse.setNome(cliente.getNome());
            clienteResponse.setEmail(cliente.getEmail());
            clientesResponse.add(clienteResponse);
        }
        when(objectMapper.convertValue(any(), eq(ClienteResponseDTO.class)))
                .thenAnswer(invocation -> {
                    Cliente cliente = invocation.getArgument(0);
                    var clienteResponse = new ClienteResponseDTO();
                    clienteResponse.setNome(cliente.getNome());
                    clienteResponse.setEmail(cliente.getEmail());
                    clienteResponse.setCpf(cliente.getCpf());

                    return clienteResponse;
                });

        Page<ClienteResponseDTO> resultado = serviceCliente.listarTodos(paginacao);

        assertEquals(clientes.size(), resultado.getContent().size());

        for (int i = 0; i < clientes.size(); i++) {
            assertEquals(clientes.get(i).getNome(), resultado.getContent().get(i).getNome());
            assertEquals(clientes.get(i).getEmail(), resultado.getContent().get(i).getEmail());
            assertEquals(clientes.get(i).getCpf(), resultado.getContent().get(i).getCpf());
        }
    }

    @Test
    @DisplayName("Deveria lançar exceção ao listar todos os clientes")
    public void deveriaFalharAoListarTodosClientes() throws Exception {

        Pageable paginacao = Pageable.unpaged();

        when(clienteRepository.findAll(paginacao)).thenThrow(new RuntimeException("Erro ao buscar clientes"));

        assertThrows(RuntimeException.class, () -> {
            serviceCliente.listarTodos(paginacao);
        });
    }

    @Test
    @DisplayName("Deveria procurar cliente por ID com sucesso")
    public void deveriaProcurarClientePorIdComSucesso() throws Exception {
        Integer idCliente = 1;
        var cliente = gerarCliente();
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(objectMapper.convertValue(cliente, ClienteResponseDTO.class)).thenReturn(gerarClienteResponse());

        ClienteResponseDTO clienteResponse = serviceCliente.procurarPorId(idCliente);

        assertNotNull(clienteResponse);
        assertEquals(cliente.getNome(), clienteResponse.getNome());
        assertEquals(cliente.getCpf(), clienteResponse.getCpf());

    }

    @Test
    @DisplayName("Deveria procurar cliente ativo por ID com sucesso")
    public void deveriaProcurarClienteAtivoPorIdComSucesso() throws Exception {

        Integer idCliente = 1;
        var cliente = gerarCliente();
        cliente.setAtivo(Ativo.A);
        when(clienteRepository.getById(idCliente)).thenReturn(cliente);

        when(objectMapper.convertValue(cliente, ClienteResponseDTO.class))
                .thenReturn(gerarClienteResponse());


        ClienteResponseDTO clienteResponse = serviceCliente.procurarClienteAtivo(idCliente);

        assertNotNull(clienteResponse);
        assertEquals(cliente.getNome(), clienteResponse.getNome());
        assertEquals(cliente.getCpf(), clienteResponse.getCpf());
    }

    @Test
    @DisplayName("Deveria lançar exceção ao procurar cliente ativo por ID")
    public void deveriaLancarExcecaoProcurarClienteAtivoPorId() {

        Integer idCliente = 1;
        var cliente = gerarCliente();
        cliente.setAtivo(Ativo.D);
        when(clienteRepository.getById(idCliente)).thenReturn(cliente);

        assertThrows(RegraDeNegocioException.class, () -> {
            serviceCliente.procurarClienteAtivo(idCliente);
        });
    }

    @Test
    @DisplayName("Deveria listar clientes ativos com sucesso")
    public void deveriaListarClientesAtivosComSucesso() {

        UsuarioResponseDTO cliente1 = gerarUsuarioResponse();
        UsuarioResponseDTO cliente2 = gerarUsuarioResponse();
        cliente2.setId(2);
        List<UsuarioResponseDTO> usuariosAtivos = Arrays.asList(cliente1, cliente2);

        when(usuarioRepository.findAllUsuariosAtivos()).thenReturn(usuariosAtivos);

        when(objectMapper.convertValue(any(), eq(ClienteResponseDTO.class)))
                .thenAnswer(invocation -> {
                    UsuarioResponseDTO usuarioResponse = invocation.getArgument(0);
                    return gerarClienteResponse();
                });

        List<ClienteResponseDTO> clientesAtivos = serviceCliente.listarClientesAtivos();


        assertEquals(2, clientesAtivos.size());

        for (ClienteResponseDTO clienteResponse : clientesAtivos) {
            assertNotNull(clienteResponse.getId());
            assertNotNull(clienteResponse.getNome());
            assertNotNull(clienteResponse.getCpf());
        }
    }


    @Test
    @DisplayName("Deveria buscar cliente ativo por ID com sucesso")
    public void deveriaBuscarClientePorIdEntidade() throws Exception {
        Integer idCliente = 1;
        var cliente = gerarCliente();

        when(clienteRepository.findByAtivoAndId(Ativo.A, idCliente)).thenReturn(Optional.of(cliente));

        Cliente clienteRetornado = serviceCliente.buscarPorIdEntidade(idCliente);

        assertNotNull(clienteRetornado);
        assertEquals(cliente, clienteRetornado);
    }


    @Test
    @DisplayName("Deveria retornar o cliente do usuário logado")
    public void deveriaRetornarClienteDoUsuarioLogado() throws Exception {

        Integer idUsuarioLogado = 1;
        Cliente clienteEsperado = gerarCliente();

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(idUsuarioLogado.toString());

        when(clienteRepository.findByAtivoAndId(Ativo.A, idUsuarioLogado)).thenReturn(Optional.of(clienteEsperado));

        Cliente clienteRetornado = serviceCliente.getUsuarioLogado();

        assertNotNull(clienteRetornado);
        assertEquals(clienteEsperado, clienteRetornado);
    }



    private static UsuarioResponseDTO gerarUsuarioResponse() {
        UsuarioResponseDTO usuarioResponse = new UsuarioResponseDTO();
        usuarioResponse.setId(1);
        usuarioResponse.setNome("Pedro");
        usuarioResponse.setEmail("pedro@dbccompany.com.br");
        usuarioResponse.setTipoUsuario(TipoUsuario.CLIENTE);
        usuarioResponse.setAtivo(Ativo.A);
        usuarioResponse.setCpf("56336040089");

        return usuarioResponse;
    }


    private static List<Cliente> gerarListaClientes() {
        List<Cliente> clientes = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            var cliente = gerarCliente();
            clientes.add(cliente);
        }

        return clientes;
    }

    private static ClienteRequestDTO gerarClienteRequest() {
        var cliente = new ClienteRequestDTO();

        cliente.setId(1);
        cliente.setNome("Pedro");
        cliente.setCpf("56336040089");
        cliente.setAtivo(Ativo.A);
        cliente.setSenha("123");
        cliente.setEmail("pedro@dbccompany.com.br");
        cliente.setTipoUsuario(TipoUsuario.CLIENTE);
        cliente.setEnderecos(gerarEndereco());
        cliente.setContatos(gerarContato());

        return cliente;
    }

    private static ClienteResponseDTO gerarClienteResponse() {
        var cliente = new ClienteResponseDTO();

        cliente.setId(1);
        cliente.setNome("Pedro");
        cliente.setCpf("56336040089");
        cliente.setAtivo(Ativo.A);
        cliente.setEmail("pedro@dbccompany.com.br");
        cliente.setTipoUsuario(TipoUsuario.CLIENTE);
        cliente.setEnderecos(gerarEndereco());
        cliente.setContatos(gerarContato());

        return cliente;
    }

    private static Cliente gerarCliente() {
        var cliente = new Cliente();

        cliente.setId(1);
        cliente.setNome("Pedro");
        cliente.setCpf("56336040089");
        cliente.setAtivo(Ativo.A);
        cliente.setSenha("123");
        cliente.setEmail("pedro@dbccompany.com.br");
        cliente.setTipoUsuario(TipoUsuario.CLIENTE);
        cliente.setEnderecos(gerarEndereco());
        cliente.setContatos(gerarContato());

        return cliente;

    }

    private static List<Endereco> gerarEndereco() {
        List<Endereco> enderecos = new ArrayList<>();
        var endereco = new Endereco();

        endereco.setIdEndereco(1);
        endereco.setEstado(Estados.RS);
        endereco.setCep("123456789");
        endereco.setLogradouro("Rua ABC");
        endereco.setNumero("123");
        endereco.setComplemento("Casa");
        endereco.setCidade("Porto Alegre");
        endereco.setAtivo(Ativo.A);
        endereco.setTipo(Tipo.RESIDENCIAL);

        enderecos.add(endereco);

        return enderecos;
    }

    private static List<Contato> gerarContato() {
        List<Contato> contatos = new ArrayList<>();
        var contato = new Contato();

        contato.setIdContato(1);
        contato.setTipo(Tipo.RESIDENCIAL);
        contato.setDescricao("Contato");
        contato.setNumero("123456789");

        contatos.add(contato);
        return contatos;

    }
}
