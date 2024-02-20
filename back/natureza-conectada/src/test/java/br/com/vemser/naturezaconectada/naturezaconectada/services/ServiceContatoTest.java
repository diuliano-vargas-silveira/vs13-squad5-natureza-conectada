package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ContatoRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ContatoResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EnderecoResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ServiceContato - Test")
public class ServiceContatoTest {

    @Mock
    private ContatoRepository contatoRepository;

    @InjectMocks
    private ServiceContato serviceContato;

    @Mock
    private ServiceCliente serviceCliente;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deveria adicionar um contato com sucesso")
    public void deveriaAdicionarEnderecoComSucesso() throws Exception {
        var contatoRequest = gerarContatoRequest();
        var contatoResponse = gerarContatoResponse();
        var contato = gerarContato();

        var cliente = gerarCliente();
        Integer idCliente = cliente.getId();

        when(serviceCliente.procurarClienteAtivo(idCliente)).thenReturn(gerarClienteResponse());
        when(objectMapper.convertValue(contatoRequest, Contato.class)).thenReturn(contato);
        when(objectMapper.convertValue(gerarClienteResponse(), Cliente.class)).thenReturn(cliente);
        when(contatoRepository.save(contato)).thenReturn(contato);
        when(objectMapper.convertValue(contato, ContatoResponseDTO.class)).thenReturn(contatoResponse);

        var contatoCriadoDTO = serviceContato.adicionar(contatoRequest, idCliente);

        assertNotNull(contatoCriadoDTO);
        assertEquals(contatoResponse, contatoCriadoDTO);
    }

    @Test
    @DisplayName("Deveria editar um contato com sucesso")
    public void deveriaEditarContatoComSucesso() throws Exception {
        var contatoRequest = gerarContatoRequest();
        var contato = gerarContato();
        var idContato = contato.getIdContato();
        var cliente = gerarCliente();

        when(contatoRepository.getById(idContato)).thenReturn(contato);
        when(contatoRepository.save(any())).thenReturn(contato);
        lenient().when(objectMapper.convertValue(eq(contatoRequest), eq(Contato.class))).thenReturn(contato);
        when(objectMapper.convertValue(any(), eq(Cliente.class))).thenReturn(cliente);
        when(objectMapper.convertValue(eq(contato), eq(ContatoResponseDTO.class))).thenReturn(gerarContatoResponse());

        var contatoEditadoDTO = serviceContato.editar(idContato, contatoRequest);

        assertEquals(contatoRequest.getDescricao(), contato.getDescricao());
        assertEquals(contatoRequest.getNumero(), contato.getNumero());
        assertEquals(contatoRequest.getTipo(), contato.getTipo());
        assertEquals(cliente, contato.getCliente());
        assertEquals(contatoRequest.getId(), contato.getIdContato());
    }

    @Test
    @DisplayName("Deveria remover um endereço com sucesso")
    public void deveriaRemoverContatoComSucesso() throws Exception {

        var contato = gerarContato();
        var idContato = contato.getIdContato();

        when(contatoRepository.getById(idContato)).thenReturn(contato);

        serviceContato.remover(idContato);

        verify(contatoRepository, times(1)).getById(idContato);

    }

    @Test
    @DisplayName("Deveria listar todos os endereços com sucesso")
    void deveriaListarTodosContatosComSucesso() throws Exception {
        List<Contato> contatos = gerarListaContato();

        when(contatoRepository.findAll()).thenReturn(contatos);

        List<ContatoResponseDTO> contatosResponse = new ArrayList<>();
        for (Contato contato : contatos) {
            var contatoResponse = gerarContatoResponse();
            contatoResponse.setIdContato(contato.getIdContato());
            contatoResponse.setTipo(contato.getTipo());
            contatoResponse.setDescricao(contato.getDescricao());
            contatoResponse.setNumero(contato.getNumero());
            contatosResponse.add(contatoResponse);
        }

        when(objectMapper.convertValue(any(), eq(ContatoResponseDTO.class)))
                .thenAnswer(invocation -> {
                    Contato contato = invocation.getArgument(0);
                    var contatoResponse = new ContatoResponseDTO();
                    contatoResponse.setIdContato(contato.getIdContato());
                    contatoResponse.setTipo(contato.getTipo());
                    contatoResponse.setDescricao(contato.getDescricao());
                    contatoResponse.setNumero(contato.getNumero());

                    return contatoResponse;
                });

        List<ContatoResponseDTO> resultado = serviceContato.listarTodos();

        assertEquals(contatos.size(), resultado.size());

        for (int i = 0; i < contatos.size(); i++) {
            assertEquals(contatos.get(i).getIdContato(), resultado.get(i).getIdContato());
            assertEquals(contatos.get(i).getTipo(), resultado.get(i).getTipo());
            assertEquals(contatos.get(i).getDescricao(), resultado.get(i).getDescricao());
            assertEquals(contatos.get(i).getNumero(), resultado.get(i).getNumero());
        }
    }


