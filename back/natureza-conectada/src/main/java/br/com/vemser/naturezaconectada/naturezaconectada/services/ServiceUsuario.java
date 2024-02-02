package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.UsuarioRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.*;
import br.com.vemser.naturezaconectada.naturezaconectada.interfaces.IServiceUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.Exception;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ServiceUsuario implements IServiceUsuario {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public UsuarioResponseDTO adicionarUsuario(UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        Usuario usuario = objectMapper.convertValue(usuarioRequestDTO, Usuario.class);

        Usuario usuarioBanco = usuarioRepository.procurarPorEmail(usuarioRequestDTO.getEmail());

        if (usuarioBanco != null) {
            throw new RegraDeNegocioException("Usuário já existe para o email: " + usuarioRequestDTO.getEmail());
        }

        Usuario usuarioAdicionado = usuarioRepository.adicionar(usuario);

        UsuarioResponseDTO usuarioResponseDTO = objectMapper.convertValue(usuarioAdicionado, UsuarioResponseDTO.class);
        return usuarioResponseDTO;
    }

    public UsuarioResponseDTO logar(String email, String senha) throws Exception {
        Usuario usuario = usuarioRepository.procurarPorEmail(email);

        if (usuario == null) {
            throw new RegraDeNegocioException("Usuário não existe pra o e-mail informado.");
        }

        if (!usuario.getSenha().equals(senha)) {
            throw new RegraDeNegocioException("Senha incorreta.");
        }
        UsuarioResponseDTO usuarioResponseDTO = objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
        return usuarioResponseDTO;
    }

    public List<UsuarioResponseDTO> listarTodos() throws Exception {
        List<Usuario> usuario= usuarioRepository.listar();
        List<UsuarioResponseDTO> usuarioResponseDTO = usuario.stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioResponseDTO.class))
                .collect(Collectors.toList());

        return usuarioResponseDTO;
    }

    public List<UsuarioResponseDTO> procurarUsuariosAtivos() throws Exception {
        List<Usuario> usuarios = usuarioRepository.procurarUsuariosAtivos();
        var usuariosResponseDTO = usuarios.stream()
                .map(usuarioDTO -> objectMapper.convertValue(usuarioDTO, UsuarioResponseDTO.class))
                .collect(Collectors.toList());

        return usuariosResponseDTO;
    }

    @Override
    public UsuarioResponseDTO procurarPorEmail(String email) throws Exception {

        Usuario usuario = usuarioRepository.procurarPorEmail(email);
        if (usuario != null) {
            UsuarioResponseDTO usuarioResponseDTO = objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
            return usuarioResponseDTO;
        } else {
            throw new RegraDeNegocioException("Nenhum usuário encontrado para o email: " + email);
        }
    }

    public UsuarioResponseDTO editar(int id, UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        Usuario usuarioRecuperado = buscarUsuario(id);

        Usuario usuario = objectMapper.convertValue(usuarioRequestDTO, Usuario.class);

        usuarioRepository.editar(id, usuario);
        usuario.setId(id);

        UsuarioResponseDTO usuarioResponseDTO = objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
        return usuarioResponseDTO;
    }

    public void remover(int id) throws Exception {
        Usuario usuario = buscarUsuario(id);
        usuarioRepository.remover(id);
    }

    private Usuario buscarUsuario(int id) throws RegraDeNegocioException {
        try {
            Usuario usuarioRecuperado = usuarioRepository.procurarPorId(id);
            return usuarioRecuperado;
        } catch (Exception ex) {
            throw new RegraDeNegocioException("Nenhum usuário encontrado para o Id: " + id);
        }
    }
    public Usuario buscarUsuarioAtivo(int id) throws Exception {
        try {
            Usuario usuarioRecuperado = usuarioRepository.procurarAtivoPorId(id);
            return usuarioRecuperado;
        } catch (Exception ex) {
            throw new RegraDeNegocioException("Nenhum usuário Ativo encontrado para o Id: " + id);
        }
    }

}