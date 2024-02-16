package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.relatorios.RelatorioQuantidadeUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceUsuarioTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private ServiceUsuario serviceUsuario;

    @Test
    @DisplayName("Deveria retornar o usuário pelo email")
    public void deveriaRetornarUsuarioPeloEmail() {

        String email = "lucas@test.com";
        Usuario usuarioMock = new Cliente();
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuarioMock));

        Optional<Usuario> usuarioEncontrado = serviceUsuario.findByLogin(email);

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals(usuarioMock, usuarioEncontrado.get());
    }

    @Test
    @DisplayName("Deveria retornar vazio se o usuário não for encontrado pelo email")
    public void deveriaRetornarVazioSeUsuarioNaoEncontradoPeloEmail() {

        String email = "lucas@test.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Usuario> usuarioEncontrado = serviceUsuario.findByLogin(email);

        assertTrue(usuarioEncontrado.isEmpty());
    }

    @Test
    @DisplayName("Deveria listar todos os usuários")
    public void deveriaListarTodosUsuarios() throws Exception {

        List<Usuario> usuariosMock = criarUsuariosMock();
        when(usuarioRepository.findAll()).thenReturn(usuariosMock);

        List<UsuarioResponseDTO> usuariosEsperados = usuariosMock.stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioResponseDTO.class))
                .collect(Collectors.toList());

        List<UsuarioResponseDTO> usuariosListados = serviceUsuario.listarTodos();

        assertEquals(usuariosEsperados, usuariosListados);
    }

    @Test
    @DisplayName("Deveria lançar exceção quando não houver usuários para listar")
    public void deveriaLancarExcecaoQuandoNaoHouverUsuariosParaListar() {

        when(usuarioRepository.findAll()).thenReturn(List.of());

        assertThrows(RegraDeNegocioException.class, () -> {
            serviceUsuario.listarTodos();
        });
    }

    @Test
    @DisplayName("Deveria listar todos os usuários ativos")
    public void deveriaListarTodosUsuariosAtivos() throws RegraDeNegocioException {

        List<UsuarioResponseDTO> usuariosAtivosMock = criarUsuariosMock().stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
        when(usuarioRepository.findAllUsuariosAtivos()).thenReturn(usuariosAtivosMock);

        List<UsuarioResponseDTO> usuariosAtivosEsperados = usuariosAtivosMock;

        List<UsuarioResponseDTO> usuariosAtivosListados = serviceUsuario.listarUsuariosAtivos();

        assertEquals(usuariosAtivosEsperados, usuariosAtivosListados);
    }

    @Test
    @DisplayName("Deveria lançar exceção quando não houver usuários ativos para listar")
    public void deveriaLancarExcecaoQuandoNaoHouverUsuariosAtivosParaListar() {

        when(usuarioRepository.findAllUsuariosAtivos()).thenReturn(List.of());

        assertThrows(RegraDeNegocioException.class, () -> {
            serviceUsuario.listarUsuariosAtivos();
        });
    }

    @Test
    @DisplayName("Deveria procurar usuário por email com sucesso")
    public void deveriaProcurarUsuarioPorEmailComSucesso() throws Exception {

        String email = "lucas@test.com";
        Usuario usuarioMock = new Cliente();
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuarioMock));

        UsuarioResponseDTO usuarioResponseDTOMock = new UsuarioResponseDTO();
        when(objectMapper.convertValue(usuarioMock, UsuarioResponseDTO.class)).thenReturn(usuarioResponseDTOMock);

        UsuarioResponseDTO usuarioRetornado = serviceUsuario.procurarPorEmail(email);

        assertNotNull(usuarioRetornado);
        assertEquals(usuarioResponseDTOMock, usuarioRetornado);
    }

    @Test
    @DisplayName("Deveria lançar exceção ao procurar por email de usuário inexistente")
    public void deveriaLancarExcecaoAoProcurarPorEmailDeUsuarioInexistente() {

        String email = "lucas@test.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> {
            serviceUsuario.procurarPorEmail(email);
        });
    }

    @Test
    @DisplayName("Deveria gerar relatório de quantidade de usuários")
    void testGerarRelatorio() {

        List<RelatorioQuantidadeUsuario> relatorioEsperado = new ArrayList<>();

        relatorioEsperado.add(mockRelatorioQuantidadeUsuario("Admin", 5));
        relatorioEsperado.add(mockRelatorioQuantidadeUsuario("Cliente", 10));

        when(usuarioRepository.relatorioParaAdmin()).thenReturn(relatorioEsperado);

        List<RelatorioQuantidadeUsuario> resultado = serviceUsuario.gerarRelatorio();

        assertEquals(relatorioEsperado, resultado);
    }

    private List<Usuario> criarUsuariosMock() {
        List<Usuario> usuarioList = new ArrayList<>();

        Cliente cliente01 = new Cliente();
        cliente01.setId(1);
        cliente01.setNome("Lucas");
        cliente01.setEmail("lucas@gmail.com");
        cliente01.setSenha("123");
        cliente01.setTipoUsuario(TipoUsuario.CLIENTE);
        cliente01.setAtivo(Ativo.A);
        cliente01.setCpf("12312312350");
        cliente01.setContatos(null);
        cliente01.setEnderecos(null);

        Cliente cliente02 = new Cliente();
        cliente02.setId(2);
        cliente02.setNome("Ana");
        cliente02.setEmail("ana@gmail.com");
        cliente02.setSenha("456");
        cliente02.setTipoUsuario(TipoUsuario.CLIENTE);
        cliente02.setAtivo(Ativo.A);
        cliente02.setCpf("45645645650");
        cliente02.setContatos(null);
        cliente02.setEnderecos(null);

        usuarioList.add(cliente01);
        usuarioList.add(cliente02);

        return usuarioList;
    }

    private RelatorioQuantidadeUsuario mockRelatorioQuantidadeUsuario(String usuario, Integer quantidade) {
        RelatorioQuantidadeUsuario relatorio = Mockito.mock(RelatorioQuantidadeUsuario.class);
        return relatorio;
    }
}