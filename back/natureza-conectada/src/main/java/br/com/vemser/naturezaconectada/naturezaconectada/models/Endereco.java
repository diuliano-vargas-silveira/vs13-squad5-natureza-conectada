package br.com.vemser.naturezaconectada.naturezaconectada.models;


import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;

public class Endereco {

    private int id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cidade;
    private Estados estado;
    private Usuario usuario;

    private static final String PAIS = "Brasil";

    private Tipo tipo;

    public Endereco() {}

    public Endereco(String cep, String logradouro, String numero, String complemento, String cidade, Estados estado, int tipo) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;

        this.tipo = (tipo == 1) ? Tipo.RESIDENCIAL : ((tipo == 2) ? Tipo.COMERCIAL : null);

        if (this.tipo == null)
            throw new IllegalArgumentException("Tipo inválido.");
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

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = (tipo == 1) ? Tipo.RESIDENCIAL : ((tipo == 2) ? Tipo.COMERCIAL : null);

        if (this.tipo == null)
            throw new IllegalArgumentException("Tipo inválido.");
    }

    @Override
    public String toString() {
        return "\nID: " + getId() + "\nCEP: " + getCep() + "\nLogradouro: " + getLogradouro() + "\nNº: " + getNumero() + "\nComplemento: " + getComplemento()
                + "\nCidade: " + getCidade() + "\nEstado: " + getEstado() + "\nPaís: " + PAIS + "\nTipo: " + getTipo()
        + "\nCidade: " + getCidade() + "\nEstado: " + getEstado() + "\nTipo: " + getTipo();
    }
}