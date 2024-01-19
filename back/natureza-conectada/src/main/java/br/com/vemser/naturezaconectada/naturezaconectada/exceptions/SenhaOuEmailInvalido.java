package br.com.vemser.naturezaconectada.naturezaconectada.exceptions;

public class SenhaOuEmailInvalido extends RuntimeException {
    public SenhaOuEmailInvalido() {
        super("Usuário ou senha inválidos!");
    }
}
