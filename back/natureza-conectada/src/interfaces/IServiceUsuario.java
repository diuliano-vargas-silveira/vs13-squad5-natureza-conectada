package interfaces;

import exceptions.BancoDeDadosException;
import models.Usuario;

import java.util.Optional;

public interface IServiceUsuario {
    Usuario procurarPorEmail(String email) throws BancoDeDadosException;
}
