package exceptions;

public class ErroDigitacao extends RuntimeException {
    public ErroDigitacao() {
        super("Valor inválido! Digite novamente.");
    }
}
