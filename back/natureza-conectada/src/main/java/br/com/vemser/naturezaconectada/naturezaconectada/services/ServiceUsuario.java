package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.relatorios.RelatorioQuantidadeUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.interfaces.IServiceUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ServiceUsuario implements IServiceUsuario {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public Optional<Usuario> findByEmailAndSenha(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }

    public UsuarioResponseDTO logar(String email, String senha) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) throw new RegraDeNegocioException("Usuário não existe pra o e-mail informado.");

        if (!usuario.getSenha().equals(senha)) throw new RegraDeNegocioException("Senha incorreta.");
        return objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
    }

    public List<UsuarioResponseDTO> listarTodos() throws Exception {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (!usuarios.isEmpty()) {
            return usuarios.stream()
                    .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioResponseDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw new RegraDeNegocioException("Nenhum usuário encontrado");
        }
    }

    public List<UsuarioResponseDTO> listarUsuariosAtivos() throws RegraDeNegocioException {
        List<UsuarioResponseDTO> usuarios = usuarioRepository.findAllUsuariosAtivos();
        if (!usuarios.isEmpty()) {
            return usuarios;
        } else {
            throw new RegraDeNegocioException("Nenhum usuário encontrado");
        }
    }

    public UsuarioResponseDTO procurarPorEmail(String email) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            return objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
        } else {
            throw new RegraDeNegocioException("Nenhum usuário encontrado para o email: " + email);
        }
    }

    public List<RelatorioQuantidadeUsuario> gerarRelatorio(){
       return this.usuarioRepository.relatorioParaAdmin();
    }
}