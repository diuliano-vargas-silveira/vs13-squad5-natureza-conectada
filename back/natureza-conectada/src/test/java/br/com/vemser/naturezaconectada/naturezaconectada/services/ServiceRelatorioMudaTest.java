package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.AvaliacaoDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.RelatorioRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioResponseAdmin;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioResponseEspecialista;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TamanhoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import br.com.vemser.naturezaconectada.naturezaconectada.models.RelatorioMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.MudaRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.RelatorioMudaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ServiceRelatorio  - Test")
class ServiceRelatorioMudaTest {

    @Spy
    private MudaRepository mudaRepository;
    @Mock
    private  ServiceMudas serviceMudas;
    @Mock
    private  RelatorioMudaRepository relatorioMudaRepository;
    @Mock
    private  ServiceEspecialista serviceEspecialista;
    @Mock
    private  ServiceCliente serviceCliente;

    @Mock
    private  ObjectMapper objectMapper;

    @Spy
    @InjectMocks
    private ServiceRelatorioMuda serviceRelatorioMuda;

    private Cliente clienteLogado;

    private Especialista especialistalogado;


    @BeforeEach
    public void setup() {
        clienteLogado = new Cliente();
        clienteLogado.setId(1);
        clienteLogado.setNome("Rafael");

        especialistalogado = new Especialista();
        especialistalogado.setId(1);
        especialistalogado.setNome("Rafael");
    }

    @Test
    void adicionar() throws Exception {
        RelatorioRequestDTO requestDTOMock = retornarRequestDTO();
        RelatorioClienteDTO relatorioClienteDTOMock = retornarCliente();
        RelatorioMuda entityMock = retornarEntity();
        List<Muda> mudaMock = List.of(new Muda());
        mudaMock.get(0).setId(1);

        when(this.serviceCliente.getUsuarioLogado()).thenReturn(clienteLogado);
        when(this.serviceMudas.buscarMudaAtiva(1)).thenReturn(mudaMock.get(0));
        when(this.relatorioMudaRepository.save(any(RelatorioMuda.class))).thenReturn(entityMock);
        doReturn(relatorioClienteDTOMock).when(this.serviceRelatorioMuda).retornarDtoCliente(any(RelatorioMuda.class));

        RelatorioClienteDTO novoRelatorio = this.serviceRelatorioMuda.adicionar(requestDTOMock);

        assertNotNull(novoRelatorio);

    }

    @Test
    void editarRelatorio() throws Exception {

        RelatorioClienteDTO relatorioclienteDTOMock = retornarCliente();
        relatorioclienteDTOMock.setEstadoMuda("Diferente");
        RelatorioRequestDTO relatoriocreateDTOMock = retornarRequestDTO();
        relatoriocreateDTOMock.setEstadoMuda("Diferente");
        RelatorioMuda relatorioEntityMock = retornarEntity();
        relatorioEntityMock.setEstadoMuda("Diferente");
        List<RelatorioMuda> relatorioMock = List.of(retornarEntity());
        List<Muda> mudaMock = List.of(new Muda());
        mudaMock.get(0).setId(1);

        when(this.serviceCliente.getUsuarioLogado()).thenReturn(clienteLogado);
        when(this.relatorioMudaRepository.findByClienteIs(any(Cliente.class))).thenReturn(relatorioMock);
        when(this.serviceMudas.getByEntidade(1)).thenReturn(mudaMock.get(0));
        when(this.relatorioMudaRepository.save(any(RelatorioMuda.class))).thenReturn(relatorioEntityMock);
        doReturn(relatorioclienteDTOMock).when(this.serviceRelatorioMuda).retornarDtoCliente(any(RelatorioMuda.class));

        RelatorioClienteDTO retorno = this.serviceRelatorioMuda.editarRelatorio(1,relatoriocreateDTOMock);

        assertEquals(relatorioclienteDTOMock,retorno);
    }

    @Test
    void listar() {
        List<RelatorioMuda> lista = List.of(retornarEntity(),retornarEntity(),retornarEntity());

        when(this.relatorioMudaRepository.findAll()).thenReturn(lista);

        List<RelatorioResponseAdmin> listarRelatorios = this.serviceRelatorioMuda.listar();

        assertEquals(lista.size(),listarRelatorios.size());
    }

