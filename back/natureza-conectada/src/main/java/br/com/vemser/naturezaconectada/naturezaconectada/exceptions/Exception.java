package br.com.vemser.naturezaconectada.naturezaconectada.exceptions;

import java.sql.SQLException;

public class Exception extends SQLException {

    public Exception(String erro) {
        super(erro);
    }
}
