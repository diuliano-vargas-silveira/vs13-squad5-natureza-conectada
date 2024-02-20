package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EnderecoRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EnderecoResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.*;
import br.com.vemser.naturezaconectada.naturezaconectada.models.*;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.ClienteRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ServiceEndereco - Test")
public class ServiceEnderecoTest {


    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private ServiceEndereco serviceEndereco;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Deveria adicionar um endereço com sucesso")
    public void deveriaAdicionarEnderecoComSucesso() throws Exception {
        var enderecoRequest = gerarEnderecoRequest();
        var enderecoResponse = gerarEnderecoResponse();
        var endereco = gerarEndereco();

        var cliente = gerarCliente();
        Integer idCliente = cliente.getId();

        when(objectMapper.convertValue(enderecoRequest, Endereco.class)).thenReturn(endereco);
        when(enderecoRepository.save(any())).thenReturn(endereco);
        when(objectMapper.convertValue(endereco, EnderecoResponseDTO.class)).thenReturn(enderecoResponse);

        var enderecoCriadoDTO = serviceEndereco.adicionar(enderecoRequest, idCliente);

        assertNotNull(enderecoCriadoDTO);
        assertEquals(enderecoCriadoDTO, enderecoResponse);

    }

    @Test
    @DisplayName("Deveria remover um endereço com sucesso")
    public void deveriaRemoverEnderecoComSucesso() throws Exception {

        var endereco = gerarEndereco();
        var idEndereco = endereco.getIdEndereco();

        when(enderecoRepository.getById(idEndereco)).thenReturn(endereco);

        serviceEndereco.remover(idEndereco);

        verify(enderecoRepository, times(1)).getById(idEndereco);
        verify(enderecoRepository, times(1)).save(endereco);
    }


    @Test
    @DisplayName("Deveria editar um endereço com sucesso")
    public void deveriaEditarEnderecoComSucesso() throws Exception {

        var enderecoRequest = gerarEnderecoRequest();
        var endereco = gerarEndereco();
        var idEndereco = endereco.getIdEndereco();

        when(enderecoRepository.getById(idEndereco)).thenReturn(endereco);
        when(enderecoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(objectMapper.convertValue(endereco, Endereco.class)).thenReturn(endereco);
        when(objectMapper.convertValue(any(), eq(EnderecoResponseDTO.class))).thenReturn(gerarEnderecoResponse());

        var enderecoEditadoDTO = serviceEndereco.editar(idEndereco, enderecoRequest);

        assertEquals(enderecoRequest.getLogradouro(), endereco.getLogradouro());
        assertEquals(enderecoRequest.getCep(), endereco.getCep());
        assertEquals(enderecoRequest.getNumero(), endereco.getNumero());
    }

    @Test
    @DisplayName("Deveria procurar um endereço por ID com sucesso")
    public void deveriaProcurarEnderecoPorIdComSucesso() throws Exception {

        Integer idEndereco = 1;
        var endereco = gerarEndereco();
        when(enderecoRepository.findById(idEndereco)).thenReturn(Optional.of(endereco));
        when(objectMapper.convertValue(endereco, EnderecoResponseDTO.class)).thenReturn(gerarEnderecoResponse());


        var enderecoResponseDTO = serviceEndereco.procurarPorIdEndereco(idEndereco);

        assertNotNull(enderecoResponseDTO);
        assertEquals(endereco.getIdEndereco(), enderecoResponseDTO.getIdEndereco());
        assertEquals(endereco.getCep(), enderecoResponseDTO.getCep());
    }

    @Test
    @DisplayName("Deveria listar todos os endereços com sucesso")
    void deveriaListarTodosEnderecosComSucesso() throws Exception {
        List<Endereco> enderecos = gerarListaEndereco();

        when(enderecoRepository.findAll()).thenReturn(enderecos);

        List<EnderecoResponseDTO> enderecosResponse = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            var enderecoResponse = gerarEnderecoResponse();
            enderecoResponse.setIdEndereco(endereco.getIdEndereco());
            enderecoResponse.setLogradouro(endereco.getLogradouro());
            enderecoResponse.setTipo(endereco.getTipo());
            enderecoResponse.setAtivo(endereco.getAtivo());
            enderecoResponse.setEcossistema(endereco.getEcossistema());
            enderecoResponse.setCep(endereco.getCep());
            enderecoResponse.setCidade(endereco.getCidade());
            enderecoResponse.setNumero(endereco.getNumero());
            enderecoResponse.setEstado(endereco.getEstado());
            enderecoResponse.setComplemento(endereco.getComplemento());

            enderecosResponse.add(enderecoResponse);
        }

        when(objectMapper.convertValue(any(), eq(EnderecoResponseDTO.class)))
                .thenAnswer(invocation -> {
                    Endereco endereco = invocation.getArgument(0);
                    var enderecoResponse = new EnderecoResponseDTO();
                    enderecoResponse.setIdEndereco(endereco.getIdEndereco());
                    enderecoResponse.setLogradouro(endereco.getLogradouro());
                    enderecoResponse.setTipo(endereco.getTipo());
                    enderecoResponse.setAtivo(endereco.getAtivo());
                    enderecoResponse.setEcossistema(endereco.getEcossistema());
                    enderecoResponse.setCep(endereco.getCep());
                    enderecoResponse.setCidade(endereco.getCidade());
                    enderecoResponse.setNumero(endereco.getNumero());
                    enderecoResponse.setEstado(endereco.getEstado());
                    enderecoResponse.setComplemento(endereco.getComplemento());

                    return enderecoResponse;
                });

        List<EnderecoResponseDTO> resultado = serviceEndereco.listarTodos();

        assertEquals(enderecos.size(), resultado.size());

        for (int i = 0; i < enderecos.size(); i++) {
            assertEquals(enderecos.get(i).getIdEndereco(), resultado.get(i).getIdEndereco());
            assertEquals(enderecos.get(i).getCep(), resultado.get(i).getCep());
            assertEquals(enderecos.get(i).getLogradouro(), resultado.get(i).getLogradouro());
            assertEquals(enderecos.get(i).getNumero(), resultado.get(i).getNumero());
            assertEquals(enderecos.get(i).getCidade(), resultado.get(i).getCidade());
        }
    }

