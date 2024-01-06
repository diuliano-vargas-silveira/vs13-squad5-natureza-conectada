package models;

import enums.TipoUsuario;

public class Especialista extends Usuario {


    private Contato contato;
    private String documento;
    private String especializacao;
    private String regiaoResponsavel;

    public Especialista() {
    }

    public Especialista(String nome, String email, String senha, Contato contato, String documento, String especializacao, String regiaoResponsavel) {
        super(nome, email, senha, TipoUsuario.ESPECIALISTA);
        this.contato = contato;
        this.documento = documento;
        this.especializacao = especializacao;
        this.regiaoResponsavel = regiaoResponsavel;
    }




    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }

    public String getRegiaoResponsavel() {
        return regiaoResponsavel;
    }

    public void setRegiaoResponsavel(String regiaoResponsavel) {
        this.regiaoResponsavel = regiaoResponsavel;
    }

    @Override
    public String toString() {
        return "Especialista{" +
                "ID=" + getId() +
                ", contato=" + contato +
                ", documento='" + documento + '\'' +
                ", especializacao='" + especializacao + '\'' +
                ", regiaoResponsavel='" + regiaoResponsavel + '\'' +
                '}';
    }
}
