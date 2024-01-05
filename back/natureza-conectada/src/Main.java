import models.Contato;
import models.Endereco;
import models.Cliente;

import java.util.ArrayList;

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

        Endereco end = new Endereco(123, "777-6666", "Rua Abc", "123", "CASA", "PORTO ALEGRE", "RS", 1);
        Contato cont = new Contato(123, "models.Contato 1", "9873424234", 2);
        Cliente cliente = new Cliente(1, "Garen, o Paladino", "garen@email.com", "katarina123", "123456789");

        // Adicionando o endereço via ArrayList ao Cliente e exibindo
        ArrayList<Endereco> enderecosCliente1 = new ArrayList<>();
        ArrayList<Contato> contatosCliente1 = new ArrayList<>();
        enderecosCliente1.add(end);
        contatosCliente1.add(cont);

        cliente.setEnderecos(enderecosCliente1);
        cliente.setContatos(contatosCliente1);

        cliente.imprimirCliente();
    }
}