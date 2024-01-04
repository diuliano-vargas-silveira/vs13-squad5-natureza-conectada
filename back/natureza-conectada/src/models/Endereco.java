package models;

import enums.Tipo;

public class Endereco {

    private int id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;

    private static final String PAIS = "Brasil";

    private Tipo tipo;

    public Endereco(int id, String cep, String logradouro, String numero, String complemento, String cidade, String estado, int tipo) {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;

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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        return "\nID: " + getId() + "\nCEP: " + getCep() + "\nLogradouro: " + getLogradouro() + "\nNº: " + getNumero() + "\nComplemento: " + getComplemento()
                + "\nCidade: " + getCidade() + "\nEstado: " + getEstado() + "\nPaís: " + PAIS + "\nenums.Tipo: " + getTipo();
    }
}