package services;

import exceptions.BancoDeDadosException;
import exceptions.InformacaoNaoEncontrada;
import exceptions.SenhaOuEmailInvalido;
import interfaces.IServiceUsuario;
import models.Usuario;
import repository.UsuarioRepository;

public class ServiceUsuario implements IServiceUsuario {

    private static final UsuarioRepository usuarioRepository = new UsuarioRepository();

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

    @Override
    public Usuario procurarPorEmail(String email) throws BancoDeDadosException {
        return usuarioRepository.procurarPorEmail(email);
    }
}

