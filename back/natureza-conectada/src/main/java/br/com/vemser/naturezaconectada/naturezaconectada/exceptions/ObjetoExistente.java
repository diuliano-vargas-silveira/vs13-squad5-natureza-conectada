package br.com.vemser.naturezaconectada.naturezaconectada.exceptions;

public class ObjetoExistente extends RuntimeException{

    public ObjetoExistente(String mensagemErro) {
        super(mensagemErro);
    }
}
