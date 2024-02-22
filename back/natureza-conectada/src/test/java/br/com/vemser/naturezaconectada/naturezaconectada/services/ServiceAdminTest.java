package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.AdminRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.AdminResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Admin;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.AdminRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceAdminTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ServiceLog serviceLog;

    @InjectMocks
    private ServiceAdmin serviceAdmin;

    @Test
    @DisplayName("Deveria adicionar um novo admin com sucesso")
    public void deveriaAdicionarAdminComSucesso() throws Exception {

        AdminRequestDTO adminRequestDTOMock = new AdminRequestDTO();
        gerarAdminRequest(adminRequestDTOMock);

        AdminResponseDTO adminResponseDTOMock = new AdminResponseDTO();
        gerarAdminResponse(adminResponseDTOMock);

        Admin adminMock = new Admin();
        gerarAdmin(adminMock);

        when(adminRepository.findByEmail(adminRequestDTOMock.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(adminRequestDTOMock.getSenha())).thenReturn("oaihfoisahfsh");
        when(adminRepository.save(any())).thenReturn(adminMock);
        when(objectMapper.convertValue(any(), eq(AdminResponseDTO.class))).thenReturn(adminResponseDTOMock);

        AdminResponseDTO adminResponseDTO = serviceAdmin.adicionar(adminRequestDTOMock);

        assertNotNull(adminResponseDTO);
        assertEquals(adminRequestDTOMock.getNome(), adminResponseDTO.getNome());
        assertEquals(adminRequestDTOMock.getEmail(), adminResponseDTO.getEmail());
        assertEquals(adminRequestDTOMock.getCpf(), adminResponseDTO.getCpf());
        assertEquals(TipoUsuario.ADMIN, adminResponseDTO.getTipoUsuario());
        assertEquals(Ativo.A, adminResponseDTO.getAtivo());
    }

    @Test
    @DisplayName("Deveria lançar exceção ao tentar adicionar um admin com email existente")
    public void deveriaLancarExcecaoAoAdicionarAdminComEmailExistente() {

        AdminRequestDTO adminRequestDTO = new AdminRequestDTO();
        adminRequestDTO.setEmail("lucas@teste.com");

        Admin adminExistente = new Admin();

        when(adminRepository.findByEmail(adminRequestDTO.getEmail())).thenReturn(adminExistente);

        assertThrows(RegraDeNegocioException.class, () -> serviceAdmin.adicionar(adminRequestDTO));
    }

    @Test
    @DisplayName("Deveria alterar o status ativo de um admin com sucesso")
    public void deveriaAlterarStatusAtivoDeAdminComSucesso() throws RegraDeNegocioException {

        int idAdmin = 1;
        AdminRequestDTO adminRequestDTO = new AdminRequestDTO();
        adminRequestDTO.setAtivo(Ativo.D);

        Admin adminExistente = new Admin();
        gerarAdmin(adminExistente);

        when(adminRepository.findById(idAdmin)).thenReturn(Optional.of(adminExistente));
        when(adminRepository.save(any())).thenReturn(adminExistente);

        AdminResponseDTO adminResponseDTO = serviceAdmin.alterarStatusAtivo(idAdmin, adminRequestDTO);

        assertEquals(adminRequestDTO.getAtivo(), adminExistente.getAtivo());
    }

    @Test
    @DisplayName("Deveria lançar exceção ao tentar alterar o status ativo de um admin inexistente")
    public void deveriaLancarExcecaoAoTentarAlterarStatusAtivoDeAdminInexistente() {

        int idAdminInexistente = 1;
        AdminRequestDTO adminRequestDTO = new AdminRequestDTO();
        adminRequestDTO.setAtivo(Ativo.D);

        when(adminRepository.findById(idAdminInexistente)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> serviceAdmin.alterarStatusAtivo(idAdminInexistente, adminRequestDTO));
    }

    @Test
    @DisplayName("Deveria editar um admin com sucesso")
    public void deveriaEditarAdminComSucesso() throws Exception {

        int idAdminExistente = 1;
        AdminRequestDTO adminRequestDTOMock = new AdminRequestDTO();
        gerarAdminRequest(adminRequestDTOMock);

        Admin adminMock = new Admin();
        gerarAdmin(adminMock);

        AdminResponseDTO adminResponseDTOMock = new AdminResponseDTO();
        gerarAdminResponse(adminResponseDTOMock);

        when(adminRepository.findById(idAdminExistente)).thenReturn(Optional.of(adminMock));
        when(passwordEncoder.encode(adminRequestDTOMock.getSenha())).thenReturn("oaihfoisahfsh");
        when(adminRepository.save(any())).thenReturn(adminMock);
        when(objectMapper.convertValue(any(), eq(AdminResponseDTO.class))).thenReturn(adminResponseDTOMock);

        AdminResponseDTO adminResponseDTO = serviceAdmin.editar(idAdminExistente, adminRequestDTOMock);

        assertNotNull(adminResponseDTO);
        assertEquals(adminRequestDTOMock.getNome(), adminResponseDTO.getNome());
        assertEquals(adminRequestDTOMock.getEmail(), adminResponseDTO.getEmail());
        assertEquals(adminRequestDTOMock.getCpf(), adminResponseDTO.getCpf());
        assertEquals(Ativo.A, adminResponseDTO.getAtivo());
    }

    @Test
    @DisplayName("Deveria lançar exceção ao tentar editar um admin inexistente")
    public void deveriaLancarExcecaoAoTentarEditarAdminInexistente() {

        int idAdminInexistente = 1;
        AdminRequestDTO adminRequestDTO = new AdminRequestDTO();

        when(adminRepository.findById(idAdminInexistente)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> serviceAdmin.editar(idAdminInexistente, adminRequestDTO));
    }

    @Test
    @DisplayName("Deveria procurar um admin por ID com sucesso")
    public void deveriaProcurarAdminPorIdComSucesso() throws Exception {

        int idAdminExistente = 1;
        AdminResponseDTO adminResponseDTOMock = new AdminResponseDTO();
        gerarAdminResponse(adminResponseDTOMock);

        Admin adminMock = new Admin();
        gerarAdmin(adminMock);

        when(adminRepository.findById(idAdminExistente)).thenReturn(Optional.of(adminMock));
        when(objectMapper.convertValue(adminMock, AdminResponseDTO.class)).thenReturn(adminResponseDTOMock);

        AdminResponseDTO adminResponseDTO = serviceAdmin.procurarPorId(idAdminExistente);

        assertNotNull(adminResponseDTO);
        assertEquals(adminResponseDTOMock, adminResponseDTO);
    }

    @Test
    @DisplayName("Deveria lançar exceção ao tentar procurar um admin por ID inexistente")
    public void deveriaLancarExcecaoAoProcurarAdminPorIdInexistente() {

        int idAdminInexistente = 1;

        when(adminRepository.findById(idAdminInexistente)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> serviceAdmin.procurarPorId(idAdminInexistente));
    }

    @Test
    @DisplayName("Deveria listar todos os admins")
    public void deveriaListarTodosAdmins() throws Exception {
        List<Admin> adminList = new ArrayList<>();
        Admin admin1 = new Admin();
        gerarAdmin(admin1);
        Admin admin2 = new Admin();
        gerarAdmin(admin2);
        adminList.add(admin1);
        adminList.add(admin2);

        List<Admin> adminsMock = adminList;
        when(adminRepository.findAll()).thenReturn(adminsMock);

        List<AdminResponseDTO> adminsEsperados = adminsMock.stream()
                .map(adminEntity -> objectMapper.convertValue(adminEntity, AdminResponseDTO.class))
                .collect(Collectors.toList());

        List<AdminResponseDTO> adminsListados = serviceAdmin.listarTodos();

        assertEquals(adminsEsperados, adminsListados);
    }

    @Test
    @DisplayName("Deveria lançar exceção quando não houver admins cadastrados")
    public void deveriaLancarExcecaoQuandoNaoHouverAdmins() throws Exception {

        when(adminRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(RegraDeNegocioException.class, () -> serviceAdmin.listarTodos());
    }

    private static void gerarAdminRequest(AdminRequestDTO adminRequestDTOMock) {
        adminRequestDTOMock.setNome("Lucas");
        adminRequestDTOMock.setEmail("lucas@teste.com");
        adminRequestDTOMock.setCpf("12345678901");
        adminRequestDTOMock.setSenha("senha123");
    }

    private static void gerarAdminResponse(AdminResponseDTO adminResponseDTOMock) {
        adminResponseDTOMock.setId(1);
        adminResponseDTOMock.setNome("Lucas");
        adminResponseDTOMock.setEmail("lucas@teste.com");
        adminResponseDTOMock.setCpf("12345678901");
        adminResponseDTOMock.setTipoUsuario(TipoUsuario.ADMIN);
        adminResponseDTOMock.setAtivo(Ativo.A);
    }

    private static void gerarAdmin(Admin adminMock) {
        adminMock.setId(1);
        adminMock.setNome("Lucas");
        adminMock.setEmail("lucas@teste.com");
        adminMock.setCpf("12345678901");
        adminMock.setSenha("senha123");
        adminMock.setTipoUsuario(TipoUsuario.ADMIN);
        adminMock.setAtivo(Ativo.A);
    }

}