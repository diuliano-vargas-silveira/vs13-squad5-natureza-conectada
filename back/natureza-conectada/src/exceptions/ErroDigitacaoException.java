package exceptions;

public class ErroDigitacaoException extends RuntimeException {
    public ErroDigitacaoException() {
        super("| Valor inválido! Digite novamente.");
    }
}
