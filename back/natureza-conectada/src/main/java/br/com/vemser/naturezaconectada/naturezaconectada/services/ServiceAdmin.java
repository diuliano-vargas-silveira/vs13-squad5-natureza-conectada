package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.AdminRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.AdminResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Admin;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.AdminRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceAdmin {

    private final AdminRepository adminRepository;


    private final ObjectMapper objectMapper;

    private final PasswordEncoder passwordEncoder;

    public AdminResponseDTO adicionar(AdminRequestDTO adminRequestDTO) throws java.lang.Exception {
        Admin buscarAdminPorEmail = adminRepository.findByEmail(adminRequestDTO.getEmail());
        if (buscarAdminPorEmail == null) {
            Admin novoAdmin = new Admin();
            novoAdmin.setTipoUsuario(TipoUsuario.ADMIN);
            novoAdmin.setNome(adminRequestDTO.getNome());
            novoAdmin.setEmail(adminRequestDTO.getEmail());
            novoAdmin.setCpf(adminRequestDTO.getCpf());
            novoAdmin.setSenha(passwordEncoder.encode(adminRequestDTO.getSenha()));
            novoAdmin.setAtivo(Ativo.A);
            this.adminRepository.save(novoAdmin);
            AdminResponseDTO adminResponseDTO = objectMapper.convertValue(novoAdmin, AdminResponseDTO.class);
            return adminResponseDTO;
        } else {
            throw new RegraDeNegocioException("Já existe um usuário para o e-mail informado: " + adminRequestDTO.getEmail());
        }
    }

    public AdminResponseDTO alterarStatusAtivo(int id, AdminRequestDTO adminRequestDTO) throws RegraDeNegocioException {
        Admin admin = procurarAdmin(id);
        admin.setAtivo(adminRequestDTO.getAtivo());
        adminRepository.save(admin);
        return objectMapper.convertValue(admin, AdminResponseDTO.class);
    }

    public AdminResponseDTO editar(int id, AdminRequestDTO adminRequestDTO) throws Exception {
        Admin admin = procurarAdmin(id);
        admin.setAtivo(Ativo.A);
        admin.setNome(adminRequestDTO.getNome());
        admin.setEmail(adminRequestDTO.getEmail());
        admin.setCpf(adminRequestDTO.getCpf());
        admin.setSenha(passwordEncoder.encode(adminRequestDTO.getSenha()));
        adminRepository.save(admin);
        return objectMapper.convertValue(admin, AdminResponseDTO.class);
    }

    public AdminResponseDTO procurarPorId(int id) throws RegraDeNegocioException, InformacaoNaoEncontrada {
        Admin admin = procurarAdmin(id);
        return objectMapper.convertValue(admin, AdminResponseDTO.class);
    }

    public List<AdminResponseDTO> listarTodos() throws Exception {
        return adminRepository.findAll().stream()
                .map(adminEntity -> objectMapper.convertValue(adminEntity, AdminResponseDTO.class))
                .collect(Collectors.toList());
    }

    private Admin procurarAdmin(int id) throws RegraDeNegocioException {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            return adminOptional.get();
        } else {
            throw new RegraDeNegocioException("Nenhum usuário encontrado para o id: " + id);
        }
    }
}