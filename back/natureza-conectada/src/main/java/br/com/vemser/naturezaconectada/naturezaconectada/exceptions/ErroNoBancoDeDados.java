package br.com.vemser.naturezaconectada.naturezaconectada.exceptions;

import java.sql.SQLException;

public class ErroNoBancoDeDados extends SQLException {

    public ErroNoBancoDeDados(String erro) {
        super(erro);
    }
}
