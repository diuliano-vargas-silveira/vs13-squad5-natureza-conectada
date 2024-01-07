package interfaces;

import models.Usuario;

import java.util.Optional;

public interface IServiceUsuario {
    Optional<Usuario> procurarPorEmail(String email);
}
