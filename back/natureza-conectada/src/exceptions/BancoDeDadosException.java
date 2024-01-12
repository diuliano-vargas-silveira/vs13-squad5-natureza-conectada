package exceptions;

import java.sql.SQLException;

public class BancoDeDadosException extends SQLException {

    public BancoDeDadosException(String mensagem) {
        super(mensagem);
    }
}
