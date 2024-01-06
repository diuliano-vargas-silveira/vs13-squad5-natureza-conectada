package utils;

import java.util.ArrayList;
import enums.StatusEntrega;
import models.*;

public class MockContainer {

    public MockContainer(){
        iniciaObjetos();
    }

    public void iniciaObjetos(){

        // Endereços e Arrays de construção
        Endereco endereco1 = new Endereco("70000-000", "Praça dos Três Poderes", "1", "Palácio do Planalto", "Brasília", "DF", 1);
        Endereco endereco2 = new Endereco("10001-001", "Liberty Island", "1", "Statue of Liberty", "Nova Iorque", "NY", 1);
        Endereco endereco3 = new Endereco("SW1A 0AA", "Westminster", "1", "Big Ben", "Londres", "Inglaterra", 2);
        Endereco endereco4 = new Endereco("2000", "Bennelong Point", "1", "Sydney Opera House", "Sydney", "NSW", 2);
        Endereco endereco5 = new Endereco("00184", "Piazza del Colosseo", "1", "Colosseo", "Roma", "Itália", 1);
        Endereco endereco6 = new Endereco("57751", "Mount Rushmore", "13000", "Sculptor's Studio", "Keystone", "SD", 1);
        Endereco endereco7 = new Endereco("100000", "Jinshanling", "-", "Great Wall of China", "Pequim", "China", 2);
        Endereco endereco8 = new Endereco("282001", "Dharmapuri, Forest Colony", "1", "Taj Mahal", "Agra", "Índia", 1);
        Endereco endereco9 = new Endereco("20500", "1600 Pennsylvania Avenue NW", "1600", "White House", "Washington", "DC", 1);
        Endereco endereco10 = new Endereco("75001", "Rue de Rivoli", "75001", "Louvre Museum", "Paris", "França", 1);
        Endereco endereco11 = new Endereco("10558", "Dionysiou Areopagitou", "1", "Acropolis", "Atenas", "Grécia", 1);
        Endereco endereco12 = new Endereco("12345", "1 Sheikh Mohammed bin Rashid Blvd", "1", "Burj Khalifa", "Dubai", "Emirados Árabes Unidos", 1);

        ArrayList<Endereco> enderecosCliente1 = new ArrayList<>();
        enderecosCliente1.add(endereco1);

        ArrayList<Endereco> enderecosCliente2 = new ArrayList<>();
        enderecosCliente2.add(endereco2);
        enderecosCliente2.add(endereco3);

        ArrayList<Endereco> enderecosCliente3 = new ArrayList<>();
        enderecosCliente3.add(endereco4);
        enderecosCliente3.add(endereco5);
        enderecosCliente3.add(endereco6);

        ArrayList<Endereco> enderecosCliente4 = new ArrayList<>();
        enderecosCliente4.add(endereco7);

        ArrayList<Endereco> enderecosCliente5 = new ArrayList<>();
        enderecosCliente5.add(endereco8);

        ArrayList<Endereco> enderecosCliente6 = new ArrayList<>();
        enderecosCliente6.add(endereco9);

        ArrayList<Endereco> enderecosCliente7 = new ArrayList<>();
        enderecosCliente7.add(endereco10);

        ArrayList<Endereco> enderecosCliente8 = new ArrayList<>();
        enderecosCliente8.add(endereco11);
        enderecosCliente8.add(endereco12);

        // Contatos
        Contato contato1 = new Contato("Celular", "+1 555-1234", 1);
        Contato contato2 = new Contato("Celular", "+1 555-5678", 1);
        Contato contato3 = new Contato("Empresa", "+1 555-8765", 2);
        Contato contato4 = new Contato("Empresa", "+1 555-4321", 2);
        Contato contato5 = new Contato("Celular", "+1 555-9876", 1);
        Contato contato6 = new Contato("Empresa", "+1 555-2345", 2);
        Contato contato7 = new Contato("Celular", "+1 555-6789", 1);
        Contato contato8 = new Contato("Empresa", "+1 555-3456", 2);
        Contato contato9 = new Contato("Celular", "+1 555-7890", 1);
        Contato contato10 = new Contato("Empresa", "+1 555-4567", 2);
        Contato contato11 = new Contato("Celular", "+1 555-8901", 1);
        Contato contato12 = new Contato("Empresa", "+1 555-6789", 2);
        Contato contato13 = new Contato("Celular", "+1 555-0123", 1);
        Contato contato14 = new Contato("Empresa", "+1 555-3456", 2);

        ArrayList<Contato> contatoCliente1 = new ArrayList<>();
        contatoCliente1.add(contato1);

        ArrayList<Contato> contatoCliente2 = new ArrayList<>();
        contatoCliente2.add(contato2);

        ArrayList<Contato> contatoCliente3 = new ArrayList<>();
        contatoCliente3.add(contato3);

        ArrayList<Contato> contatoCliente4 = new ArrayList<>();
        contatoCliente4.add(contato4);
        contatoCliente4.add(contato5);

        ArrayList<Contato> contatoCliente5 = new ArrayList<>();
        contatoCliente5.add(contato6);

        ArrayList<Contato> contatoCliente6 = new ArrayList<>();
        contatoCliente6.add(contato7);

        ArrayList<Contato> contatoCliente7 = new ArrayList<>();
        contatoCliente7.add(contato8);

        ArrayList<Contato> contatoCliente8 = new ArrayList<>();
        contatoCliente8.add(contato9);

        ArrayList<Contato> contatoEspecialista1 = new ArrayList<>();
        contatoEspecialista1.add(contato10);

        ArrayList<Contato> contatoEspecialista2 = new ArrayList<>();
        contatoEspecialista2.add(contato11);
        contatoEspecialista2.add(contato12);

        ArrayList<Contato> contatoEspecialista3 = new ArrayList<>();
        contatoEspecialista3.add(contato13);
        contatoEspecialista3.add(contato14);

        // Mudas - Arrays e objetos

        Muda muda1 = new Muda(1, "Rosa", "Rosa gallica", 1, "Jardim", "Belíssima rosa com pétalas vermelhas");
        Muda muda2 = new Muda(2, "Lírio", "Lilium candidum", 2, "Canteiro", "Lírio branco de grande porte");
        Muda muda3 = new Muda(1, "Orquídea", "Phalaenopsis amabilis", 1, "Estufa", "Orquídea popular com flores duradouras");
        Muda muda4 = new Muda(2, "Margarida", "Leucanthemum vulgare", 2, "Campo", "Margarida simples e encantadora");
        Muda muda5 = new Muda(1, "Girassol", "Helianthus annuus", 1, "Jardim", "Girassol amarelo vibrante");
        Muda muda6 = new Muda(2, "Azaleia", "Rhododendron simsii", 2, "Vaso", "Azaleia de folhas perenes");
        Muda muda7 = new Muda(1, "Orégano", "Origanum vulgare", 1, "Horta", "Erva aromática para culinária");
        Muda muda8 = new Muda(2, "Bromélia", "Neoregalia spp.", 2, "Vaso", "Bromélia colorida e exótica");
        Muda muda9 = new Muda(1, "Lavanda", "Lavandula angustifolia", 1, "Canteiro", "Lavanda perfumada e relaxante");
        Muda muda10 = new Muda(2, "Bambu", "Bambusoideae", 2, "Jardim", "Bambu ornamental e resistente");
        Muda muda11 = new Muda(1, "Cacto", "Cactaceae", 1, "Soleira", "Cacto de fácil manutenção");
        Muda muda12 = new Muda(2, "Hortênsia", "Hydrangea macrophylla", 2, "Vaso", "Hortênsia de flores globosas");
        Muda muda13 = new Muda(1, "Suculenta", "Echeveria spp.", 1, "Soleira", "Suculenta com rosetas coloridas");
        Muda muda14 = new Muda(2, "Jasmim", "Jasminum spp.", 2, "Canteiro", "Jasmim perfumado e elegante");

        ArrayList<Muda> listaDeMudas1 = new ArrayList<>();
        listaDeMudas1.add(muda1);
        listaDeMudas1.add(muda2);

        ArrayList<Muda> listaDeMudas2 = new ArrayList<>();
        listaDeMudas2.add(muda3);
        listaDeMudas2.add(muda4);

        ArrayList<Muda> listaDeMudas3 = new ArrayList<>();
        listaDeMudas3.add(muda5);

        ArrayList<Muda> listaDeMudas4 = new ArrayList<>();
        listaDeMudas4.add(muda6);
        listaDeMudas4.add(muda7);
        listaDeMudas4.add(muda8);

        ArrayList<Muda> listaDeMudas5 = new ArrayList<>();
        listaDeMudas5.add(muda9);

        ArrayList<Muda> listaDeMudas6 = new ArrayList<>();
        listaDeMudas6.add(muda10);
        listaDeMudas6.add(muda11);

        ArrayList<Muda> listaDeMudas7 = new ArrayList<>();
        listaDeMudas7.add(muda12);
        listaDeMudas7.add(muda13);
        listaDeMudas7.add(muda14);

        ArrayList<Muda> listaDeMudas8 = new ArrayList<>();
        listaDeMudas8.add(muda14);

        // Entregas
        Entrega entrega1 = new Entrega(1, listaDeMudas1, StatusEntrega.RECEBIDO, null);
        Entrega entrega2 = new Entrega(2, listaDeMudas2, StatusEntrega.ENVIADO, null);
        Entrega entrega3 = new Entrega(3, listaDeMudas3, StatusEntrega.ENTREGUE, null);
        Entrega entrega4 = new Entrega(4, listaDeMudas4, StatusEntrega.RECEBIDO, null);
        Entrega entrega5 = new Entrega(5, listaDeMudas5, StatusEntrega.ENVIADO, null);
        Entrega entrega6 = new Entrega(6, listaDeMudas6, StatusEntrega.ENTREGUE, null);
        Entrega entrega7 = new Entrega(7, listaDeMudas7, StatusEntrega.RECEBIDO, null);
        Entrega entrega8 = new Entrega(8, listaDeMudas8, StatusEntrega.ENVIADO, null);


        Cliente cliente1 = new Cliente(1, "Willian", "willian@dbc.com.br", "senhadificil", "123.456.789-09");
        Cliente cliente2 = new Cliente(1, "Diuliano", "Diuliano@dbc.com.br", "senhadificil", "987.654.321-00");
        Cliente cliente3 = new Cliente(1, "Luh Santos", "luh.santos@dbc.com.br", "senhadificil", "111.222.333-44");
        Cliente cliente4 = new Cliente(1, "Pedro", "pedro@dbc.com.br", "senhadificil", "555.666.777-88");
        Cliente cliente5 = new Cliente(1, "Rafael", "rafael@dbc.com.br", "senhadificil", "999.000.111-22");
    }
}
