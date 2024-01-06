package models;

import enums.TipoUsuario;
import java.util.ArrayList;

public class Cliente extends Usuario {
    private int id;
    private String cpf;
    private ArrayList<Endereco> enderecos = new ArrayList<>();
    private ArrayList<Contato> contatos = new ArrayList<>();
    private ArrayList<Muda> mudas = new ArrayList<>();
    private ArrayList<Entrega> entregas = new ArrayList<>();


    // Setters e Getters
    public ArrayList<Entrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(ArrayList<Entrega> entregas) {
        this.entregas = entregas;
    }


    // Construtor
    public Cliente(int ID, String nome, String email, String senha, String cpf) {
        super(ID, nome, email, senha, TipoUsuario.CLIENTE);
        this.cpf = cpf;
    }

    // Setters e Getters
    public void setId(int id){ this.id = id;}

    public int getId() {
        return id;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getCpf() {
        return cpf;
    }

    public void setEnderecos(ArrayList<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
    public ArrayList<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setContatos(ArrayList<Contato> contatos) {
        this.contatos = contatos;
    }
    public ArrayList<Contato> getContatos() {
        return contatos;
    }

    public void setMuda(ArrayList<Muda> muda) {
        this.mudas = muda;
    }
    public ArrayList<Muda> getMuda() {
        return mudas;
    }

    public ArrayList<Muda> getMudas() {
        return mudas;
    }

    public void setMudas(ArrayList<Muda> mudas) {
        this.mudas = mudas;
    }

    // Exibição
    private void imprimirLista(ArrayList<?> lista, String tipo) {
        System.out.println("Lista de " + tipo + ":");
        for (Object item : lista) {
            if (item != null) {
                System.out.println(item.toString());
            }
        }
        System.out.println();
    }

    // Métodos de impressão
    public void imprimirContatos() {
        imprimirLista(contatos, "Contatos");
    }

    public void imprimirEnderecos() {
        imprimirLista(enderecos, "Endereços");
    }

    public void imprimirMudas() {
        imprimirLista(mudas, "Mudas");
    }

    public void imprimirEntregas() {
        imprimirLista(entregas, "Entregas");
    }

    //Métodos de adição

    public void adicionarContato(Contato contato) {
        contatos.add(contato);
    }
    public void adicionarEndereco(Endereco endereco) {
        enderecos.add(endereco);
    }
    public void adicionarMuda(Muda muda) {
        mudas.add(muda);
    }

    @Override
    public String toString() {
        return "\nID: " + getId() +
                "\nNome: " + getNome() +
                "\nCPF: " + getCpf() +
                "\nE-mail: " + getEmail()
                ;
    }

}