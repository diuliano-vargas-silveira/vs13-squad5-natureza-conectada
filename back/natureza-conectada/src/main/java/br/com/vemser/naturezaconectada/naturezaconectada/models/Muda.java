package br.com.vemser.naturezaconectada.naturezaconectada.models;


import br.com.vemser.naturezaconectada.naturezaconectada.enums.TamanhoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoMuda;

public class Muda {


    private int id;

    private int quantidade;

    private TipoMuda tipo;

    private String nome;

    private String nomeCientifico;

    private TamanhoMuda porte;

    private String ambienteIdeal;

    private String descricao;

    public Muda(){

    }

    public Muda(int tipo, int quantidade, String nome, String nomeCientifico, int porte, String ambienteIdeal, String descricao) {

        if (TipoMuda.ofTipo(tipo) == null) {
            throw new IllegalArgumentException("enum Tipo inválido.");
        } else if (TamanhoMuda.ofTipo(porte) == null) {
            throw new IllegalArgumentException("enum porte inválido.");
        } else {
            this.quantidade = quantidade;
            this.tipo = TipoMuda.ofTipo(tipo);
            this.porte = TamanhoMuda.ofTipo(porte);
            this.nome = nome;
            this.nomeCientifico = nomeCientifico;
            this.ambienteIdeal = ambienteIdeal;
            this.descricao = descricao;
        }
    }

    @Override
    public String toString(){
        return "\nID: " + String.valueOf(this.id)
        + "\nTipo: " + String.valueOf(this.tipo).toLowerCase()
        + "\nNome: " + this.nome
        + "\nNome científico: " + this.nomeCientifico
        + "\nPorte: " + String.valueOf(this.porte).toLowerCase()
        + "\nAmbiente recomendado: " + this.ambienteIdeal
        + "\nQuantidade: " + this.quantidade
        + "\nDescrição: " + this.descricao;
    }
    


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoMuda getTipo() {
        return tipo;
    }

    public void setTipo(TipoMuda tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public TamanhoMuda getPorte() {
        return porte;
    }

    public void setPorte(TamanhoMuda porte) {
        this.porte = porte;
    }

    public String getAmbienteIdeal() {
        return ambienteIdeal;
    }

    public void setAmbienteIdeal(String ambienteIdeal) {
        this.ambienteIdeal = ambienteIdeal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
