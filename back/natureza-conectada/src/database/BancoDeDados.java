package database;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    // ID de todas as classes, não vai mudar diferenciando por listas
    private static int ID = 0;


    // TODO: Adicionar listas de entregas
    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<Cliente> clientes = new ArrayList<>();
    public static List<Especialista> especialistas = new ArrayList<>();
    public static List<Contato> contatos = new ArrayList<>();
    public static List<Endereco> enderecos = new ArrayList<>();
    public static List<Muda> mudas = new ArrayList<>();
    public static List<Relatorio> relatorios = new ArrayList<>();
    public static List<Entrega> entregas = new ArrayList<>();
    // Usar somente quando for criar um id novo
    public static int getNewID() {
        ID++;
        return ID;
    }
}
