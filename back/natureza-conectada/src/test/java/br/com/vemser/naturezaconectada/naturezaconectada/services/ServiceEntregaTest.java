package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EntregaRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EnderecoDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EntregaResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.*;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.*;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.ClienteRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EnderecoRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EntregaMudaRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EntregaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceEntregaTest {

    @Mock
    private EntregaRepository entregaRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private ServiceCliente serviceCliente;
    @Mock
    private ServiceEndereco serviceEndereco;
    @Mock
    private ServiceMudas serviceMudas;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private EntregaMudaRepository entregaMudaRepository;
    @InjectMocks
    private ServiceEntrega serviceEntrega;

    @Test
    @DisplayName("Deveria alterar a data de entrega ao mudar o status para 'ENTREGUE'")
    public void deveriaMudarStatusParaEntregueDeveAlterarDataEntrega() throws Exception {

        int idEntrega = 1;

        Entrega entregaMock = new Entrega();
        entregaMock.setId(idEntrega);
        entregaMock.setStatus(StatusEntrega.RECEBIDO);
        Cliente clienteMock = new Cliente();
        clienteMock.setId(1);
        entregaMock.setCliente(clienteMock);

        when(entregaRepository.findById(idEntrega)).thenReturn(Optional.of(entregaMock));
        when(clienteRepository.findById(clienteMock.getId())).thenReturn(Optional.of(clienteMock));

        EntregaResponseDTO entregaResponseDTO = new EntregaResponseDTO();
        entregaResponseDTO.setStatus(StatusEntrega.ENTREGUE);
        entregaResponseDTO.setDataEntregue(LocalDate.now());

        when(objectMapper.convertValue(any(), eq(EntregaResponseDTO.class)))
                .thenReturn(entregaResponseDTO);

        String novoStatus = "ENTREGUE";

        EntregaResponseDTO result = serviceEntrega.mudarStatusEntrega(idEntrega, novoStatus);

        assertNotNull(result);
        assertEquals(StatusEntrega.ENTREGUE, result.getStatus());
        assertNotNull(result.getDataEntregue());
        assertEquals(LocalDate.now(), result.getDataEntregue());
    }

    @Test
    @DisplayName("Deveria lançar exceção ao tentar mudar para um status inválido")
    public void deveriaMudarParaStatusInvalido() {

        int idEntrega = 1;
        String novoStatus = "STATUS_INVALIDO";

        assertThrows(RegraDeNegocioException.class, () -> {
            serviceEntrega.mudarStatusEntrega(idEntrega, novoStatus);
        });
    }

    @Test
    @DisplayName("Deveria lançar exceção ao tentar mudar o status de uma entrega inexistente")
    public void deveriaMudarStatusEntregaParaEntregaInexistente() {

        int idEntregaInexistente = 40;

        when(entregaRepository.findById(idEntregaInexistente)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> {
            serviceEntrega.mudarStatusEntrega(idEntregaInexistente, "ENTREGUE");
        });
    }

    @Test
    @DisplayName("Deveria lançar exceção quando o endereço não for encontrado")
    public void deveriaLancarExcecaoQuandoEnderecoNaoEncontrado() throws Exception {

        Integer idEndereco = 1;
        when(serviceEndereco.procurarPorIdEndereco(idEndereco)).thenReturn(null);

        EntregaRequestDTO entregaRequestDTO = new EntregaRequestDTO();

        Integer idEnderecoInvalido = 1;

        assertThrows(InformacaoNaoEncontrada.class, () -> serviceEntrega.adicionar(entregaRequestDTO, idEnderecoInvalido));
    }

    @Test
    @DisplayName("Deveria lançar exceção quando o cliente não for encontrado")
    public void deveriaLancarExcecaoQuandoClienteNaoEncontrado() throws Exception {

        Integer idCliente = 1;
        lenient().when(serviceCliente.procurarPorId(idCliente)).thenReturn(null);

        EntregaRequestDTO entregaRequestDTO = new EntregaRequestDTO();

        assertThrows(InformacaoNaoEncontrada.class, () -> serviceEntrega.adicionar(entregaRequestDTO, anyInt()));
    }

    @Test
    @DisplayName("Deveria lançar exceção quando mudas inválidas forem passadas como entrada")
    public void deveriaLancarExcecaoQuandoMudasInvalidasForemPassadas() throws Exception {

        Mockito.lenient().when(serviceEndereco.procurarPorIdEndereco(anyInt())).thenReturn(new EnderecoDTO(
                1, "35588000", "Rua 1", "100", "casa", "São Paulo", Estados.SP, Tipo.RESIDENCIAL, Ecossistema.INDIFERENTE, Ativo.A
        ));

        Mockito.lenient().when(serviceCliente.procurarPorId(anyInt())).thenReturn(new ClienteResponseDTO());

        Mockito.lenient().when(serviceMudas.procurarPorIDEntidade(anyInt())).thenReturn(null);

        List<Muda> mudasInvalidas = new ArrayList<>();
        mudasInvalidas.add(new Muda());

        EntregaRequestDTO entregaRequestDTO = new EntregaRequestDTO();
        entregaRequestDTO.setIdCliente(1);
        entregaRequestDTO.setMudas(mudasInvalidas);

        assertThrows(Exception.class, () -> serviceEntrega.adicionar(entregaRequestDTO, anyInt()));
    }

    @Test
    @DisplayName("Deveria listar todas as entregas com sucesso")
    public void deveriaListarTodasEntregasComSucesso() throws Exception {
        List<Entrega> entregasMock = new ArrayList<>();
        Entrega entrega1 = new Entrega();
        entrega1.setId(1);
        Entrega entrega2 = new Entrega();
        entrega2.setId(2);
        entregasMock.add(entrega1);
        entregasMock.add(entrega2);

        when(entregaRepository.findAll()).thenReturn(entregasMock);

        List<EntregaResponseDTO> entregasResponseDTO = new ArrayList<>();
        EntregaResponseDTO entregaResponseDTO1 = new EntregaResponseDTO();
        entregaResponseDTO1.setId(1);
        EntregaResponseDTO entregaResponseDTO2 = new EntregaResponseDTO();
        entregaResponseDTO2.setId(2);
        entregasResponseDTO.add(entregaResponseDTO1);
        entregasResponseDTO.add(entregaResponseDTO2);

        when(objectMapper.convertValue(any(), eq(EntregaResponseDTO.class)))
                .thenReturn(entregaResponseDTO1)
                .thenReturn(entregaResponseDTO2);

        List<EntregaResponseDTO> result = serviceEntrega.listarTodos();

        assertEquals(entregasResponseDTO, result);
    }

    @Test
    @DisplayName("Deveria lançar exceção quando não houver entregas cadastradas")
    public void deveriaLancarExcecaoQuandoNaoHouverEntregas() {
        when(entregaRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(RegraDeNegocioException.class, () -> serviceEntrega.listarTodos());
    }

    @Test
    @DisplayName("Deveria procurar uma entrega por ID com sucesso")
    public void deveriaProcurarEntregaPorIdComSucesso() throws Exception {

        int idEntregaExistente = 1;
        Entrega entregaMock = new Entrega();
        entregaMock.setId(1);

        EntregaResponseDTO entregaResponseDTOMock = new EntregaResponseDTO();
        entregaResponseDTOMock.setId(1);

        when(entregaRepository.findById(idEntregaExistente)).thenReturn(Optional.of(entregaMock));
        when(objectMapper.convertValue(entregaMock, EntregaResponseDTO.class)).thenReturn(entregaResponseDTOMock);

        EntregaResponseDTO entregaResponseDTO = serviceEntrega.procurarPorId(idEntregaExistente);

        assertNotNull(entregaResponseDTO);
        assertEquals(entregaResponseDTOMock, entregaResponseDTO);
    }

    @Test
    @DisplayName("Deveria lançar exceção ao tentar procurar uma entrega por ID inexistente")
    public void deveriaLancarExcecaoAoProcurarEntregaPorIdInexistente() {
        int idEntregaInexistente = 1;
        when(entregaRepository.findById(idEntregaInexistente)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> serviceEntrega.procurarPorId(idEntregaInexistente));
    }

}