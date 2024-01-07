package database;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    // ID de todas as classes, vai mudar diferenciando por listas
    private static int IDCliente = 0;
    private static int IDEspecialita = 0;
    private static int IDAdmin = 0;
    private static int IDContato = 0;
    private static int IDEndereco = 0;
    private static int IDMudas = 0;
    private static int IDEntrega = 0;
    private static int IDRelatorios = 0;

    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<Cliente> clientes = new ArrayList<>();
    public static List<Especialista> especialistas = new ArrayList<>();
    public static List<Admin> admins = new ArrayList<>();
    public static List<Contato> contatos = new ArrayList<>();
    public static List<Endereco> enderecos = new ArrayList<>();
    public static List<Entrega> entregas = new ArrayList<>();
    public static List<Muda> mudas = new ArrayList<>();
    public static List<Relatorio> relatorios = new ArrayList<>();


    // Usar somente quando for criar um id novo
    public static int gerarNovoIdContato() {
        IDContato++;
        return IDContato;
    }
    public static int gerarNovoIDMudas() {
        IDMudas++;
        return IDMudas;

    }
    public static int gerarNovoIdCliente() {
        IDCliente++;
        return IDCliente;
    }
    public static int gerarNovoIdEndereco() {
        IDEndereco++;
        return IDEndereco;
    }
    public static int gerarNovoIdEspecialista() {
        IDEspecialita++;
        return IDEspecialita;
    }
    public static int gerarNovoIdAdmin() {
        IDAdmin++;
        return IDAdmin;
    }
    public static int gerarNovoIdRelatorio() {
        IDRelatorios++;
        return IDRelatorios;
    }
    public static int gerarNovoIdEntrega() {
        IDEntrega++;
        return IDEntrega;
    }



}
