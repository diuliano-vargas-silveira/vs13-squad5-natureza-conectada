package models;

import enums.TamanhoMuda;
import enums.TipoMuda;

import java.util.ArrayList;
import java.util.List;


public class Muda {


    private int id;

    private int idEspecialista;
    
    private int idCliente;

    private TipoMuda tipo;

    private String nome;

    private String nomeCientifico;

    private TamanhoMuda porte;

    private String ambienteIdeal;

    private String descricao;

    private List<Especialista> especialistasDeRegiao = new ArrayList<>();

    public Muda(){

    }

    public Muda(int tipo, String nome, String nomeCientifico, int porte, String ambienteIdeal, String descricao) {

        if (TipoMuda.ofTipo(tipo) == null) {
            throw new IllegalArgumentException("enum Tipo inválido.");
        } else if (TamanhoMuda.ofTipo(porte) == null) {
            throw new IllegalArgumentException("enum porte inválido.");
        } else {

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
        return "ID: " + String.valueOf(this.id)
        + "\nTipo: " + String.valueOf(this.tipo).toLowerCase()
        + "\nNome: " + this.nome
        + "\nNome científico: " + this.nomeCientifico
        + "\nPorte: " + String.valueOf(this.porte).toLowerCase()
        + "\nAmbiente recomendado: " + this.ambienteIdeal
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

    public List<Especialista> getEspecialistasDeRegiao() {
        return especialistasDeRegiao;
    }

    public void setEspecialistasDeRegiao(List<Especialista> especialistasDeRegiao) {
        this.especialistasDeRegiao = especialistasDeRegiao;
    }

    public void setIdEspecialista(int idEspecialista){
        this.idEspecialista = idEspecialista;
    }
    
    public int getIdEspecialista(){
        return this.idEspecialista;
    }
    
    public void setIdCliente(int idCliente){
        this.idCliente = idCliente; 
    }

    public int getIdCliente(){
        return this.idCliente;
    }
}