    @Test
    void avaliarRelatorio() throws Exception {
        Integer id = new Random().nextInt();
        AvaliacaoDTO avaliacaoMock = new AvaliacaoDTO();
        avaliacaoMock.setAvaliacao(1.45);
        avaliacaoMock.setSugestoes("mais agua");
        RelatorioMuda entityMock = retornarEntity();

        when(this.serviceEspecialista.getUsuarioLogado()).thenReturn(especialistalogado);
        doReturn(entityMock).when(this.serviceRelatorioMuda).procurarPorID(id);
        when(this.relatorioMudaRepository.save(any(RelatorioMuda.class))).thenReturn(entityMock);

        this.serviceRelatorioMuda.avaliarRelatorio(id,avaliacaoMock);

        verify(this.relatorioMudaRepository,times(1)).save(entityMock);




    }

    @Test
    void buscarRelatorio() {

    }
    @Test
    void relatorioEspecialista() throws Exception {
        Integer id = new Random().nextInt();
        List<RelatorioMuda> lista = List.of(retornarEntity(),retornarEntity());
        lista.get(0).setEspecialista(especialistalogado);
        lista.get(1).setEspecialista(especialistalogado);

        when(this.serviceEspecialista.procurarPorIDEntidade(id)).thenReturn(especialistalogado);
        when(this.relatorioMudaRepository.findByEspecialistaIs(especialistalogado)).thenReturn(lista);

        List<RelatorioResponseEspecialista> relatorios = this.serviceRelatorioMuda.relatorioEspecialista(id);

        assertEquals(lista.size(),relatorios.size());

    }
    @Test
    void retornarDtoCliente() {
        RelatorioMuda relatorioMudaMock = retornarEntity();
        RelatorioClienteDTO relatorioClienteDTOMock = retornarCliente();
        relatorioClienteDTOMock.setNomeEspecialista("Aguardando avaliação");
        RelatorioClienteDTO dto = this.serviceRelatorioMuda.retornarDtoCliente(relatorioMudaMock);


        assertEquals(relatorioClienteDTOMock.getNomeEspecialista(),dto.getNomeEspecialista());

    }

    @Test
    void buscarAbertos() {
        List<RelatorioMuda> relatorioEspecialistaMock =List.of(retornarEntity(),retornarEntity(),retornarEntity());

        when(this.relatorioMudaRepository.findByAvaliacaoIsNull()).thenReturn(relatorioEspecialistaMock);

        List<RelatorioResponseEspecialista> buscarAberto = this.serviceRelatorioMuda.buscarAbertos();

        assertEquals(relatorioEspecialistaMock.size(),buscarAberto.size());

    }

    public RelatorioMuda retornarEntity(){
        RelatorioMuda relatorio = new RelatorioMuda();
        Muda muda = new Muda(1,25, TipoMuda.ARVORE,"Arvore","arvoris", TamanhoMuda.PEQUENO, Ecossistema.COSTEIRO,"uma arvore", Ativo.A,null,null);
        Cliente cliente = new Cliente();
        cliente.setId(2);
        cliente.setNome("Rafael");
        relatorio.setId(1);
        relatorio.setEspecialista(null);
        relatorio.setEstadoMuda("Está com folhas verde");
        relatorio.setCliente(cliente);
        relatorio.setMuda(muda);
        return  relatorio;
    }
    public RelatorioClienteDTO retornarCliente(){
        RelatorioClienteDTO relatorio = new RelatorioClienteDTO();
        Muda muda = new Muda(1,25, TipoMuda.ARVORE,"Arvore","arvoris", TamanhoMuda.PEQUENO, Ecossistema.COSTEIRO,"uma arvore", Ativo.A,null,null);
        Cliente cliente = new Cliente();
        cliente.setId(2);
        cliente.setNome("Rafael");
        relatorio.setId(1);
        relatorio.setNomeEspecialista(null);
        relatorio.setEstadoMuda("Está com folhas verde");
        relatorio.setCliente(cliente);
        relatorio.setMuda(muda);
        return  relatorio;
    }

    public RelatorioRequestDTO retornarRequestDTO(){
        RelatorioRequestDTO relatorio = new RelatorioRequestDTO();
        Muda muda = new Muda(1,25, TipoMuda.ARVORE,"Arvore","arvoris", TamanhoMuda.PEQUENO, Ecossistema.COSTEIRO,"uma arvore", Ativo.A,null,null);
        Cliente cliente = new Cliente();
        relatorio.setIdMuda(1);
        cliente.setId(2);
        cliente.setNome("Rafael");
        relatorio.setEstadoMuda("Está com folhas verde");
        return  relatorio;
    }
}