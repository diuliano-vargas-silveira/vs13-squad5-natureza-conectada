package exceptions;

import java.sql.SQLException;

public class BancoDeDadosException extends SQLException {

    public BancoDeDadosException(Throwable erro) {
        super(erro);
    }
}
