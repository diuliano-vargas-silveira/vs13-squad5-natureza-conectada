package br.com.vemser.naturezaconectada.naturezaconectada.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.BancoDeDadosException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;

public interface IServiceUsuario {
    Usuario procurarPorEmail(String email) throws BancoDeDadosException;
}
