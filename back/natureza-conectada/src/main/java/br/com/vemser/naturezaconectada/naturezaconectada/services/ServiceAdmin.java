//package br.com.vemser.naturezaconectada.naturezaconectada.services;
//
//import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.AdminRequestDTO;
//import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.AdminResponseDTO;
//import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
//import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
//import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
//import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
//import br.com.vemser.naturezaconectada.naturezaconectada.models.Admin;
//import br.com.vemser.naturezaconectada.naturezaconectada.repository.AdminRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class ServiceAdmin {
//
//    private final AdminRepository adminRepository;
//    private final ServiceUsuario serviceUsuario;
//    private final ObjectMapper objectMapper;
//
//    public AdminResponseDTO adicionar(AdminRequestDTO adminRequestDTO) throws java.lang.Exception {
//        if (adminRequestDTO.getTipoUsuario() == TipoUsuario.ADMIN) {
//            UsuarioResponseDTO usuarioResponseDTO = serviceUsuario.adicionarUsuario(adminRequestDTO);
//            adminRequestDTO.setId(usuarioResponseDTO.getId());
//            Admin admin = adminRepository.adicionar(objectMapper.convertValue(adminRequestDTO, Admin.class));
//            adminRequestDTO.setIdAdmin(admin.getIdAdmin());
//            AdminResponseDTO adminResponseDTO = objectMapper.convertValue(adminRequestDTO, AdminResponseDTO.class);
//            return adminResponseDTO;
//        } else {
//            throw new RegraDeNegocioException("Tipo de usuário deve ser ADMIN");
//        }
//    }
//
//    public void deletar(int id) throws java.lang.Exception {
//        Admin admin = procurar(id);
//        int idUsuario = admin.getId();
//        adminRepository.remover(id);
//        serviceUsuario.remover(idUsuario);
//    }
//
//    public AdminResponseDTO editar(int id, AdminRequestDTO adminRequestDTO) throws java.lang.Exception {
//        Admin admin = procurar(id);
//        adminRequestDTO.setTipoUsuario(TipoUsuario.ADMIN);
//        UsuarioResponseDTO usuarioResponseDTO = serviceUsuario.editar(admin.getId(), adminRequestDTO);
//        adminRequestDTO.setIdAdmin(id);
//        AdminResponseDTO adminResponseDTO = objectMapper.convertValue(adminRequestDTO, AdminResponseDTO.class);
//        return adminResponseDTO;
//    }
//
//    public AdminResponseDTO procurarPorID(int id) throws RegraDeNegocioException, InformacaoNaoEncontrada {
//        Admin admin = procurar(id);
//
//        if (admin == null) {
//            throw new InformacaoNaoEncontrada("Não existe nenhum administrador com este ID!");
//        }
//        AdminResponseDTO adminResponseDTO = objectMapper.convertValue(admin, AdminResponseDTO.class);
//        return adminResponseDTO;
//    }
//
//    public List<AdminResponseDTO> listarTodos() throws Exception {
//        List<Admin> admin= adminRepository.listar();
//        List<AdminResponseDTO> adminResponseDTO = admin.stream()
//                .map(adminEntity -> objectMapper.convertValue(adminEntity, AdminResponseDTO.class))
//                .collect(Collectors.toList());
//
//        return adminResponseDTO;
//    }
//
//    private Admin procurar(int id) throws RegraDeNegocioException {
//        try {
//            return adminRepository.procurarPorId(id);
//        } catch (Exception ex) {
//            throw new RegraDeNegocioException("Nenhum admin encontrado para o Id: " + id);
//        }
//    }
//}