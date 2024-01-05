package exceptions;

public class ContatoExistente extends RuntimeException {

    public ContatoExistente() {
        super("Este contato já existe.");
    }
}
