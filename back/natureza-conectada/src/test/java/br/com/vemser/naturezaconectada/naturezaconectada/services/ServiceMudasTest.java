package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.MudaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.MudaDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TamanhoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.MudaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ServiceMudas  - Test")
class ServiceMudasTest {

    @Mock
    private MudaRepository mudaRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ServiceCliente serviceCliente;

    @Spy
    @InjectMocks
    private ServiceMudas serviceMudas;

    @Mock
    private ServiceLog serviceLog;

    private static MudaDTO retornarDTO() {
        MudaDTO dto = new MudaDTO();
        dto.setId(1);
        dto.setDescricao("Uma muda");
        dto.setEcossistema(Ecossistema.COSTEIRO);
        dto.setPorte(TamanhoMuda.MEDIO);
        dto.setTipo(TipoMuda.ARVORE);
        dto.setNomeCientifico("Mudis arvoris");
        dto.setNome("Uma muda de arvore");
        return dto;
    }

    private static MudaCreateDTO retornarCreateDTO() {
        MudaCreateDTO dto = new MudaCreateDTO();
        dto.setId(1);
        dto.setDescricao("Uma muda");
        dto.setEcossistema(Ecossistema.COSTEIRO);
        dto.setPorte(TamanhoMuda.MEDIO);
        dto.setTipo(TipoMuda.ARVORE);
        dto.setNomeCientifico("Mudis arvoris");
        dto.setNome("Uma muda de arvore");
        dto.setAtivo(Ativo.A);
        dto.setEstoque(10);
        return dto;
    }

    private static Muda retornarEntity() {
        Muda muda = new Muda();
        muda.setId(1);
        muda.setDescricao("Uma muda");
        muda.setEcossistema(Ecossistema.COSTEIRO);
        muda.setPorte(TamanhoMuda.MEDIO);
        muda.setTipo(TipoMuda.ARVORE);
        muda.setNomeCientifico("Mudis arvoris");
        muda.setNome("Uma muda de arvore");
        muda.setAtivo(Ativo.A);
        muda.setEstoque(10);

        return muda;
    }

    @Test
    @DisplayName("Deveria criar uma nova Muda com sucesso")
    void adicionar() throws Exception {
        MudaCreateDTO createDTOMock = retornarCreateDTO();
        Muda mudaEntityMock = retornarEntity();

        when(this.objectMapper.convertValue(createDTOMock, Muda.class)).thenReturn(mudaEntityMock);
        when(this.mudaRepository.save(any())).thenReturn(mudaEntityMock);
        when(this.objectMapper.convertValue(mudaEntityMock, MudaCreateDTO.class)).thenReturn(createDTOMock);

        MudaCreateDTO mudaCreateDTO = this.serviceMudas.adicionar(createDTOMock);

        assertNotNull(mudaCreateDTO);
        assertEquals(mudaCreateDTO, mudaCreateDTO);
    }

    @Test
    @DisplayName("Deveria mudar Ativo da muda")
    void mudarAtivoMuda() throws Exception {
        Integer id = new Random().nextInt();
        Muda mudaEntityMock = retornarEntity();
        mudaEntityMock.setAtivo(Ativo.D);

        doReturn(mudaEntityMock).when(this.serviceMudas).procurarPorIDEntidade(id);

        this.serviceMudas.mudarAtivoMuda(id);

        assertEquals(Ativo.A, mudaEntityMock.getAtivo());
        verify(this.mudaRepository, times(1)).save(mudaEntityMock);
    }

    @Test
    @DisplayName("Deveria retornar uma muda entidade")
    void procurarPorIDEntidade() throws Exception {
        Integer id = new Random().nextInt();
        Muda mudaEntityMock = retornarEntity();

        when(this.mudaRepository.findById(id)).thenReturn(Optional.of(mudaEntityMock));

        Muda mudaEncontrada = this.serviceMudas.procurarPorIDEntidade(id);

        assertEquals(mudaEntityMock, mudaEncontrada);
    }

    @Test
    @DisplayName("Deveria retornar  uma muda DTO")
    void procurarPorIdDto() throws Exception {
        Integer id = new Random().nextInt();
        Muda mudaEntityMock = retornarEntity();
        MudaDTO mudaDTOMock = retornarDTO();

        when(this.mudaRepository.findById(id)).thenReturn(Optional.of(mudaEntityMock));
        when(this.objectMapper.convertValue(mudaEntityMock, MudaDTO.class)).thenReturn(mudaDTOMock);

        MudaDTO mudaEncontrada = this.serviceMudas.procurarPorIdDto(id);

        assertEquals(mudaDTOMock, mudaEncontrada);
    }

    @Test
    @DisplayName("Deveria retornar uma muda Ativa ")
    void buscarMudaAtiva() throws Exception {
        Integer id = new Random().nextInt();
        Muda mudaEntityMock = retornarEntity();

        when(this.mudaRepository.findByAtivoAndId(Ativo.A, id)).thenReturn(Optional.of(mudaEntityMock));

        Muda mudaEncontrada = this.serviceMudas.buscarMudaAtiva(id);

        assertNotNull(mudaEncontrada);
        assertEquals(mudaEntityMock, mudaEncontrada);

    }

