package exceptions;

public class MudaExistente extends RuntimeException {

    public MudaExistente(String mensagemErro){
        super(mensagemErro);
    }
}
