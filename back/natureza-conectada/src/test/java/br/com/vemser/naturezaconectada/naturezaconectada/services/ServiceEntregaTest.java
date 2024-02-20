package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EntregaRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.*;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.*;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.*;
import br.com.vemser.naturezaconectada.naturezaconectada.pk.EntregaMudaPK;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

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
    @Spy
    @InjectMocks
    private ServiceEntrega serviceEntrega;

    @Test
    @DisplayName("Deveria adicionar uma entrega com sucesso")
    public void deveriaAdicionarEntregaComSucesso() throws Exception {

        Endereco enderecoMock = gerarEndereco();
        EnderecoResponseDTO enderecoDTO = gerarEnderecoDTO();
        List<Muda> mudas = gerarMudas();

        Integer id = new Random().nextInt();

        EntregaRequestDTO entregaRequestDTO = new EntregaRequestDTO();
        entregaRequestDTO.setIdCliente(id);
        entregaRequestDTO.setStatus(StatusEntrega.RECEBIDO);
        entregaRequestDTO.setDataPedido(LocalDate.now());
        entregaRequestDTO.setEndereco(enderecoMock);
        entregaRequestDTO.setMudas(mudas);

        List<MudaDTO> mudasDTO = new ArrayList<>();

        for (Muda muda : mudas) {
            MudaDTO mudaDTO = objectMapper.convertValue(muda, MudaDTO.class);
            mudasDTO.add(mudaDTO);
        }

        EntregaResponseDTO entregaResponseDTO = new EntregaResponseDTO();
        entregaResponseDTO.setCliente(objectMapper.convertValue(gerarCliente(), ClienteEntregaDTO.class));
        entregaResponseDTO.setStatus(StatusEntrega.RECEBIDO);
        entregaResponseDTO.setDataPedido(LocalDate.now());
        entregaResponseDTO.setEndereco(objectMapper.convertValue(enderecoMock, EnderecoEntregaDTO.class));
        entregaResponseDTO.setMudas(mudasDTO);

        Cliente clienteMock = gerarCliente();
        Entrega entregaMock = gerarEntrega();

        when(this.serviceEndereco.procurarPorIdEndereco(anyInt())).thenReturn(enderecoDTO);
        doReturn(mudas).when(this.serviceEntrega).validarMudas(mudas);
        lenient().doReturn(enderecoMock).when(this.serviceEntrega).obterEnderecoPorId(id);
        when(objectMapper.convertValue(entregaRequestDTO, Entrega.class)).thenReturn(entregaMock);

        lenient().when(entregaRepository.save(any(Entrega.class))).thenReturn(entregaMock);
        when(objectMapper.convertValue(entregaMock, EntregaResponseDTO.class)).thenReturn(entregaResponseDTO);

        doReturn(enderecoMock).when(serviceEntrega).obterEnderecoPorId(anyInt());
        doReturn(clienteMock).when(serviceEntrega).obterClientePorId(anyInt());
        lenient().when(entregaRepository.save(any(Entrega.class))).thenReturn(entregaMock);

        var result = serviceEntrega.adicionar(entregaRequestDTO, 1);

        assertNotNull(result);
    }

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

        assertThrows(InformacaoNaoEncontrada.class, () -> serviceEntrega.adicionar(entregaRequestDTO, idCliente));
    }

    @Test
    @DisplayName("Deveria lançar exceção quando mudas inválidas forem passadas como entrada")
    public void deveriaLancarExcecaoQuandoMudasInvalidasForemPassadas() throws Exception {

        Mockito.lenient().when(serviceEndereco.procurarPorIdEndereco(anyInt())).thenReturn(new EnderecoResponseDTO(
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
    @DisplayName("Deveria processar as mudas corretamente e atualizar o estoque")
    public void deveriaProcessarMudasEAtualizarEstoqueCorretamente() {

        List<Muda> mudas = new ArrayList<>();
        Muda muda1 = new Muda();
        muda1.setId(1);
        muda1.setEstoque(3);
        Muda muda2 = new Muda();
        muda2.setId(2);
        muda2.setEstoque(2);
        mudas.add(muda1);
        mudas.add(muda2);
        Entrega entregaProcessada = new Entrega();
        entregaProcessada.setId(1);

        serviceEntrega.processarMudas(mudas, entregaProcessada);

        assertEquals(2, muda1.getEstoque());
        assertEquals(1, muda2.getEstoque());
    }


    @Test
    @DisplayName("Deveria salvar as relações de entrega e muda corretamente")
    public void deveriaSalvarRelacoesDeEntregaEMudaCorretamente() {

        List<Muda> mudas = new ArrayList<>();
        Muda muda = new Muda();
        muda.setId(1);
        mudas.add(muda);
        Entrega entregaProcessada = new Entrega();
        entregaProcessada.setId(1);

        serviceEntrega.processarMudas(mudas, entregaProcessada);

        verify(entregaMudaRepository, times(1)).save(any(EntregaMuda.class));
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

    @Test
    @DisplayName("Deveria retornar lista vazia se não houver mudas de entrega")
    public void deveriaRetornarListaVaziaSeNaoHouverMudasEntrega() throws Exception {

        int idEntrega = 1;

        when(entregaMudaRepository.buscarMudasEntrega(idEntrega)).thenReturn(new ArrayList<>());

        List<MudaDTO> result = serviceEntrega.buscarMudasEntrega(idEntrega);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deveria buscar mudas de entrega com sucesso")
    public void deveriaBuscarMudasEntregaComSucesso() throws Exception {

        int idEntrega = 1;

        EntregaMuda entregaMuda1 = new EntregaMuda();
        EntregaMudaPK pk1 = new EntregaMudaPK();
        pk1.setIdEntrega(1);
        pk1.setIdMuda(1);
        entregaMuda1.setEntregaPK(pk1);

        EntregaMuda entregaMuda2 = new EntregaMuda();
        EntregaMudaPK pk2 = new EntregaMudaPK();
        pk2.setIdEntrega(1);
        pk2.setIdMuda(2);
        entregaMuda2.setEntregaPK(pk2);

        List<EntregaMuda> entregaMudaList = new ArrayList<>();
        entregaMudaList.add(entregaMuda1);
        entregaMudaList.add(entregaMuda2);

        when(entregaMudaRepository.buscarMudasEntrega(idEntrega)).thenReturn(entregaMudaList);

        MudaDTO mudaDTO1 = new MudaDTO();
        MudaDTO mudaDTO2 = new MudaDTO();
        when(serviceMudas.procurarPorIdDto(1)).thenReturn(mudaDTO1);
        when(serviceMudas.procurarPorIdDto(2)).thenReturn(mudaDTO2);

        List<MudaDTO> result = serviceEntrega.buscarMudasEntrega(idEntrega);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(mudaDTO1));
        assertTrue(result.contains(mudaDTO2));
    }

    @Test
    @DisplayName("Deveria retornar lista vazia se não houver mudas de entrega")
    public void deveriaRetornarListaVaziaSeNaoHouverMudasEntityEntrega() throws Exception {

        int idEntrega = 1;

        when(entregaMudaRepository.buscarMudasEntrega(idEntrega)).thenReturn(new ArrayList<>());

        List<Muda> result = serviceEntrega.buscarMudasEntityEntrega(idEntrega);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deveria buscar mudas de entrega com sucesso")
    public void deveriaBuscarMudasEntityEntregaComSucesso() throws Exception {

        int idEntrega = 1;
        EntregaMuda entregaMuda1 = new EntregaMuda();
        EntregaMudaPK pk1 = new EntregaMudaPK();
        pk1.setIdEntrega(1);
        pk1.setIdMuda(1);
        entregaMuda1.setEntregaPK(pk1);

        EntregaMuda entregaMuda2 = new EntregaMuda();
        EntregaMudaPK pk2 = new EntregaMudaPK();
        pk2.setIdEntrega(1);
        pk2.setIdMuda(2);
        entregaMuda2.setEntregaPK(pk2);

        List<EntregaMuda> entregaMudaList = new ArrayList<>();
        entregaMudaList.add(entregaMuda1);
        entregaMudaList.add(entregaMuda2);

        when(entregaMudaRepository.buscarMudasEntrega(idEntrega)).thenReturn(entregaMudaList);

        Muda muda1 = new Muda();
        Muda muda2 = new Muda();
        when(serviceMudas.procurarPorIDEntidade(1)).thenReturn(muda1);
        when(serviceMudas.procurarPorIDEntidade(2)).thenReturn(muda2);

        List<Muda> result = serviceEntrega.buscarMudasEntityEntrega(idEntrega);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(muda1));
        assertTrue(result.contains(muda2));
    }

    @Test
    @DisplayName("Deveria lançar InformacaoNaoEncontrada ao validar endereço inexistente")
    public void deveriaLancarInformacaoNaoEncontradaAoValidarEnderecoInexistente() throws Exception {

        int idEnderecoInexistente = 1;
        when(serviceEndereco.procurarPorIdEndereco(idEnderecoInexistente)).thenReturn(null);

        assertThrows(InformacaoNaoEncontrada.class, () -> serviceEntrega.validarEndereco(idEnderecoInexistente));
    }

    @Test
    @DisplayName("Não deveria lançar InformacaoNaoEncontrada ao validar endereço existente")
    public void naoDeveriaLancarInformacaoNaoEncontradaAoValidarEnderecoExistente() throws Exception {

        int idEnderecoExistente = 1;

        Mockito.lenient().when(serviceEndereco.procurarPorIdEndereco(anyInt())).thenReturn(new EnderecoResponseDTO(
                1, "35588000", "Rua 1", "100", "casa", "São Paulo", Estados.SP, Tipo.RESIDENCIAL, Ecossistema.INDIFERENTE, Ativo.A));

        assertDoesNotThrow(() -> serviceEntrega.validarEndereco(idEnderecoExistente));
    }

    @Test
    @DisplayName("Deveria lançar InformacaoNaoEncontrada ao validar cliente inexistente")
    public void deveriaLancarInformacaoNaoEncontradaAoValidarClienteInexistente() throws Exception {

        Integer idClienteInexistente = 1;

        when(serviceCliente.procurarPorId(idClienteInexistente)).thenThrow(new InformacaoNaoEncontrada("Cliente não encontrado"));

        assertThrows(InformacaoNaoEncontrada.class, () -> serviceEntrega.validarCliente(idClienteInexistente));
    }

    @Test
    @DisplayName("Deveria obter cliente por ID com sucesso")
    public void deveriaObterClientePorIdComSucesso() throws Exception {

        Integer idClienteExistente = 1;
        ClienteResponseDTO clienteDTO = new ClienteResponseDTO();
        clienteDTO.setId(idClienteExistente);
        Cliente clienteMock = new Cliente();
        clienteMock.setId(idClienteExistente);

        when(serviceCliente.procurarPorId(idClienteExistente)).thenReturn(clienteDTO);

        when(objectMapper.convertValue(clienteDTO, Cliente.class)).thenReturn(clienteMock);

        Cliente result = serviceEntrega.obterClientePorId(idClienteExistente);

        assertNotNull(result);
        assertEquals(clienteMock, result);
    }

    @Test
    @DisplayName("Deveria retornar null quando o cliente não é encontrado")
    public void deveriaRetornarNullQuandoClienteNaoEncontrado() throws Exception {
        Integer idCliente = 1;

        when(serviceCliente.procurarPorId(idCliente)).thenReturn(null);

        Cliente result = serviceEntrega.obterClientePorId(idCliente);

        assertNull(result);
    }

    @Test
    @DisplayName("Deveria obter endereço por ID com sucesso")
    public void deveriaObterEnderecoPorIdComSucesso() throws Exception {

        Integer idEndereco = 1;
        EnderecoResponseDTO enderecoDTO = new EnderecoResponseDTO(
                1, "35588000", "Rua 1", "100", "casa", "São Paulo", Estados.SP, Tipo.RESIDENCIAL, Ecossistema.INDIFERENTE, Ativo.A);
        Endereco enderecoMock = new Endereco();

        when(serviceEndereco.procurarPorIdEndereco(idEndereco)).thenReturn(enderecoDTO);
        when(objectMapper.convertValue(enderecoDTO, Endereco.class)).thenReturn(enderecoMock);

        Endereco result = serviceEntrega.obterEnderecoPorId(idEndereco);

        assertNotNull(result);
        assertEquals(enderecoMock, result);
    }

    @Test
    @DisplayName("Deveria retornar null quando o endereço não é encontrado")
    public void deveriaRetornarNullQuandoEnderecoNaoEncontrado() throws Exception {

        Integer idEndereco = 1;

        when(serviceEndereco.procurarPorIdEndereco(idEndereco)).thenReturn(null);

        Endereco result = serviceEntrega.obterEnderecoPorId(idEndereco);

        assertNull(result);
    }

    @Test
    @DisplayName("Deveria lançar exceção quando uma muda não estiver disponível ou não estiver ativa")
    public void deveriaLancarExcecaoQuandoMudaNaoDisponivel() throws Exception {
        Muda muda = new Muda();
        muda.setId(1);

        List<Muda> mudas = List.of(muda);

        when(serviceMudas.procurarPorIDEntidade(eq(1))).thenReturn(null);

        assertThrows(InformacaoNaoEncontrada.class, () -> serviceEntrega.validarMudas(mudas));
    }

    @Test
    @DisplayName("Deveria lançar exceção quando a quantidade de uma muda for insuficiente")
    public void deveriaLancarExcecaoQuandoQuantidadeInsuficiente() throws Exception {
        Muda muda = new Muda();
        muda.setId(1);
        muda.setEstoque(0);

        List<Muda> mudas = List.of(muda);

        when(serviceMudas.procurarPorIDEntidade(eq(1))).thenReturn(muda);

        assertThrows(InformacaoNaoEncontrada.class, () -> serviceEntrega.validarMudas(mudas));
    }

    @Test
    @DisplayName("Deveria adicionar uma muda à lista de mudas válidas")
    public void deveriaAdicionarMudaNaLista() throws Exception {

        Muda muda = new Muda();
        muda.setId(1);
        muda.setAtivo(Ativo.A);
        muda.setEstoque(10);
        when(serviceMudas.procurarPorIDEntidade(eq(1))).thenReturn(muda);

        List<Muda> mudas = Collections.singletonList(muda);
        List<Muda> mudaEntity = serviceEntrega.validarMudas(mudas);

        assertEquals(1, mudaEntity.size());
        assertTrue(mudaEntity.contains(muda));
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

    private static EnderecoResponseDTO gerarEnderecoDTO() {
        var enderecoDTO = new EnderecoResponseDTO(1,
                "123456789",
                "Rua ABC",
                "123",
                "Casa",
                "Porto Alegre",
                Estados.SP,
                Tipo.RESIDENCIAL,
                Ecossistema.COSTEIRO,
                Ativo.A);

        return enderecoDTO;
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

    private static Entrega gerarEntrega() {
        Entrega entrega = new Entrega();

        entrega.setId(1);
        entrega.setStatus(StatusEntrega.RECEBIDO);
        entrega.setDataPedido(LocalDate.now());
        entrega.setDataEntrega(null);

        Cliente cliente = gerarCliente();
        Endereco endereco = gerarEndereco();

        entrega.setCliente(cliente);
        entrega.setEndereco(endereco);

        return entrega;
    }

    private static List<Muda> gerarMudas() {
        List<Muda> mudas = new ArrayList<>();
        Muda muda1 = new Muda();
        muda1.setId(1);
        muda1.setEstoque(3);
        Muda muda2 = new Muda();
        muda2.setId(2);
        muda2.setEstoque(2);
        mudas.add(muda1);
        mudas.add(muda2);
        return mudas;
    }
}