package exceptions;

public class SenhaOuEmailInvalido extends RuntimeException {
    public SenhaOuEmailInvalido() {
        super("Usuário ou senha inválidos!");
    }
}