    @Test
    @DisplayName("Deveria listar endereços ativos com sucesso")
    public void deveriaListarEnderecosAtivosComSucesso() throws Exception {
        List<Endereco> enderecos = gerarListaEndereco();

        when(enderecoRepository.findAll()).thenReturn(enderecos);

        List<EnderecoResponseDTO> enderecosResponse = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            var enderecoResponse = gerarEnderecoResponse();
            enderecoResponse.setIdEndereco(endereco.getIdEndereco());
            enderecoResponse.setLogradouro(endereco.getLogradouro());
            enderecoResponse.setTipo(endereco.getTipo());
            enderecoResponse.setAtivo(endereco.getAtivo());
            enderecoResponse.setEcossistema(endereco.getEcossistema());
            enderecoResponse.setCep(endereco.getCep());
            enderecoResponse.setCidade(endereco.getCidade());
            enderecoResponse.setNumero(endereco.getNumero());
            enderecoResponse.setEstado(endereco.getEstado());
            enderecoResponse.setComplemento(endereco.getComplemento());

            enderecosResponse.add(enderecoResponse);
        }

        when(objectMapper.convertValue(any(), eq(EnderecoResponseDTO.class)))
                .thenAnswer(invocation -> {
                    Endereco endereco = invocation.getArgument(0);
                    var enderecoResponse = new EnderecoResponseDTO();
                    enderecoResponse.setIdEndereco(endereco.getIdEndereco());
                    enderecoResponse.setLogradouro(endereco.getLogradouro());
                    enderecoResponse.setTipo(endereco.getTipo());
                    enderecoResponse.setAtivo(endereco.getAtivo());
                    enderecoResponse.setEcossistema(endereco.getEcossistema());
                    enderecoResponse.setCep(endereco.getCep());
                    enderecoResponse.setCidade(endereco.getCidade());
                    enderecoResponse.setNumero(endereco.getNumero());
                    enderecoResponse.setEstado(endereco.getEstado());
                    enderecoResponse.setComplemento(endereco.getComplemento());

                    return enderecoResponse;
                });

        List<EnderecoResponseDTO> resultado = serviceEndereco.listarEnderecosPorAtivo();

        assertEquals(enderecos.size(), resultado.size());

        for (int i = 0; i < enderecos.size(); i++) {
            assertEquals(enderecos.get(i).getIdEndereco(), resultado.get(i).getIdEndereco());
            assertEquals(enderecos.get(i).getCep(), resultado.get(i).getCep());
            assertEquals(enderecos.get(i).getLogradouro(), resultado.get(i).getLogradouro());
            assertEquals(enderecos.get(i).getNumero(), resultado.get(i).getNumero());
            assertEquals(enderecos.get(i).getCidade(), resultado.get(i).getCidade());
        }
    }

    @Test
    @DisplayName("Deveria ativar um endereço com sucesso")
    void deveriaAtivarEnderecoComSucesso() throws Exception {
        var idEndereco = 1;
        var endereco = gerarEndereco();
        when(enderecoRepository.getById(idEndereco)).thenReturn(endereco);

        String ecossistema = endereco.getEcossistema().toString();
        serviceEndereco.ativarEndereco(idEndereco, ecossistema);

        verify(enderecoRepository, times(1)).getById(idEndereco);
        verify(enderecoRepository, times(1)).save(endereco);

        assertEquals(Ativo.A, endereco.getAtivo());

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
        cliente.setContatos(gerarContato());

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

    private static Endereco gerarEndereco() {
        List<Cliente> clientes = new ArrayList<>();
        List<Entrega> entregas = new ArrayList<>();
        var cliente = gerarCliente();
        clientes.add(cliente);
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
        endereco.setEcossistema(Ecossistema.COSTEIRO);
        endereco.setClientes(clientes);
        endereco.setEntregas(entregas);


        return endereco;
    }

    private static EnderecoRequestDTO gerarEnderecoRequest() {
        List<Cliente> clientes = new ArrayList<>();
        var cliente = gerarCliente();
        clientes.add(cliente);
        var endereco = new EnderecoRequestDTO();

        endereco.setIdEndereco(1);
        endereco.setEstado(Estados.RS);
        endereco.setCep("123456789");
        endereco.setLogradouro("Rua ABC");
        endereco.setNumero("123");
        endereco.setComplemento("Casa");
        endereco.setCidade("Porto Alegre");
        endereco.setAtivo(Ativo.A);
        endereco.setTipo(Tipo.RESIDENCIAL);
        endereco.setEcossistema(Ecossistema.COSTEIRO);
        endereco.setClientes(clientes);

        return endereco;
    }

    private static EnderecoResponseDTO gerarEnderecoResponse() {
        var endereco = new EnderecoResponseDTO();

        endereco.setIdEndereco(1);
        endereco.setEstado(Estados.RS);
        endereco.setCep("123456789");
        endereco.setLogradouro("Rua ABC");
        endereco.setNumero("123");
        endereco.setComplemento("Casa");
        endereco.setCidade("Porto Alegre");
        endereco.setAtivo(Ativo.A);
        endereco.setTipo(Tipo.RESIDENCIAL);
        endereco.setEcossistema(Ecossistema.COSTEIRO);


        return endereco;
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
