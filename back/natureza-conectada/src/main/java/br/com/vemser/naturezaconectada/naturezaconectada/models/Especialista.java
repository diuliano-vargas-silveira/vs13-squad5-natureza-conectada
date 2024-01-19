package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;

public class Especialista extends Usuario {

    private int idEspecialista;
    private Contato contato;
    private String documento;
    private String especializacao;
    private Estados regiaoResponsavel;

    public Especialista() {
        super.setTipoUsuario(TipoUsuario.ESPECIALISTA);
    }

    public Especialista(String nome, String email, String senha, String documento, String especializacao, Estados regiaoResponsavel) {
        super(nome, email, senha, TipoUsuario.ESPECIALISTA);
        this.documento = documento;
        this.especializacao = especializacao;
        this.regiaoResponsavel = regiaoResponsavel;
    }

    public int getIdEspecialista() {
        return idEspecialista;
    }

    public void setIdEspecialista(int idEspecialista) {
        this.idEspecialista = idEspecialista;
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
                "ID=" + getId() +
                ", contato=" + contato +
                ", documento='" + documento + '\'' +
                ", especializacao='" + especializacao + '\'' +
                ", regiaoResponsavel='" + regiaoResponsavel + '\'' +
                '}';
    }
}
