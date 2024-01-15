package utils;

import java.util.ArrayList;
import java.util.List;

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
        Tipo[] tipoEndereco = {Tipo.RESIDENCIAL, Tipo.RESIDENCIAL, Tipo.COMERCIAL, Tipo.COMERCIAL, Tipo.RESIDENCIAL, Tipo.COMERCIAL, Tipo.RESIDENCIAL, Tipo.COMERCIAL, Tipo.RESIDENCIAL, Tipo.COMERCIAL, Tipo.RESIDENCIAL, Tipo.COMERCIAL, Tipo.RESIDENCIAL, Tipo.COMERCIAL, Tipo.RESIDENCIAL, Tipo.COMERCIAL, Tipo.RESIDENCIAL, Tipo.COMERCIAL, Tipo.COMERCIAL};
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

        // Criando administrador

        ServiceAdmin serviceAdmin = new ServiceAdmin();
        Admin admin = new Admin("Administrador", "administrador@naturezaconectada.com", "senhadificil");
        serviceAdmin.adicionar(admin);

        // Criando Mudas

        TipoMuda[] tipoMuda = {TipoMuda.PLANTA, TipoMuda.PLANTA, TipoMuda.PLANTA, TipoMuda.PLANTA, TipoMuda.PLANTA, TipoMuda.PLANTA, TipoMuda.PLANTA, TipoMuda.PLANTA, TipoMuda.PLANTA, TipoMuda.PLANTA, TipoMuda.ARVORE, TipoMuda.PLANTA, TipoMuda.PLANTA, TipoMuda.PLANTA, TipoMuda.ARVORE, TipoMuda.ARVORE, TipoMuda.PLANTA, TipoMuda.PLANTA};
        int[] quantidadeMuda = {3, 10, 50, 1, 7, 7, 4, 8, 4, 3, 11, 21, 35, 64, 2, 13, 17, 18};
        String[] nomeMuda = {"Rosa", "Lírio", "Orquídea", "Margarida", "Girassol", "Azaleia", "Orégano", "Bromélia", "Lavanda", "Bambu", "Cacto", "Hortênsia", "Suculenta", "Jasmim", "Ipê Amarelo", "Cerejeira", "Samambaia", "Agave", };
        String[] nomeCientificoMuda = {"Rosa gallica", "Lilium candidum", "Phalaenopsis amabilis", "Leucanthemum vulgare", "Helianthus annuus", "Rhododendron simsii", "Origanum vulgare", "Neoregalia spp.", "Lavandula angustifolia", "Bambusoideae", "Cactaceae", "Hydrangea macrophylla", "Echeveria spp.", "Jasminum spp.", "Handroanthus spp.", "Prunus serrulata", "Nephrolepis exaltata", "Agave americana"};
        TamanhoMuda[] tamanhoMuda = {TamanhoMuda.PEQUENO, TamanhoMuda.MEDIO, TamanhoMuda.PEQUENO, TamanhoMuda.MEDIO, TamanhoMuda.PEQUENO, TamanhoMuda.MEDIO, TamanhoMuda.PEQUENO, TamanhoMuda.MEDIO, TamanhoMuda.PEQUENO, TamanhoMuda.MEDIO, TamanhoMuda.PEQUENO, TamanhoMuda.MEDIO, TamanhoMuda.PEQUENO, TamanhoMuda.MEDIO, TamanhoMuda.GRANDE, TamanhoMuda.GRANDE, TamanhoMuda.PEQUENO, TamanhoMuda.MEDIO};
        String[] ambienteIdealMuda = {"Jardim", "Canteiro", "Estufa", "Campo", "Jardim", "Vaso", "Horta", "Vaso", "Canteiro", "Jardim", "Soleira", "Vaso", "Soleira", "Canteiro", "Jardim", "Canteiro", "Vaso", "Soleira"};
        String[] descricaoMuda = {"Belíssima rosa com pétalas vermelhas", "Lírio branco de grande porte", "Orquídea popular com flores duradouras", "Margarida simples e encantadora", "Girassol amarelo vibrante", "Azaleia de folhas perenes", "Erva aromática para culinária", "Bromélia colorida e exótica", "Lavanda perfumada e relaxante", "Bambu ornamental e resistente", "Cacto de fácil manutenção", "Hortênsia de flores globosas", "Suculenta com rosetas coloridas", "Jasmim perfumado e elegante", "Árvore brasileira com flores amarelas vibrantes", "Árvore ornamental com flores de cerejeira", "Planta ornamental com folhas delicadas", "Planta suculenta resistente, conhecida como 'pita'"};

        ServiceMudas serviceMudas = new ServiceMudas();

        Muda muda = new Muda();

        for(int i = 0; i < 18; i++) {
            muda.setTipo(tipoMuda[i]);
            muda.setQuantidade(quantidadeMuda[i]);
            muda.setNome(nomeMuda[i]);
            muda.setNomeCientifico(nomeCientificoMuda[i]);
            muda.setPorte(tamanhoMuda[i]);
            muda.setAmbienteIdeal(ambienteIdealMuda[i]);
            muda.setDescricao(descricaoMuda[i]);
            serviceMudas.adicionar(muda);
        }

        String[] estadoMudaRelatorio = {"Bom", "Ruim"};
        int[] idClienteRelatorio = {1, 2};
        int[] idAvaliadorRelatorio = {1, 2};
        int[] idMudaRelatorio = {1, 2};

        ServiceRelatorio serviceRelatorio = new ServiceRelatorio();

        Relatorio relatorio = new Relatorio();

        for (int i = 0; i < 2; i++) {
            relatorio.setEstadoMuda(estadoMudaRelatorio[i]);
            serviceRelatorio.adicionar(relatorio, idClienteRelatorio[i], idAvaliadorRelatorio[i], idMudaRelatorio[i]);
        }

        // Criando Entregas
        ServiceMudas serviceMudas2 = new ServiceMudas();
        List<Muda> mudas = new ArrayList<>();
        mudas.add(serviceMudas.buscarPorId(1));
        mudas.add(serviceMudas.buscarPorId(2));
        mudas.get(0).setQuantidade(2);
        mudas.get(1).setQuantidade(1);

        ServiceCliente serviceCliente2 = new ServiceCliente();
        Cliente cliente2 = new Cliente();
        cliente2 = serviceCliente2.procurarPorID(1);

        ServiceEntrega serviceEntrega = new ServiceEntrega();
        Entrega entrega = new Entrega();
        entrega.setMudas(mudas);
        entrega.setStatus(StatusEntrega.ENVIADO);
        entrega.setCliente(cliente2);

        serviceEntrega.adicionar(entrega , 1);

    }
}

