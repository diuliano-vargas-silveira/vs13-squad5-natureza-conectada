package br.com.vemser.naturezaconectada.naturezaconectada.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.Exception;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;

public interface IServiceUsuario {
    Usuario procurarPorEmail(String email) throws Exception;
}
