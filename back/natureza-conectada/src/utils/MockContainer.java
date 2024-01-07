package utils;

import java.util.ArrayList;
import enums.StatusEntrega;
import enums.Estados;
import models.*;
import services.ServiceCliente;

public class MockContainer {

    public MockContainer(){
        iniciaObjetos();
    }

    public void iniciaObjetos(){

        // Endereços - objetos
        Endereco endereco1 = new Endereco("70000-000", "Praça dos Três Poderes", "1", "Palácio do Planalto", "Brasília", Estados.DF, 1);
        Endereco endereco2 = new Endereco("10001-001", "Copacabana Beach", "1", "Cristo Redentor", "Rio de Janeiro", Estados.RJ, 1);
        Endereco endereco3 = new Endereco("SW1A 0AA", "Praça da Sé", "1", "Pátio do Colégio", "São Paulo", Estados.SP, 2);
        Endereco endereco4 = new Endereco("2000", "Ibirapuera Park", "1", "MASP - Museu de Arte de São Paulo", "São Paulo", Estados.SP, 2);
        Endereco endereco5 = new Endereco("00184", "Praia de Iracema", "1", "Dragão do Mar Center of Art and Culture", "Fortaleza", Estados.CE, 1);
        Endereco endereco6 = new Endereco("57751", "Praça da Liberdade", "13000", "Mineirão", "Belo Horizonte", Estados.MG, 1);
        Endereco endereco7 = new Endereco("100000", "Praia de Boa Viagem", "-", "Recife Antigo", "Recife", Estados.PE, 2);
        Endereco endereco8 = new Endereco("282001", "Praia de Ponta Negra", "1", "Forte dos Reis Magos", "Natal", Estados.RN, 1);
        Endereco endereco9 = new Endereco("20500", "Praça dos Três Poderes", "1600", "Palácio do Buriti", "Brasília", Estados.DF, 1);
        Endereco endereco10 = new Endereco("75001", "Praça da Liberdade", "75001", "Praça da Estação", "Belo Horizonte", Estados.MG, 1);
        Endereco endereco11 = new Endereco("10558", "Praia de Ponta Negra", "1", "Arena das Dunas", "Natal", Estados.RN, 1);
        Endereco endereco12 = new Endereco("12345", "Copacabana Beach", "1", "Pão de Açúcar", "Rio de Janeiro", Estados.RJ, 1);
        Endereco endereco13 = new Endereco("70000-001", "Parque do Ibirapuera", "1", "MASP - Museu de Arte de São Paulo", "São Paulo", Estados.SP, 1);
        Endereco endereco14 = new Endereco("20001", "Praia de Iracema", "1", "Mercado Central", "Fortaleza", Estados.CE, 1);
        Endereco endereco15 = new Endereco("57752", "Praça da Estação", "13001", "Mercado Central", "Belo Horizonte", Estados.MG, 1);
        Endereco endereco16 = new Endereco("100001", "Recife Antigo", "-", "Praia de Boa Viagem", "Recife", Estados.PE, 2);
        Endereco endereco17 = new Endereco("282002", "Ponta Negra", "1", "Centro de Turismo de Natal", "Natal", Estados.RN, 1);

        // Contatos - objetos
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
        Contato contato15 = new Contato("Celular", "+55 11 98765-4321", 1);
        Contato contato16 = new Contato("Empresa", "+55 21 1234-5678", 2);
        Contato contato17 = new Contato("Celular", "+55 31 8765-4321", 1);
        Contato contato18 = new Contato("Empresa", "+55 85 2345-6789", 2);

        // Mudas - Listas e objetos
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
        Muda muda15 = new Muda(1, "Ipê Amarelo", "Handroanthus spp.", 1, "Jardim", "Árvore brasileira com flores amarelas vibrantes");
        Muda muda16 = new Muda(2, "Cerejeira", "Prunus serrulata", 2, "Canteiro", "Árvore ornamental com flores de cerejeira");
        Muda muda17 = new Muda(1, "Samambaia", "Nephrolepis exaltata", 1, "Vaso", "Planta ornamental com folhas delicadas");
        Muda muda18 = new Muda(2, "Agave", "Agave americana", 2, "Soleira", "Planta suculenta resistente, conhecida como 'pita'");

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

        ArrayList<Muda> listaDeMudas8 = new ArrayList<>();
        listaDeMudas8.add(muda14);

        ArrayList<Muda> listaDeMudas9 = new ArrayList<>();
        listaDeMudas9.add(muda15);
        listaDeMudas9.add(muda16);

        ArrayList<Muda> listaDeMudas10 = new ArrayList<>();
        listaDeMudas10.add(muda17);
        listaDeMudas10.add(muda18);

        // Entregas - objetos
        Entrega entrega1 = new Entrega(1, listaDeMudas1, StatusEntrega.RECEBIDO, null);
        Entrega entrega2 = new Entrega(2, listaDeMudas2, StatusEntrega.ENVIADO, null);
        Entrega entrega3 = new Entrega(3, listaDeMudas3, StatusEntrega.ENTREGUE, null);
        Entrega entrega4 = new Entrega(4, listaDeMudas4, StatusEntrega.RECEBIDO, null);
        Entrega entrega5 = new Entrega(5, listaDeMudas5, StatusEntrega.ENVIADO, null);
        Entrega entrega6 = new Entrega(6, listaDeMudas6, StatusEntrega.ENTREGUE, null);
        Entrega entrega7 = new Entrega(7, listaDeMudas7, StatusEntrega.RECEBIDO, null);
        Entrega entrega8 = new Entrega(8, listaDeMudas8, StatusEntrega.ENVIADO, null);
        Entrega entrega9 = new Entrega(9, listaDeMudas9, StatusEntrega.ENVIADO, null);
        Entrega entrega10 = new Entrega(10, listaDeMudas10, StatusEntrega.ENTREGUE, null);

        // Clientes e suas atribuições
        ServiceCliente serviceCliente = new ServiceCliente();

        Cliente cliente1 = new Cliente("Willian Cavalheiro", "willian@dbc.com.br", "senhadificil", "123.456.789-09");
        serviceCliente.adicionarContato(cliente1, contato1);
        serviceCliente.adicionarContato(cliente1, contato2);
        serviceCliente.adicionarEndereco(cliente1, endereco1);
        serviceCliente.adicionarEndereco(cliente1, endereco2);
        serviceCliente.adicionarMuda(cliente1, muda1);
        serviceCliente.adicionarMuda(cliente1, muda2);
        serviceCliente.adicionarEntregas(cliente1, entrega1);

        Cliente cliente2 = new Cliente("Diuliano Vargas", "Diuliano@dbc.com.br", "senhadificil", "987.654.321-00");
        serviceCliente.adicionarContato(cliente2, contato3);
        serviceCliente.adicionarEndereco(cliente2, endereco3);
        serviceCliente.adicionarMuda(cliente2, muda3);
        serviceCliente.adicionarMuda(cliente2, muda4);
        serviceCliente.adicionarEntregas(cliente2, entrega2);

        Cliente cliente3 = new Cliente("Luísa Santos", "luh.santos@dbc.com.br", "senhadificil", "111.222.333-44");
        serviceCliente.adicionarContato(cliente3, contato4);
        serviceCliente.adicionarContato(cliente3, contato5);
        serviceCliente.adicionarEndereco(cliente3, endereco4);
        serviceCliente.adicionarMuda(cliente3, muda5);
        serviceCliente.adicionarEntregas(cliente3, entrega3);

        Cliente cliente4 = new Cliente("Pedro Antonetti", "pedro@dbc.com.br", "senhadificil", "555.666.777-88");
        serviceCliente.adicionarContato(cliente4, contato6);
        serviceCliente.adicionarEndereco(cliente4, endereco5);
        serviceCliente.adicionarMuda(cliente4, muda6);
        serviceCliente.adicionarMuda(cliente4, muda7);
        serviceCliente.adicionarMuda(cliente4, muda8);
        serviceCliente.adicionarEntregas(cliente4, entrega4);

        Cliente cliente5 = new Cliente("Janier Freitas", "janier@dbc.com.br", "senhadificil", "999.000.666-22");
        serviceCliente.adicionarContato(cliente5, contato7);
        serviceCliente.adicionarContato(cliente5, contato8);
        serviceCliente.adicionarEndereco(cliente5, endereco6);
        serviceCliente.adicionarEndereco(cliente5, endereco7);
        serviceCliente.adicionarMuda(cliente5, muda9);
        serviceCliente.adicionarEntregas(cliente5, entrega5);

        Cliente cliente6 = new Cliente("Thales Salla", "thales@dbc.com.br", "senhadificil", "999.000-999-22");
        serviceCliente.adicionarContato(cliente6, contato9);
        serviceCliente.adicionarContato(cliente6, contato10);
        serviceCliente.adicionarContato(cliente6, contato11);
        serviceCliente.adicionarEndereco(cliente6, endereco8);
        serviceCliente.adicionarMuda(cliente6, muda10);
        serviceCliente.adicionarMuda(cliente6, muda11);
        serviceCliente.adicionarEntregas(cliente6, entrega6);

        Cliente cliente7 = new Cliente("Carlos Queiroz", "carlos@dbc.com.br", "senhadificil", "999.000.555-22");
        serviceCliente.adicionarContato(cliente7, contato12);
        serviceCliente.adicionarEndereco(cliente7, endereco9);
        serviceCliente.adicionarEndereco(cliente7, endereco10);
        serviceCliente.adicionarMuda(cliente7, muda12);
        serviceCliente.adicionarMuda(cliente7, muda13);
        serviceCliente.adicionarEntregas(cliente7, entrega7);

        Cliente cliente8 = new Cliente("Welton Santos", "welton@dbc.com.br", "senhadificil", "999.000.444-22");
        serviceCliente.adicionarContato(cliente8, contato13);
        serviceCliente.adicionarContato(cliente8, contato14);
        serviceCliente.adicionarEndereco(cliente8, endereco11);
        serviceCliente.adicionarEndereco(cliente8, endereco12);
        serviceCliente.adicionarMuda(cliente8, muda14);
        serviceCliente.adicionarEntregas(cliente8, entrega8);

        Cliente cliente9 = new Cliente("Rafael Camilo", "rcamilo@dbc.com.br", "senhadificil", "999.000.444-22");
        serviceCliente.adicionarContato(cliente9, contato15);
        serviceCliente.adicionarContato(cliente9, contato16);
        serviceCliente.adicionarEndereco(cliente9, endereco13);
        serviceCliente.adicionarEndereco(cliente9, endereco14);
        serviceCliente.adicionarMuda(cliente9, muda15);
        serviceCliente.adicionarMuda(cliente9, muda16);
        serviceCliente.adicionarEntregas(cliente9, entrega9);

        Cliente cliente10 = new Cliente("Rafael Ramos", "rramos@dbc.com.br", "senhadificil", "999.000.444-22");
        serviceCliente.adicionarContato(cliente10, contato17);
        serviceCliente.adicionarContato(cliente10, contato18);
        serviceCliente.adicionarEndereco(cliente10, endereco15);
        serviceCliente.adicionarEndereco(cliente10, endereco16);
        serviceCliente.adicionarEndereco(cliente10, endereco17);
        serviceCliente.adicionarMuda(cliente10, muda17);
        serviceCliente.adicionarMuda(cliente10, muda18);
        serviceCliente.adicionarEntregas(cliente10, entrega10);

    }
}
