package br.com.vemser.naturezaconectada.naturezaconectada.exceptions;

public class SenhaOuEmailInvalido extends Exception {
    public SenhaOuEmailInvalido() {
        super("Usuário ou senha inválidos!");
    }
}
