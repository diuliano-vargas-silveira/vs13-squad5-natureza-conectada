package models;

import enums.Estados;
import enums.TipoUsuario;

public class Especialista extends Usuario {

    private int ID;
    private String especialista;
    private Contato contato;
    private String documento;
    private String especializacao;
    private Estados regiaoResponsavel;

    public Especialista() {
    }

    public Especialista(int id, String nome, String email, String senha, TipoUsuario tipoUsuario, int ID, String especialista, Contato contato, String documento, String especializacao, Estados regiaoResponsavel) {
        super(id, nome, email, senha, TipoUsuario.ESPECIALISTA);
        this.ID = ID;
        this.especialista = especialista;
        this.contato = contato;
        this.documento = documento;
        this.especializacao = especializacao;
        this.regiaoResponsavel = regiaoResponsavel;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEspecialista() {
        return especialista;
    }

    public void setEspecialista(String especialista) {
        this.especialista = especialista;
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

    public Estados getRegiaoResponsavel() {
        return regiaoResponsavel;
    }

    public void setRegiaoResponsavel(Estados regiaoResponsavel) {
        this.regiaoResponsavel = regiaoResponsavel;
    }

    @Override
    public String toString() {
        return "Especialista{" +
                "ID=" + ID +
                ", especialista='" + especialista + '\'' +
                ", contato=" + contato +
                ", documento='" + documento + '\'' +
                ", especializacao='" + especializacao + '\'' +
                ", regiaoResponsavel='" + regiaoResponsavel + '\'' +
                '}';
    }
}
