package br.com.vemser.naturezaconectada.naturezaconectada.exceptions;

public class ErroDigitacao extends RuntimeException {
    public ErroDigitacao() {
        super("Valor inválido! Digite novamente.");
    }
}
