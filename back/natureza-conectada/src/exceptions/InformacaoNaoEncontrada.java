package exceptions;

public class InformacaoNaoEncontrada extends RuntimeException {
    public InformacaoNaoEncontrada(String mensagemErro) {
        super(mensagemErro);
    }
}
