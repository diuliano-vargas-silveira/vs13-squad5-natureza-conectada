package models;

public class Relatorio {

    private  int id;
    private Cliente dono;
    private Especialista avaliador;
    private Muda muda;
    private String estadoMuda;
    private String sugestoes;
    private double avaliacaoEspecialista;

    public Relatorio(int id, Cliente dono, Especialista avaliador, Muda muda, String estadoMuda, String sugestoes, double avaliacaoEspecialista) {
        this.id = id;
        this.dono = dono;
        this.avaliador = avaliador;
        this.muda = muda;
        this.estadoMuda = estadoMuda;
        this.sugestoes = sugestoes;
        this.avaliacaoEspecialista = avaliacaoEspecialista;
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
