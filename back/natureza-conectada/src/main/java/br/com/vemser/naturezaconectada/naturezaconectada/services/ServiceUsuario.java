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

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ServiceUsuario implements IServiceUsuario {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public UsuarioResponseDTO adicionarUsuario(UsuarioRequestDTO usuarioRequestDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Usuario usuario = objectMapper.convertValue(usuarioRequestDTO, Usuario.class);

        Usuario usuarioBanco = usuarioRepository.procurarPorEmail(usuarioRequestDTO.getEmail());

        if (usuarioBanco != null) {
            throw new RegraDeNegocioException("Usuário já existe no banco de dados!");
        }

        Usuario usuarioAdicionado = usuarioRepository.adicionar(usuario);

        UsuarioResponseDTO usuarioResponseDTO = objectMapper.convertValue(usuarioAdicionado, UsuarioResponseDTO.class);
        return usuarioResponseDTO;
    }

    public UsuarioResponseDTO logar(String email, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        Usuario usuario = usuarioRepository.procurarPorEmail(email);

        if (usuario == null) {
            throw new RegraDeNegocioException("Usuário não existe");
        }

        if (!usuario.getSenha().equals(senha)) {
            throw new SenhaOuEmailInvalido();
        }
        UsuarioResponseDTO usuarioResponseDTO = objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
        return usuarioResponseDTO;
    }

    public List<UsuarioResponseDTO> listarTodos() throws BancoDeDadosException {
        List<Usuario> usuario= usuarioRepository.listar();
        List<UsuarioResponseDTO> usuarioResponseDTO = usuario.stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioResponseDTO.class))
                .collect(Collectors.toList());

        return usuarioResponseDTO;
    }

    @Override
    public UsuarioResponseDTO procurarPorEmail(String email) throws BancoDeDadosException, RegraDeNegocioException {
        try {
            Usuario usuario = usuarioRepository.procurarPorEmail(email);
            UsuarioResponseDTO usuarioResponseDTO = objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
            return usuarioResponseDTO;
        } catch (Exception ex) {
            throw new RegraDeNegocioException("Nenhum usuário encontrado para o email: " + email);
        }
    }

    public UsuarioResponseDTO editar(int id, UsuarioRequestDTO usuarioRequestDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Usuario usuarioRecuperado = buscarUsuario(id);

        Usuario usuario = objectMapper.convertValue(usuarioRequestDTO, Usuario.class);

        usuarioRepository.editar(id, usuario);
        usuario.setId(id);

        UsuarioResponseDTO usuarioResponseDTO = objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
        return usuarioResponseDTO;
    }

    public void remover(int id) throws BancoDeDadosException, RegraDeNegocioException {
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

}
