package models;

import enums.Tipo;

public class Contato {

    private int id;
    private String descricao;
    private String numero;
    private Tipo tipo;

    public Contato(int id, String descricao, String numero, int tipo) {
        this.id = id;
        this.descricao = descricao;
        this.numero = numero;

        this.tipo = (tipo == 1) ? Tipo.RESIDENCIAL : ((tipo == 2) ? Tipo.COMERCIAL : null);

        if (this.tipo == null)
            throw new IllegalArgumentException("enums.Tipo inválido.");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = (tipo == 1) ? Tipo.RESIDENCIAL : ((tipo == 2) ? Tipo.COMERCIAL : null);

        if (this.tipo == null)
            throw new IllegalArgumentException("enums.Tipo inválido.");
    }

    @Override
    public String toString() {
        return "\nID: " + getId() + "\nDescrição: " + getDescricao() + "\nNumero: " + getNumero() + "\nenums.Tipo: " + getTipo();
    }
}