package exceptions;

public class EnderecoExistente extends RuntimeException {

    public EnderecoExistente() {
        super("Este endereço já foi cadastrado.");
    }
}