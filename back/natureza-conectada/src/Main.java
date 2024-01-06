
import models.Muda;


import database.BancoDeDados;
import models.Contato;

import models.Endereco;

import models.Cliente;
import services.ServiceContato;



public class Main {

    public static void main(String[] args) {


        /*try {
            Endereco end = new Endereco(123, "777-6666", "Rua Abc", "123", "CASA", "PORTO ALEGRE", "RS", 1);
            Contato cont = new Contato(123, "models.Contato 1", "9873424234", 3);
            System.out.println(end);
            System.out.println(cont);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }*/
//
//        Endereco end = new Endereco(123, "777-6666", "Rua Abc", "123", "CASA", "PORTO ALEGRE", "RS", 1);
//        Contato cont = new Contato(123, "models.Contato 1", "9873424234", 2);
//        Cliente cliente = new Cliente(1, "Garen, o Paladino", "garen@email.com", "katarina123", "123456789");
//
//        // Adicionando o endereço via ArrayList ao Cliente e exibindo
//        ArrayList<Endereco> enderecosCliente1 = new ArrayList<>();
//        ArrayList<Contato> contatosCliente1 = new ArrayList<>();
//        enderecosCliente1.add(end);
//        contatosCliente1.add(cont);
//
//        cliente.setEnderecos(enderecosCliente1);
//        cliente.setContatos(contatosCliente1);
//
//        cliente.imprimirCliente();

        Muda muda1 = new Muda(0,"da","adas",0,"asdas","adada");

        Muda muda2 = new Muda(0,"da","adas",0,"asdas","adada");

        Muda muda3 = new Muda(0,"da","adas",0,"asdas","adada");




        Endereco end = new Endereco( "777-6666", "Rua Abc", "123", "CASA", "PORTO ALEGRE", "RS", 1);
        Contato cont = new Contato("models.Contato 1", "9873424234", 2);
        Cliente cliente = new Cliente(1, "Garen, o Paladino", "garen@email.com", "katarina123", "123456789");







        ServiceContato sc = new ServiceContato();

        sc.adicionar(cont);
        System.out.println(cont);
        System.out.println(sc.editar(cont.getId(), new Contato("models.Contato 1", "9873424234", 1)));
        System.out.println(sc.procurarPorID(cont.getId()));
        System.out.println(sc.listarTodos());

        System.out.println(sc.listarTodos());


    }
}