package br.com.vemser.naturezaconectada.naturezaconectada.models;

public class Relatorio {

    private  int id;
    private Cliente dono;
    private Especialista avaliador;
    private Muda muda;
    private String estadoMuda;
    private String sugestoes;
    private double avaliacaoEspecialista;


    public Relatorio(){};
    public Relatorio(String estadoMuda) {
        this.estadoMuda = estadoMuda;

    }

    @Override
    public String toString(){
        return "Relatório ID: " + this.id
        + "\nDados do cliente: \n" + this.dono.toString()
        + "\nDados do especialista:\n" + this.avaliador
        + "\nDescrição da muda: \n" + this.muda
        + "\nEstado da muda: " + this.estadoMuda
        + "\nSugestões: " + this.sugestoes
        + "\nAvaliação do especialista: " + this.avaliacaoEspecialista;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getDono() {
        return dono;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public Especialista getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(Especialista avaliador) {
        this.avaliador = avaliador;
    }

    public Muda getMuda() {
        return muda;
    }

    public void setMuda(Muda muda) {
        this.muda = muda;
    }

    public String getEstadoMuda() {
        return estadoMuda;
    }

    public void setEstadoMuda(String estadoMuda) {
        this.estadoMuda = estadoMuda;
    }

    public String getSugestoes() {
        return sugestoes;
    }

    public void setSugestoes(String sugestoes) {
        this.sugestoes = sugestoes;
    }

    public double getAvaliacaoEspecialista() {
        return avaliacaoEspecialista;
    }

    public void setAvaliacaoEspecialista(double avaliacaoEspecialista) {
        this.avaliacaoEspecialista = avaliacaoEspecialista;
    }
}