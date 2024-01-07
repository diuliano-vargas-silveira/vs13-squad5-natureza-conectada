package exceptions;

public class EnumNaoExistente extends RuntimeException {
    public EnumNaoExistente(String mensagem) {
        super(mensagem);
    }
}
