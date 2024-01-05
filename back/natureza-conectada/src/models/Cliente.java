package models;

import enums.TipoUsuario;

import java.util.ArrayList;

public class Cliente extends Usuario {
    private String cpf;
    private ArrayList<Endereco> enderecos = new ArrayList<>();
    private ArrayList<Contato> contatos = new ArrayList<>();

    // Construtor
    public Cliente(int ID, String nome, String email, String senha, String cpf) {
        super(ID, nome, email, senha, TipoUsuario.CLIENTE);
        this.cpf = cpf;
    }

    // Setters e Getters
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

    public void imprimirCliente() {
        System.out.println("------------------------------");
        System.out.println("ID: " + getId());
        System.out.println("Cliente: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("E-mail: " + getEmail());
        System.out.println("------------------------------");
        System.out.println("Contatos:");
        imprimirContatos();
        System.out.println("------------------------------");
        System.out.println("Endereços:");
        imprimirEnderecos();
        System.out.println("------------------------------");
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