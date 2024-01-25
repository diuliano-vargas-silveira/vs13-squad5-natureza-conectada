package br.com.vemser.naturezaconectada.naturezaconectada.services;



import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.Exception;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ObjetoExistente;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.SenhaOuEmailInvalido;
import br.com.vemser.naturezaconectada.naturezaconectada.interfaces.IServiceUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceUsuario implements IServiceUsuario {

    private final UsuarioRepository usuarioRepository;

    public ServiceUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario adicionarUsuario(Usuario usuario) throws Exception {
        Usuario usuarioBanco = usuarioRepository.procurarPorEmail(usuario.getEmail());

        if (usuarioBanco != null) {
            throw new ObjetoExistente("Usuário já existe no banco de dados!");
        }

        return usuarioRepository.adicionar(usuario);
    }

    public Usuario logar(String email, String senha) throws Exception {
        Usuario usuario = usuarioRepository.procurarPorEmail(email);

        if (usuario == null) {
            throw new InformacaoNaoEncontrada("Usuário não existe");
        }


        if (!usuario.getSenha().equals(senha)) {
            throw new SenhaOuEmailInvalido();
        }

        return usuario;
    }

    public List<Usuario> listarTodos() throws Exception {
        List<Usuario> usuarios = usuarioRepository.listar();

        return  usuarios;
    }

    @Override
    public Usuario procurarPorEmail(String email) throws Exception {
        return usuarioRepository.procurarPorEmail(email);
    }

    public void remover(int id) throws Exception {
        usuarioRepository.remover(id);
    }

    public boolean editar(int id, Usuario usuarioEditado) throws Exception {
        return usuarioRepository.editar(id, usuarioEditado);
    }
}
