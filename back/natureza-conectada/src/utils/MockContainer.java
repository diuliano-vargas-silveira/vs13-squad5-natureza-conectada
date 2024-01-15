package utils;

import java.util.ArrayList;

import enums.*;
import models.*;
import services.*;

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
        String[] especializaçãoEspecialista = {"Horticultura Ornamental", "Agronomia", "Silvicultura", "Botânica Aplicada"};
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

        // Criando Contatos
        String[] descricaoContato = {"Celular", "Celular", "Empresa", "Empresa", "Celular", "Empresa", "Celular", "Empresa", "Celular", "Empresa", "Celular", "Empresa", "Celular", "Empresa", "Celular", "Empresa", "Celular", "Empresa", "Empresa"};
        String[] numeroContato = {"15913255123", "15943155567", "15901455876", "15902155432", "15942559876", "15943552345", "54965556789", "15992553456", "15986557890", "15974554567", "15996558901", "55253356789", "51990150123", "51995553456", "11987654321", "21123456780", "31876543210", "85234567890", "85234540000"};
        String[] tipoContato = {"RESIDENCIAL", "RESIDENCIAL", "COMERCIAL", "COMERCIAL", "RESIDENCIAL", "COMERCIAL", "RESIDENCIAL", "COMERCIAL", "RESIDENCIAL", "COMERCIAL", "RESIDENCIAL", "COMERCIAL", "RESIDENCIAL", "COMERCIAL", "RESIDENCIAL", "COMERCIAL", "RESIDENCIAL", "COMERCIAL", "COMERCIAL"};
        int[] idClienteContato = {1, 2, 1, 2, 3, 3, 4, 4, 5, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

        ServiceContato serviceContato = new ServiceContato();

        Contato contato = new Contato();
        for (int i = 0; i < 19; i++){
            contato.setDescricao(descricaoContato[i]);
            contato.setNumero(numeroContato[i]);
            contato.setTipo(tipoContato[i]);
            serviceContato.adicionarContato(contato, idClienteContato[i]);

        }

        // Criando Endereços

        String[] cepEndereco = {"12345678", "98765432", "54321876", "87654321", "23456789", "67890123", "98765432", "12345678", "54321876", "87654321", "23456789", "67890123", "98765432", "12345678", "54321876", "87654321", "23456789", "67890123", "87654321"};
        String[] logradouroEndereco = {"Praça dos Três Poderes", "Copacabana Beach", "Praça da Sé", "Ibirapuera Park", "Praia de Iracema", "Praça da Liberdade", "Praia de Boa Viagem", "Praia de Ponta Negra", "Praça dos Três Poderes", "Praça da Liberdade", "Praia de Ponta Negra", "Copacabana Beach", "Parque do Ibirapuera", "Praia de Iracema", "Praça da Estação", "Recife Antigo", "Ponta Negra", "Rua Rio Paraná", "Rua Agice Ramos"};
        String[] numeroEndereco = {"123", "456", "789", "1011", "1213", "1415", "1617", "1819", "2021", "2223", "2425", "2627", "2829", "3031", "3233", "3435", "3637", "3839", "4041"};
        String[] complementoEndereco = {"Palácio do Planalto", "Cristo Redentor", "Pátio do Colégio", "MASP", "Dragão do Mar", "Mineirão", "Recife Antigo", "Forte dos Reis Magos", "Palácio do Buriti", "Praça da Estação", "Arena das Dunas", "Pão de Açúcar", "MASP", "Mercado Central", "Mercado Central", "Praia de Boa Viagem", "Centro de Turismo", "Rio Jacuí", "Depósito Tramonta"};
        String[] cidadeEndereco = {"Brasília", "Rio de Janeiro", "São Paulo", "São Paulo", "Fortaleza", "Belo Horizonte", "Recife", "Natal", "Brasília", "Belo Horizonte", "Natal", "Rio de Janeiro", "São Paulo", "Fortaleza", "Belo Horizonte", "Recife", "Natal", "Charqueadas", "Charqueadas"};
        Estados[] estadoEndereco = {Estados.DF, Estados.RJ, Estados.SP, Estados.SP, Estados.CE, Estados.MG, Estados.PE, Estados.RN, Estados.DF, Estados.MG, Estados.RN, Estados.RJ, Estados.SP, Estados.CE, Estados.MG, Estados.PE, Estados.RN, Estados.RS, Estados.RS};
        int[] tipoEndereco = {1, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2};
        int[] idClienteEndereco = {1, 2, 1, 2, 3, 4, 4, 5, 5, 6, 6, 7, 8, 9, 10, 11, 12, 13, 14};

        ServiceEndereco serviceEndereco = new ServiceEndereco();

        Endereco endereco = new Endereco();

        for (int i = 0; i < 19; i++) {
            endereco.setCep(cepEndereco[i]);
            endereco.setLogradouro(logradouroEndereco[i]);
            endereco.setNumero(numeroEndereco[i]);
            endereco.setComplemento(complementoEndereco[i]);
            endereco.setCidade(cidadeEndereco[i]);
            endereco.setEstado(estadoEndereco[i]);
            endereco.setTipo(tipoEndereco[i]);
            serviceEndereco.adicionar(endereco, idClienteEndereco[i]);
        }



    }
    /*

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

