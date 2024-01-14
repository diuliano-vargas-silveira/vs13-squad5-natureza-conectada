package services;

import exceptions.BancoDeDadosException;
import exceptions.InformacaoNaoEncontrada;
import exceptions.ObjetoExistente;
import exceptions.SenhaOuEmailInvalido;
import interfaces.IServiceUsuario;
import models.Usuario;
import repository.UsuarioRepository;

import java.util.List;

public class ServiceUsuario implements IServiceUsuario {

    private static final UsuarioRepository usuarioRepository = new UsuarioRepository();

    public Usuario adicionarUsuario(Usuario usuario) throws BancoDeDadosException {
        Usuario usuarioBanco = usuarioRepository.procurarPorEmail(usuario.getEmail());

        if (usuarioBanco != null) {
            throw new ObjetoExistente("Usuário já existe no banco de dados!");
        }

        return usuarioRepository.adicionar(usuario);
    }

    public Usuario logar(String email, String senha) throws BancoDeDadosException {
        Usuario usuario = usuarioRepository.procurarPorEmail(email);

        if (usuario == null) {
            throw new InformacaoNaoEncontrada("Usuário não existe");
        }


        if (!usuario.getSenha().equals(senha)) {
            throw new SenhaOuEmailInvalido();
        }

        return usuario;
    }

    public List<Usuario> listarTodos() throws BancoDeDadosException {
        List<Usuario> usuarios = usuarioRepository.listar();

        return  usuarios;
    }

    @Override
    public Usuario procurarPorEmail(String email) throws BancoDeDadosException {
        return usuarioRepository.procurarPorEmail(email);
    }
}

