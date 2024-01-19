package br.com.vemser.naturezaconectada.naturezaconectada.exceptions;

public class EnumNaoExistente extends RuntimeException {
    public EnumNaoExistente(String mensagem) {
        super(mensagem);
    }
}
