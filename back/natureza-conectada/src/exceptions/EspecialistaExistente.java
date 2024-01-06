package exceptions;

public class EspecialistaExistente extends RuntimeException {
    public EspecialistaExistente() {
        super("Este especialista já existe");
    }
}