    @Test
    @DisplayName("Deveria procurar contatos por ID de cliente com sucesso")
    void deveriaProcurarPorIdClienteComSucesso() throws Exception {
        var cliente = gerarCliente();
        var idCliente = cliente.getId();
        var contatos = cliente.getContatos();
        for (Contato contato : contatos)
            contato.setCliente(cliente);

        when(contatoRepository.findAll()).thenReturn(new ArrayList<>(contatos));

        for (Contato contato : contatos) {
            ContatoResponseDTO contatoResponse = gerarContatoResponse();
            when(objectMapper.convertValue(contato, ContatoResponseDTO.class)).thenReturn(contatoResponse);
        }

        List<ContatoResponseDTO> resultado = serviceContato.procurarPorIdCliente(idCliente);

        assertEquals(contatos.size(), resultado.size());
        for (int i = 0; i < contatos.size(); i++) {
            assertEquals(contatos.get(i).getIdContato(), resultado.get(i).getIdContato());
            assertEquals(contatos.get(i).getTipo(), resultado.get(i).getTipo());
            assertEquals(contatos.get(i).getDescricao(), resultado.get(i).getDescricao());
            assertEquals(contatos.get(i).getNumero(), resultado.get(i).getNumero());
        }
    }


    private static ClienteResponseDTO gerarClienteResponse() {
        var cliente = new ClienteResponseDTO();

        cliente.setId(1);
        cliente.setNome("Pedro");
        cliente.setCpf("56336040089");
        cliente.setAtivo(Ativo.A);
        cliente.setEmail("pedro@dbccompany.com.br");
        cliente.setTipoUsuario(TipoUsuario.CLIENTE);
        cliente.setEnderecos(gerarListaEndereco());
        cliente.setContatos(gerarListaContato());

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
        cliente.setEnderecos(gerarListaEndereco());
        cliente.setContatos(gerarListaContato());

        return cliente;

    }

    private static List<Endereco> gerarListaEndereco() {
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

    private static List<Contato> gerarListaContato() {
        List<Contato> contatos = new ArrayList<>();

        var contato = new Contato();
        contato.setIdContato(1);
        contato.setTipo(Tipo.RESIDENCIAL);
        contato.setDescricao("Contato");
        contato.setNumero("123456789");


        contatos.add(contato);
        return contatos;
    }

    private static List<ContatoResponseDTO> gerarListaContatoResponse() {
        List<ContatoResponseDTO> contatos = new ArrayList<>();

        var contato = new ContatoResponseDTO();
        contato.setIdContato(1);
        contato.setTipo(Tipo.RESIDENCIAL);
        contato.setDescricao("Contato");
        contato.setNumero("123456789");


        contatos.add(contato);
        return contatos;
    }

    private static Contato gerarContato() {
        var contato = new Contato();

        contato.setIdContato(1);
        contato.setTipo(Tipo.RESIDENCIAL);
        contato.setDescricao("Contato");
        contato.setNumero("123456789");
        contato.setCliente(gerarCliente());

        return contato;

    }

    private static ContatoRequestDTO gerarContatoRequest() {
        var contato = new ContatoRequestDTO();

        contato.setTipo(Tipo.RESIDENCIAL);
        contato.setDescricao("Contato");
        contato.setNumero("123456789");
        contato.setId(1);
        contato.setCliente(gerarCliente());

        return contato;

    }

    private static ContatoResponseDTO gerarContatoResponse() {
        var contato = new ContatoResponseDTO();

        contato.setIdContato(1);
        contato.setTipo(Tipo.RESIDENCIAL);
        contato.setDescricao("Contato");
        contato.setNumero("123456789");

        return contato;
    }
}
