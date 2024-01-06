package models;

import enums.TipoUsuario;
import java.util.ArrayList;

public class Cliente extends Usuario {
    private int id;
    private String cpf;
    private ArrayList<Endereco> enderecos = new ArrayList<>();
    private ArrayList<Contato> contatos = new ArrayList<>();
    private ArrayList<Muda> mudas = new ArrayList<>();


    // Construtor
    public Cliente(int ID, String nome, String email, String senha, String cpf) {
        super(ID, nome, email, senha, TipoUsuario.CLIENTE);
        this.cpf = cpf;
    }

    // Setters e Getters
    public void setId(int id){ this.id = id;}
    public int getID() {
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

    // Exibição
    public void imprimirContatos() {
        for (Contato contato : contatos) {
            if (contato == null) {
                break;
            }
            System.out.println(contato.toString());
        }
    }

    public void imprimirEnderecos() {
        for (Endereco endereco : enderecos) {
            if (endereco == null) {
                break;
            }
            System.out.println(endereco.toString());
        }
    }

    public void imprimirMudas() {
        for (Muda muda : mudas) {
            if (muda == null) {
                break;
            }
            System.out.println(muda.toString());
        }
    }

    //Métodos de adicionar objetos externos

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