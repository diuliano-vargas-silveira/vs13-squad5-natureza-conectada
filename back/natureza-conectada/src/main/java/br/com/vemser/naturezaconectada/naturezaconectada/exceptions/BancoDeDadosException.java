package br.com.vemser.naturezaconectada.naturezaconectada.exceptions;

import java.sql.SQLException;

public class BancoDeDadosException extends SQLException {

    public BancoDeDadosException(String erro) {
        super(erro);
    }
}
