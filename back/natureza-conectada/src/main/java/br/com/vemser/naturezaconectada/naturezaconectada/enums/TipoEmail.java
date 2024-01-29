package br.com.vemser.naturezaconectada.naturezaconectada.enums;

public enum TipoEmail {
    CRIACAO("Estamos felizes em ter você em nosso sistema :)","Muito obrigado, seu pedido foi gerado com sucesso\n  identificação da entrega:"),
    EXCLUSAO("Seu usuário foi excluído do nosso sistema ","Mas que pena, seu pedido foi excluído, Continue solicitando suas mudas, #TodosConectadosANatureza\n" +
            "identificação da entrega: " ),
    ALTERACAO("Seu usuário foi alterado em nosso sistema :( ","A Sua entrega Foi alterada Com sucesso \n identificação da entrega:");

    private String mensagemUsuario;

    private String mensagemEntrega;

    TipoEmail(String mensagem,String mensagemEntrega) {
        this.mensagemUsuario = mensagem;
        this.mensagemEntrega = mensagemEntrega;
    }

    public String getMensagemEntrega() {
        return mensagemEntrega;
    }

    public String getMensagemUsuario() {
        return mensagemUsuario;
    }
}
