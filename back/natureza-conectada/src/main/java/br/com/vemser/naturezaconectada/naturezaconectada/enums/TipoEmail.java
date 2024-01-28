package br.com.vemser.naturezaconectada.naturezaconectada.enums;

public enum TipoEmail {
    CRIACAO("Estamos felizes em ter você em nosso sistema :)"),
    EXCLUSAO("Seu usuário foi excluído do nosso sistema "),
    ALTERACAO("Seu usuário foi alterado em nosso sistema :( ");

    private String mensagem;

    TipoEmail(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
