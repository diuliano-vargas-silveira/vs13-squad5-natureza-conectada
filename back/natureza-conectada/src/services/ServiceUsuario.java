/*
package services;

import database.BancoDeDados;
import enums.Tipo;
import enums.TipoUsuario;
import exceptions.InformacaoNaoEncontrada;
import exceptions.SenhaOuEmailInvalido;
import interfaces.IServiceUsuario;
import models.Cliente;
import models.Usuario;

import java.util.Optional;

public class ServiceUsuario implements IServiceUsuario {

    private static final ServiceCliente serviceCliente = new ServiceCliente();
    private static final ServiceEspecialista serviceEspecialista = new ServiceEspecialista();

    public Usuario logar(String email, String senha) {
        Optional<Usuario> usuario = procurarPorEmail(email);

        if (usuario.isEmpty()) {
            throw new InformacaoNaoEncontrada("Usuário não existe");
        }

        Usuario usuarioRetorno = usuario.get();

        if (!usuarioRetorno.getSenha().equals(senha)) {
            throw new SenhaOuEmailInvalido();
        }

        return usuarioRetorno;
    }

    @Override
    public Optional<Usuario> procurarPorEmail(String email) {
        return BancoDeDados.usuarios.stream().filter(usuario -> usuario.getEmail().equals(email)).findFirst();
    }
}
 */
