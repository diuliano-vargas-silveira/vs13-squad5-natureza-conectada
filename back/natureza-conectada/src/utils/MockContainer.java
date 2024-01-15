package utils;

import java.util.ArrayList;
import enums.StatusEntrega;
import enums.Estados;
import enums.TipoUsuario;
import models.*;
import services.*;
import repository.*;

public class MockContainer {

    public void iniciaObjetos() throws Exception {


        // Criando Clientes

        String[] nomeCliente = {"Willian Cavalheiro", "Diuliano Vargas", "Luísa Santos", "Pedro Antonetti", "Janier Freitas", "Thales Salla", "Carlos Queiroz", "Welton Santos", "Rafael Camilo", "Rafael Ramos"};
        String[] emailCliente = {"willian@dbc.com.br", "Diuliano@dbc.com.br", "luh.santos@dbc.com.br", "pedro@dbc.com.br", "janier@dbc.com.br", "thales@dbc.com.br", "carlos@dbc.com.br", "welton@dbc.com.br", "rcamilo@dbc.com.br", "rramos@dbc.com.br"};
        String[] cpfCliente = {"12345678909", "98765432100", "11122233344", "55566677788", "99900066622", "99900099922", "99900055522", "99900014422", "99900044422", "99900034422"};

        ServiceCliente serviceCliente = new ServiceCliente();

        Cliente cliente = new Cliente();
        for (int i = 0; i < 10; i++){
            cliente.setNome(nomeCliente[i]);
            cliente.setEmail(emailCliente[i]);
            cliente.setSenha("senhadificil");
            cliente.setCpf(cpfCliente[i]);
            serviceCliente.adicionar(cliente);
        }


        // Criando Especialistas
        String[] nomeEspecialista = {"Rafael Lazzari", "Ályson Campos", "Mayra Amaral", "Cristina Jung"};
        String[] emailEspecialista = {"rlazzari@dbc.com.br", "alysonc@dbc.com.br", "mayraa@dbc.com.br", "crisjung@dbc.com.br"};
        String[] documentoEspecialista = {"999.999.444-00", "999.999.444-13", "999.999.444-25", "999.999.444-12"};
        String[] especializaçãoEspecialista = {"Horticultura Ornamental", "Agronomia", "Silvicultura", "Botânica Aplicada:"};
        Estados[] regiaoResponsavelEspecialista = {Estados.SP, Estados.RS, Estados.PB, Estados.AM};

        ServiceEspecialista serviceEspecialista = new ServiceEspecialista();

        Especialista especialista = new Especialista();
        for (int i = 0; i < 4; i++){
            especialista.setNome(nomeEspecialista[i]);
            especialista.setEmail(emailEspecialista[i]);
            especialista.setSenha("senhadificil");
            especialista.setDocumento(documentoEspecialista[i]);
            especialista.setEspecializacao(especializaçãoEspecialista[i]);
            especialista.setRegiaoResponsavel(regiaoResponsavelEspecialista[i]);
            serviceEspecialista.adicionar(especialista);
        }

        // String[] listaCep = {"70000-000", "10001-001", "SW1A 0AA", "2000"};
        // String[] listaLogradouro = {"Praça dos Três Poderes", "Copacabana Beach"};
        // for(int i = 0; i < 17; i++){
        //    EnderecoRepository enderecoRepository = new EnderecoRepository();
        //    enderecoRepository.adicionar(new Endereco(listaCep[i], listaLogradouro[i],));
        //}

    }
        /*

        // Endereços - criação de objetos e adições ao banco de dados
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

        ServiceEndereco serviceEndereco = new ServiceEndereco();
        serviceEndereco.adicionar(endereco1);
        serviceEndereco.adicionar(endereco2);
        serviceEndereco.adicionar(endereco3);
        serviceEndereco.adicionar(endereco4);
        serviceEndereco.adicionar(endereco5);
        serviceEndereco.adicionar(endereco6);
        serviceEndereco.adicionar(endereco7);
        serviceEndereco.adicionar(endereco8);
        serviceEndereco.adicionar(endereco9);
        serviceEndereco.adicionar(endereco10);
        serviceEndereco.adicionar(endereco11);
        serviceEndereco.adicionar(endereco12);
        serviceEndereco.adicionar(endereco13);
        serviceEndereco.adicionar(endereco14);
        serviceEndereco.adicionar(endereco15);
        serviceEndereco.adicionar(endereco16);
        serviceEndereco.adicionar(endereco17);

        // Contatos - criação de objetos e adições ao banco de dados
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
        Contato contato19 = new Contato("Empresa", "+51 85 2345-4000", 2);
        Contato contato20 = new Contato("Empresa", "+51 85 2345-5000", 2);
        Contato contato21 = new Contato("Empresa", "+51 85 2345-6000", 2);
        Contato contato22 = new Contato("Empresa", "+51 85 2345-7000", 2);

        ServiceContato serviceContato = new ServiceContato();
        serviceContato.adicionar(contato1);
        serviceContato.adicionar(contato2);
        serviceContato.adicionar(contato3);
        serviceContato.adicionar(contato4);
        serviceContato.adicionar(contato5);
        serviceContato.adicionar(contato6);
        serviceContato.adicionar(contato7);
        serviceContato.adicionar(contato8);
        serviceContato.adicionar(contato9);
        serviceContato.adicionar(contato10);
        serviceContato.adicionar(contato11);
        serviceContato.adicionar(contato12);
        serviceContato.adicionar(contato13);
        serviceContato.adicionar(contato14);
        serviceContato.adicionar(contato15);
        serviceContato.adicionar(contato16);
        serviceContato.adicionar(contato17);
        serviceContato.adicionar(contato18);
        serviceContato.adicionar(contato19);
        serviceContato.adicionar(contato20);
        serviceContato.adicionar(contato21);
        serviceContato.adicionar(contato22);

        // Mudas - criação de objetos, adições ao banco de dados e criação de listas
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

        ServiceMudas serviceMudas = new ServiceMudas();
        serviceMudas.adicionar(muda1);
        serviceMudas.adicionar(muda2);
        serviceMudas.adicionar(muda3);
        serviceMudas.adicionar(muda4);
        serviceMudas.adicionar(muda5);
        serviceMudas.adicionar(muda6);
        serviceMudas.adicionar(muda7);
        serviceMudas.adicionar(muda8);
        serviceMudas.adicionar(muda9);
        serviceMudas.adicionar(muda10);
        serviceMudas.adicionar(muda11);
        serviceMudas.adicionar(muda12);
        serviceMudas.adicionar(muda13);
        serviceMudas.adicionar(muda14);
        serviceMudas.adicionar(muda15);
        serviceMudas.adicionar(muda16);
        serviceMudas.adicionar(muda17);
        serviceMudas.adicionar(muda18);

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
        Entrega entrega1 = new Entrega( listaDeMudas1, StatusEntrega.RECEBIDO, null);
        Entrega entrega2 = new Entrega( listaDeMudas2, StatusEntrega.ENVIADO, null);
        Entrega entrega3 = new Entrega( listaDeMudas3, StatusEntrega.ENTREGUE, null);
        Entrega entrega4 = new Entrega( listaDeMudas4, StatusEntrega.RECEBIDO, null);
        Entrega entrega5 = new Entrega( listaDeMudas5, StatusEntrega.ENVIADO, null);
        Entrega entrega6 = new Entrega( listaDeMudas6, StatusEntrega.ENTREGUE, null);
        Entrega entrega7 = new Entrega( listaDeMudas7, StatusEntrega.RECEBIDO, null);
        Entrega entrega8 = new Entrega( listaDeMudas8, StatusEntrega.ENVIADO, null);
        Entrega entrega9 = new Entrega( listaDeMudas9, StatusEntrega.ENVIADO, null);
        Entrega entrega10 = new Entrega( listaDeMudas10, StatusEntrega.ENTREGUE, null);

        ServiceEntrega serviceEntrega = new ServiceEntrega();
        serviceEntrega.adicionar(entrega1);
        serviceEntrega.adicionar(entrega2);
        serviceEntrega.adicionar(entrega3);
        serviceEntrega.adicionar(entrega4);
        serviceEntrega.adicionar(entrega5);
        serviceEntrega.adicionar(entrega6);
        serviceEntrega.adicionar(entrega7);
        serviceEntrega.adicionar(entrega8);
        serviceEntrega.adicionar(entrega9);
        serviceEntrega.adicionar(entrega10);


        // Administrador - criação de objetos, adoções ao banco de dados
        ServiceAdmin serviceAdmin = new ServiceAdmin();

        Admin admin = new Admin("Administrador", "administrador@naturezaconectada.com", "senhadificil");
        serviceAdmin.adicionar(admin);

        ServiceRelatorio serviceRelatorio = new ServiceRelatorio();
        Relatorio relatorio = new Relatorio(cliente10, null,  "Bom","");
        Relatorio relatorio2 = new Relatorio(cliente10, null,  "Ruim", "");

        serviceRelatorio.adicionar(relatorio);
        serviceRelatorio.adicionar(relatorio2);

    }
     */
}

