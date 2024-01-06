package exceptions;

public class ObjetoExistente extends RuntimeException{

    public ObjetoExistente(String mensagemErro) {
        super(mensagemErro);
    }
}
