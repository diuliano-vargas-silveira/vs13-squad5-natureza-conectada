package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EspecialistaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EspecialistaDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.models.RelatorioMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EspecialistaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ServiceEspecialistaTeste - Test")
class ServiceEspecialistaTest {

    @Mock
    private EspecialistaRepository especialistaRepository;
    @Mock
    private ServiceUsuario serviceUsuario;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private PasswordEncoder encoder;
    @Spy
    @InjectMocks
    private ServiceEspecialista serviceEspecialista;

    private Especialista especialista;
    private EspecialistaCreateDTO especialistaCreateDTO;

    private EspecialistaCreateDTO especialistaRetornoDTO;
    private EspecialistaDTO especialistaDTO;
    @BeforeEach
    void setUp(){
        especialista = new Especialista();
        especialista.setId(1);
        especialista.setNome("Rafa");
        especialista.setSenha(encoder.encode("123"));

        especialistaCreateDTO = new EspecialistaCreateDTO();
        especialistaCreateDTO.setId(1);
        especialistaCreateDTO.setNome("Rafa");
        especialistaCreateDTO.setSenha("123");

        especialistaRetornoDTO = new EspecialistaCreateDTO();
        especialistaRetornoDTO.setId(1);
        especialistaRetornoDTO.setNome("Rafa");
        especialistaRetornoDTO.setSenha("123");


        especialistaDTO = new EspecialistaDTO();
        especialistaDTO.setId(1);
        especialistaDTO.setNome("Rafa");


    }

    @Test
    void adicionar() throws Exception {

        doReturn(especialista).when(this.serviceEspecialista).retornarEntidade(any(EspecialistaCreateDTO.class));
        when(this.especialistaRepository.save(any(Especialista.class))).thenReturn(especialista);
        doReturn(especialistaRetornoDTO).when(this.serviceEspecialista).retornarDto(any(Especialista.class));

        EspecialistaCreateDTO novoEspecialista = this.serviceEspecialista.adicionar(especialistaCreateDTO);

        assertEquals(especialistaCreateDTO,novoEspecialista);


    }

    @Test
    void mudarAtivoEspecialista() throws Exception {
        Integer id = new Random().nextInt();
        especialista.setAtivo(Ativo.D);

        Especialista desativo = new Especialista();
        desativo.setAtivo(Ativo.A);

        doReturn(especialista).when(this.serviceEspecialista).procurarPorIDEntidade(id);

        this.serviceEspecialista.mudarAtivoEspecialista(id);

        assertEquals(desativo.getAtivo(),especialista.getAtivo());

    }

    @Test
    void editar() throws Exception {
        Integer id = new Random().nextInt();
        especialistaDTO.setNome("novo Nome");


        doReturn(especialista).when(this.serviceEspecialista).procurarPorIDEntidade(id);

        doReturn(especialistaCreateDTO).when(this.serviceEspecialista).retornarDto(any(Especialista.class));

        EspecialistaCreateDTO especialistaEditado = this.serviceEspecialista.editar(id,especialistaDTO);

        assertEquals(especialistaCreateDTO,especialistaEditado);

    }

    @Test
    void procurarPorIdCompleto() throws Exception {
        Optional<Especialista> empty = Optional.empty();

        when(this.especialistaRepository.findById(anyInt())).thenReturn(empty);

        assertThrows(RegraDeNegocioException.class,() -> this.serviceEspecialista.procurarPorIdCompleto(anyInt()));
    }

    @Test
    void listarTodos() throws Exception {
        List<EspecialistaDTO> listaDTO = List.of(especialistaDTO);
        List<Especialista> listaEspecialista = List.of(especialista);

        when(this.especialistaRepository.findAll()).thenReturn(listaEspecialista);
       List<EspecialistaDTO> retorno = this.serviceEspecialista.listarTodos();

       assertEquals(listaDTO.size(),retorno.size());
    }

    @Test
    void procurarPorIdBasica() throws Exception {
        Integer id = new Random().nextInt();

        when( this.especialistaRepository.findById(id)).thenReturn(Optional.ofNullable(especialista));

        when(this.objectMapper.convertValue(especialista, EspecialistaDTO.class)).thenReturn(especialistaDTO);

        EspecialistaDTO especBuscado = this.serviceEspecialista.procurarPorIdBasica(id);

        assertEquals(especialistaDTO,especBuscado);

    }

    @Test
    void procurarPorIdEntidade() throws Exception {
        Integer id = new Random().nextInt();

        when( this.especialistaRepository.findById(id)).thenReturn(Optional.ofNullable(especialista));

        Especialista especBuscado = this.serviceEspecialista.procurarPorIDEntidade(id);

        assertEquals(especialista,especBuscado);
    }

    @Test
    void getUsuarioLogado() throws Exception {
        Integer id = new Random().nextInt();

        when(this.serviceUsuario.getIdLoggedUser()).thenReturn(id);
        doReturn(especialista).when(this.serviceEspecialista).procurarPorIDEntidade(id);

        Especialista retorno = this.serviceEspecialista.getUsuarioLogado();

        assertEquals(especialista,retorno);
    }

    @Test
    void retornarEntidade(){

        when(this.objectMapper.convertValue(especialistaCreateDTO, Especialista.class)).thenReturn(especialista);

        Especialista retorno = this.serviceEspecialista.retornarEntidade(especialistaCreateDTO);

        assertEquals(especialista,retorno);

    }
}