package br.com.vemser.naturezaconectada.naturezaconectada.exceptions;

import java.sql.SQLException;

public class BancoDeDadosException extends RegraDeNegocioException {

    public BancoDeDadosException(String erro) {
        super(erro);
    }
}