    @Test
    @DisplayName("Deveria retornar uma muda Editada ")
    void editarmuda() throws Exception {
        Integer id = new Random().nextInt();
        Muda mudaEntityMock = retornarEntity();

        MudaCreateDTO mudaCreateEditadoMock = retornarCreateDTO();
        mudaCreateEditadoMock.setNome("Novo Nome");
        mudaCreateEditadoMock.setEcossistema(Ecossistema.INDIFERENTE);

        Muda mudaEditadaMock = retornarEntity();
        mudaEditadaMock.setNome("Novo Nome");
        mudaEditadaMock.setEcossistema(Ecossistema.INDIFERENTE);

        doReturn(mudaEntityMock).when(this.serviceMudas).procurarPorIDEntidade(id);
        when(this.mudaRepository.save(any())).thenReturn(mudaEditadaMock);
        doReturn(mudaCreateEditadoMock).when(this.serviceMudas).retornarDto(any());

        MudaCreateDTO mudaEditada = this.serviceMudas.editarmuda(id, mudaCreateEditadoMock);

        assertNotNull(mudaEditada);
        assertEquals(mudaCreateEditadoMock, mudaEditada);
    }

    @Test
    @DisplayName("Deveria retornar uma lista de mudas ativas com o mesmo tamanho da lista esperada ")
    void listarMudasAtivas() throws Exception {
        List<MudaDTO> mudasMock = List.of(retornarDTO(), retornarDTO(), retornarDTO());
        List<Muda> mudasEntityMock = List.of(retornarEntity(), retornarEntity(), retornarEntity());

        when(this.mudaRepository.findByAtivoIs(Ativo.A)).thenReturn(mudasEntityMock);


        List<MudaDTO> mudasEncontradas = this.serviceMudas.listarMudasAtivas();

        assertNotNull(mudasEncontradas);
        assertEquals(mudasMock.size(), mudasEncontradas.size());

    }

    @Test
    @DisplayName("Deveria retornar uma lista de mudas por ecossistema com o mesmo tamanho da lista esperada ")
    void buscarPorEco() throws Exception {
        List<MudaDTO> mudasMock = List.of(retornarDTO(), retornarDTO(), retornarDTO());
        List<Muda> mudasEntityMock = List.of(retornarEntity(), retornarEntity(), retornarEntity());

        when(this.mudaRepository.findByEcossistemaIs(any())).thenReturn(mudasEntityMock);


        List<MudaDTO> mudasEncontradas = this.serviceMudas.buscarPorEco(Ecossistema.COSTEIRO);

        assertNotNull(mudasEncontradas);
        assertEquals(mudasMock.size(), mudasEncontradas.size());
    }

    @Test
    @DisplayName("Deveria retornar uma nova muda criada ")
    void novaMuda() throws Exception {
        Muda mudaMock = retornarEntity();

        MudaCreateDTO mudaCriadaMock = retornarCreateDTO();

        doReturn(mudaMock).when(this.serviceMudas).retornarEntidade(any());
        doReturn(mudaCriadaMock).when(this.serviceMudas).retornarDto(any());

        MudaCreateDTO mudaCriada = this.serviceMudas.novaMuda(mudaCriadaMock);

        verify(this.mudaRepository, times(1)).save(mudaMock);
        assertNotNull(mudaCriada);
        assertEquals(mudaCriadaMock, mudaCriada);

    }

    @Test
    @DisplayName("Deveria lançar a excessão informação não encontrada ")
    void getByEntidadeNaoEncontrada() {
        Optional<Muda> vazio = Optional.empty();

        when(this.mudaRepository.findById(2)).thenReturn(vazio);

        Assertions.assertThrows(InformacaoNaoEncontrada.class, () -> this.serviceMudas.getByEntidade(2));
    }

    @Test
    @DisplayName("Deveria Retornar uma entidade encontrada  ")
    void getByEntidade() throws Exception {
        Optional<Muda> muda = Optional.of(retornarEntity());

        when(this.mudaRepository.findById(2)).thenReturn(muda);

        Muda mudaEncontrada = this.serviceMudas.getByEntidade(2);
        assertEquals(muda.get(), mudaEncontrada);
    }

    @Test
    @DisplayName("Deveria conferir se a mudas pertencem ao cliente e seguir o codigo")
    void confereMudaCliente() throws Exception {
        Integer id = new Random().nextInt();
        Cliente clienteMock = new Cliente();
        clienteMock.setSenha("123456");
        clienteMock.setMudas(List.of(retornarEntity()));
        clienteMock.setAtivo(Ativo.A);
        when(this.serviceCliente.buscarPorIdEntidade(id)).thenReturn(clienteMock);
        when(this.mudaRepository.findByClienteAndIdIs(clienteMock, id)).thenReturn(Optional.ofNullable(clienteMock.getMudas()));

        Assertions.assertDoesNotThrow(() -> this.serviceMudas.confereMudaCliente(id, id));

    }

    @Test
    @DisplayName("Deveria lançar a excessão Regra de negocio(muda não pertence ao cliente ")
    void confereMudaClienteNaoPertence() throws Exception {
        Integer id = new Random().nextInt();
        Optional<List<Muda>> lista = Optional.empty();
        Cliente clienteMock = new Cliente();
        clienteMock.setSenha("123456");
        clienteMock.setMudas(List.of(retornarEntity()));
        clienteMock.setAtivo(Ativo.A);

        when(this.serviceCliente.buscarPorIdEntidade(id)).thenReturn(clienteMock);
        when(this.mudaRepository.findByClienteAndIdIs(clienteMock, id)).thenReturn(lista);

        Assertions.assertThrows(RegraDeNegocioException.class, () -> this.serviceMudas.confereMudaCliente(id, id));

    }

}