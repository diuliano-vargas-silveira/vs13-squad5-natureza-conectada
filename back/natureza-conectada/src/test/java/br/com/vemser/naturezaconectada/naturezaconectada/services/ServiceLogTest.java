package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.LogMudasCriadasDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.LogUsuarioDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.models.LogContaUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.models.LogMudasCriadas;
import br.com.vemser.naturezaconectada.naturezaconectada.models.LogUsuarios;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.LogMudasCriadasRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.LogUsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ServiceLog  - Test")
class ServiceLogTest {

    @Mock
    private LogUsuarioRepository logUsuarioRepository;
    @Mock
    private LogMudasCriadasRepository logMudasRepository;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private ServiceLog serviceLog;

    @Test
    @DisplayName("Deveria criar um log de usuário com sucesso")
    public void deveriaCriarLogUsuarioComSucesso() {

        LogUsuarios logUsuarios = new LogUsuarios();

        serviceLog.criarLogUsuario(logUsuarios);

        assertNotNull(logUsuarios.getDataDeCriacao());

        verify(logUsuarioRepository, times(1)).save(logUsuarios);
    }

    @Test
    @DisplayName("Deveria criar um log de mudas com sucesso")
    public void deveriaCriarLogMudasComSucesso() {

        LogMudasCriadas logMuda = new LogMudasCriadas();

        serviceLog.criarLogMudas(logMuda);

        assertNotNull(logMuda.getCriadoEm());

        verify(logMudasRepository, times(1)).save(logMuda);
    }

    @Test
    @DisplayName("Deveria retornar o total de usuários por tipo")
    public void deveriaRetornarTotalDeUsuarioPorTipo() {

        List<LogContaUsuario> logContaUsuariosMock = new ArrayList<>();

        LogContaUsuario log1 = new LogContaUsuario();
        log1.setTipoUsuario(TipoUsuario.ADMIN);
        log1.setQuantidade(5);

        LogContaUsuario log2 = new LogContaUsuario();
        log2.setTipoUsuario(TipoUsuario.CLIENTE);
        log2.setQuantidade(10);

        logContaUsuariosMock.add(log1);
        logContaUsuariosMock.add(log2);

        when(logUsuarioRepository.groupByTipoUsuarioAndCount()).thenReturn(logContaUsuariosMock);

        LogUsuarioDTO adminDTO = new LogUsuarioDTO();
        adminDTO.setTipoUsuario(TipoUsuario.ADMIN);
        LogUsuarioDTO clienteDTO = new LogUsuarioDTO();
        clienteDTO.setTipoUsuario(TipoUsuario.CLIENTE);

        when(objectMapper.convertValue(log1, LogUsuarioDTO.class)).thenReturn(adminDTO);
        when(objectMapper.convertValue(log2, LogUsuarioDTO.class)).thenReturn(clienteDTO);

        List<LogUsuarioDTO> resultado = serviceLog.totalDeUsuarioPorTipo();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("ADMIN", resultado.get(0).getTipoUsuario().toString());
        assertEquals("CLIENTE", resultado.get(1).getTipoUsuario().toString());
    }

    @Test
    @DisplayName("Deveria listar as mudas criadas por um admin")
    public void deveriaListarMudasCriadasPorAdmin() {

        List<LogMudasCriadas> logMudasCriadasMock = new ArrayList<>();

        LogMudasCriadas log1 = new LogMudasCriadas();
        log1.setNomeDoAdmin("Admin1");

        LogMudasCriadas log2 = new LogMudasCriadas();
        log2.setNomeDoAdmin("Admin1");

        logMudasCriadasMock.add(log1);
        logMudasCriadasMock.add(log2);

        when(logMudasRepository.findAllBynomeDoAdminLikeIgnoreCase("Admin1")).thenReturn(logMudasCriadasMock);

        LogMudasCriadasDTO logDTO1 = new LogMudasCriadasDTO();
        logDTO1.setNomeDoAdmin("Admin1");

        LogMudasCriadasDTO logDTO2 = new LogMudasCriadasDTO();
        logDTO2.setNomeDoAdmin("Admin1");

        when(objectMapper.convertValue(log1, LogMudasCriadasDTO.class)).thenReturn(logDTO1);
        when(objectMapper.convertValue(log2, LogMudasCriadasDTO.class)).thenReturn(logDTO2);

        List<LogMudasCriadasDTO> resultado = serviceLog.listarMudasCriadasPorNome("Admin1");

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Admin1", resultado.get(0).getNomeDoAdmin());
        assertEquals("Admin1", resultado.get(1).getNomeDoAdmin());
    }



}