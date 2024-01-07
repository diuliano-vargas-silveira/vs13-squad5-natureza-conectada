package utils;

import database.BancoDeDados;
import enums.Estados;
import enums.StatusEntrega;
import enums.TipoUsuario;
import models.*;
import services.*;
import java.util.List;
import java.util.Objects;

public class Menu {
    private static int opcaoMenuIncial = 0;
    private static Usuario usuarioCadastrado;
    private static final String QUEBRA_DE_LINHA = "| -------------------------------------------------------------------------- |";
    private static final String OPCAO_INVALIDA = "| Opção inválida! Digite novamente.";
    private static final int SAIR_PROGRAMA = 3;
    private static final ServiceAdmin serviceAdmin = new ServiceAdmin();
    private static final ServiceCliente serviceCliente = new ServiceCliente();
    private static final ServiceEspecialista serviceEspecialista = new ServiceEspecialista();
    private static final ServiceMudas serviceMudas = new ServiceMudas();
    private static final ServiceUsuario serviceUsuario = new ServiceUsuario();
    private static final ServiceContato serviceContato = new ServiceContato();
    private static final ServiceEndereco serviceEndereco = new ServiceEndereco();
    private static final ServiceRelatorio serviceRelatorio = new ServiceRelatorio();

    private static final ServiceEntrega serviceEntrega = new ServiceEntrega();

    public static void rodarAplicacao() {
        do {
            menuIncial();
        } while (opcaoMenuIncial != SAIR_PROGRAMA);
        System.out.println(QUEBRA_DE_LINHA);
    }

    private static void menuIncial() {
        try {
            System.out.println(QUEBRA_DE_LINHA);
            System.out.println("| Seja Bem-vinda(o), você está no app da Natureza Conectada!\n| ");
            System.out.println("| 1 - Cadastrar");
            System.out.println("| 2 - Logar");
            System.out.println("| 3 - Desconectar");

            opcaoMenuIncial = Teclado.nextInt("| Escolha uma opcão menu:");

            switch (opcaoMenuIncial) {
                case 1:
                    menuCadastrar();
                    break;
                case 2:
                    menuLogar();
                    break;
                case 3:
                    System.out.println("| Muito obrigado por usar o programa S2!");
                    System.exit(0);
                    break;
                default:
                    System.out.println(OPCAO_INVALIDA);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void menuLogar() {
        try {
            System.out.println(QUEBRA_DE_LINHA);
            System.out.println("| Você está no Login de Usuário, estamos\n| muito feliz de ter você de novo aqui! S2");
            String email = Teclado.nextString("| Digite seu email:");
            String senha = Teclado.nextString("| Digite sua senha:");

            usuarioCadastrado = serviceUsuario.logar(email, senha);

            switch (usuarioCadastrado.getTipoUsuario()) {
                case CLIENTE -> {
                    menuCliente();
                }
                case ESPECIALISTA -> {
                    menuEspecialista();
                }
                case ADMIN -> {
                    menuAdmin();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    private static void menuCadastrar() {
        try {
            System.out.println(QUEBRA_DE_LINHA);
            System.out.println("| Você está no Cadastro de Usuário, estamos muito feliz de ter você aqui! S2");
            System.out.println("| Escolha qual tipo de Usuário você é: ");
            System.out.println("| 1 - Cliente");
            System.out.println("| 2 - Especialista");
            int tipoUsuario = Teclado.nextInt("| Digite seu tipo de usuário:");

            String nome = null;
            String email = null;
            String senha = null;
            String documento = null;

            switch (tipoUsuario) {
                case 1:
                    nome = Teclado.nextString("| Digite seu nome:");
                    email = Teclado.nextString("| Digite seu email:");
                    senha = Teclado.nextString("| Digite sua senha:");
                    documento = Teclado.nextString("| Digite seu CPF ou CNPJ:");
                    Cliente novoCliente = new Cliente();

                    novoCliente.setNome(nome);
                    novoCliente.setEmail(email);
                    novoCliente.setSenha(senha);
                    novoCliente.setCpf(documento);
                    novoCliente.setTipoUsuario(TipoUsuario.CLIENTE);

                    serviceCliente.adicionar(novoCliente);
                    break;
                case 2:
                    nome = Teclado.nextString("| Digite seu nome:");
                    email = Teclado.nextString("| Digite seu email:");
                    senha = Teclado.nextString("| Digite sua senha:");
                    documento = Teclado.nextString("| Digite seu CPF ou CNPJ:");
                    String especialidade = Teclado.nextString("| Digite sua especialidade:");
                    boolean isValido = false;
                    String regiaoResponsavel = null;
                    while (!isValido) {
                        regiaoResponsavel = Teclado.nextString("| Digite a sigla da sua região (exemplo: RS):");
                        for (Estados estados : Estados.values()) {
                            if (estados.toString().equals(regiaoResponsavel)) {
                                isValido = true;
                                break;
                            }
                        }
                        if (!isValido) {
                            System.err.println("| Região inválida ");
                        }
                    }

                    System.out.println("| Agora vamos criar seu Contato!");
                    String decricao = Teclado.nextString("| Digite a descrição dele:");
                    String numero = Teclado.nextString("| Digite seu telefone:");

                    int tipoContato = 0;
                    tipoContato = getTipoContato(tipoContato);

                    Contato contato = new Contato(decricao, numero, tipoContato);

                    serviceContato.adicionar(contato);

                    Especialista novoEspecialista = new Especialista();

                    novoEspecialista.setNome(nome);
                    novoEspecialista.setEmail(email);
                    novoEspecialista.setSenha(senha);
                    novoEspecialista.setDocumento(documento);
                    novoEspecialista.setEspecializacao(especialidade);
                    novoEspecialista.setRegiaoResponsavel(Estados.valueOf(regiaoResponsavel));
                    novoEspecialista.setContato(contato);
                    novoEspecialista.setTipoUsuario(TipoUsuario.ESPECIALISTA);

                    serviceEspecialista.adicionar(novoEspecialista);
                    break;
                default:
                    System.out.println(OPCAO_INVALIDA);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void menuCliente() {
        int opcao = 0;
        while (opcao != 6) {
            try {
                System.out.println(QUEBRA_DE_LINHA);
                System.out.println("| Bem-vindo " + usuarioCadastrado.getNome());
                System.out.println("| 1 - Mudas");
                System.out.println("| 2 - Relatório");
                System.out.println("| 3 - Contatos");
                System.out.println("| 4 - Endereços");
                System.out.println("| 5 - Entregas");
                System.out.println("| 6 - Sair");
                opcao = Teclado.nextInt("| Digite sua opção:");

                switch (opcao) {
                    case 1:
                        menuClienteMudas();
                        break;
                    case 2:
                        menuClienteRelatorio();
                        break;
                    case 3:
                        menuClienteContatos();
                        break;
                    case 4:
                        menuClienteEnderecos();
                        break;
                    case 5:
                        menuClienteEntregas();
                        break;
                    case 6:
                        menuIncial();
                        System.out.println("| Saindo da conta...");
                        break;
                    default:
                        System.out.println(OPCAO_INVALIDA);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void menuClienteEntregas() {
        System.out.println(QUEBRA_DE_LINHA);
        System.out.println("| Minhas Entregas");

        // TODO: Adicionar lista de entregas
    }

    private static void menuMinhasMudas() {
        System.out.println(QUEBRA_DE_LINHA);
        System.out.println("| Suas mudas");
        List<Muda> mudas = serviceMudas.listarTodos();

        for (Muda muda : mudas) {
            System.out.println(muda);
        }
    }

    private static void menuSolicitarMuda() {

        System.out.println(QUEBRA_DE_LINHA);
        System.out.println("| Lista de mudas");

        List<Muda> mudas = serviceMudas.listarTodos();
        if(mudas.isEmpty()) {
            System.out.println("Não há mudas disponíveis");
            menuClienteMudas();
        }else {
            mudas.forEach(System.out::println);
            int id = Teclado.nextInt("| Digite o ID da muda escolhida");
            try {
                Muda mudaEscolhida = serviceMudas.procurarPorID(id);
                Cliente clienteLogado = serviceCliente.procurarPorID(usuarioCadastrado.getId());
                System.out.println("| Escolha um endereço de entrega");
                clienteLogado.imprimirEnderecos();

                int idEnderecoEscolhido = Teclado.nextInt("| Digite o ID");
                Endereco enderecoEscolhido = serviceEndereco.procurarPorID(idEnderecoEscolhido);
                while(!clienteLogado.getEnderecos().contains(enderecoEscolhido)){
                    System.err.println("Erro nenhum endereço encontrado, escolha novamente");
                    System.out.println("| Escolha um endereço de entrega");
                    clienteLogado.imprimirEnderecos();

                    idEnderecoEscolhido = Teclado.nextInt("| Digite o ID");
                    enderecoEscolhido = serviceEndereco.procurarPorID(idEnderecoEscolhido);
                }
                Entrega novaEntrega = new Entrega(List.of(mudaEscolhida), StatusEntrega.RECEBIDO, clienteLogado);
                novaEntrega.setEnderecoDeEntrega(enderecoEscolhido);

                serviceEntrega.adicionar(novaEntrega);
                System.out.println(novaEntrega);
                clienteLogado.adicionarEntregas(novaEntrega);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

    }

    private static void menuClienteEnderecos() {
        int opcao = 0;

        while (opcao != 3) {
            try {
                System.out.println(QUEBRA_DE_LINHA);
                System.out.println("| Menu de Endereços");
                System.out.println("| 1 - Cadastrar endereços");
                System.out.println("| 2 - Meus endereços");
                System.out.println("| 3 - Voltar");
                opcao = Teclado.nextInt("| Digite sua opção:");

                switch (opcao) {
                    case 1:
                        menuCadastrarEnderecos();
                        break;
                    case 2:
                        menuMeusEnderecos();
                        break;
                    case 3:
                        menuCliente();
                    default:
                        System.out.println(OPCAO_INVALIDA);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void menuMeusEnderecos() {

        System.out.println(QUEBRA_DE_LINHA);
        System.out.println("| Seus endereços");

        Cliente cliente = serviceCliente.procurarPorID(usuarioCadastrado.getId());

        List<Endereco> enderecos = cliente.getEnderecos();

        for (Endereco endereco : enderecos)
            System.out.println(endereco);
    }

    private static void menuCadastrarEnderecos() {
        String cep = Teclado.nextString("Digite seu CEP: ");
        String logradouro = Teclado.nextString("Digite o logradouro: ");
        String numero = Teclado.nextString("Digite o numero: ");
        String complemento = Teclado.nextString("Digite o complemento: ");
        String cidade = Teclado.nextString("Digite sua cidade: ");

        boolean isValido = false;
        String estado = null;
        while (!isValido) {
            estado = Teclado.nextString("| Digite a sigla do seu estado (exemplo: RS):");
            for (Estados estados : Estados.values()){
                if (estados.toString().equals(estado)){
                    isValido = true;
                    break;
                }
            }
            if (!isValido) {
                System.err.println("| Região inválida ");
            }
        }

        int tipo = 0;
        while (tipo > 2 || tipo < 1) {
            tipo = Teclado.nextInt("Escolha o tipo do seu contato:\n1. Residencial\n2. Comercial: ");
            if (tipo >= 3)
                System.out.println("Número inválido.");
        }

        System.out.println("Endereco cadastrado com sucesso!");
        Endereco endereco = new Endereco(cep, logradouro, numero, complemento, cidade, Estados.valueOf(estado), tipo);

        Cliente cliente = serviceCliente.procurarPorID(usuarioCadastrado.getId());
        cliente.adicionarEndereco(endereco);
        serviceEndereco.adicionar(endereco);
    }

    private static void menuClienteContatos() {
        int opcao = 0;

        while (opcao != 3) {
            try {
                System.out.println(QUEBRA_DE_LINHA);
                System.out.println("| Menu de Contatos");
                System.out.println("| 1 - Cadastrar contato");
                System.out.println("| 2 - Meus contatos");
                System.out.println("| 3 - Voltar");
                opcao = Teclado.nextInt("| Digite sua opção:");

                switch (opcao) {
                    case 1:
                        menuCadastrarContato();
                        break;
                    case 2:
                        menuMeusContato();
                        break;
                    case 3:
                        menuCliente();
                    default:
                        System.out.println(OPCAO_INVALIDA);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void menuMeusContato() {

        System.out.println(QUEBRA_DE_LINHA);
        System.out.println("| Seus contatos");

        Cliente cliente = serviceCliente.procurarPorID(usuarioCadastrado.getId());

        List<Contato> contatos = cliente.getContatos();

        for (Contato contato : contatos)
            System.out.println(contato);

    }

    private static void menuCadastrarContato() {
        String descricao = Teclado.nextString("Digite a descrição: ");
        int tipo = 0;
        while (tipo > 2 || tipo < 1) {
            tipo = Teclado.nextInt("Escolha o tipo do seu contato:\n1. Residencial\n2. Comercial: ");
            if (tipo >= 3)
                System.out.println("Número inválido.");
        }

        String numero = Teclado.nextString("Digite seu número: ");
        System.out.println("Contato cadastrado com sucesso!");
        Contato novoContato = new Contato(descricao, numero, tipo);

        Cliente cliente = serviceCliente.procurarPorID(usuarioCadastrado.getId());
        cliente.adicionarContato(novoContato);
        serviceContato.adicionar(novoContato);


    }

    private static void menuClienteRelatorio() {
        int opcao = 0;
        Cliente clienteLogado = serviceCliente.procurarPorID(usuarioCadastrado.getId());



        while (opcao != 4) {
            try {
                System.out.println(QUEBRA_DE_LINHA);
                System.out.println("| Menu de Relatório");
                System.out.println("| 1 - Gerar relatório");
                System.out.println("| 2 - Consultar relatórios");
                System.out.println("| 3 - Editar relatório");
                System.out.println("| 4 - Voltar");
                opcao = Teclado.nextInt("| Digite sua opção:");

                switch (opcao) {

                    case 1:

                        serviceCliente.imprimirMudasCliente(clienteLogado);
                        int idDaMudaEscolhida =  Teclado.nextInt("| Selecione o ID da muda");
                        Muda mudaSelecionado = serviceMudas.procurarPorID(idDaMudaEscolhida);
                        String estadoDaMuda = Teclado.nextString("| Diga para nós, como está a muda?");
                        String sugestao = Teclado.nextString("| digite alguma sugestão para nós");
                        Relatorio relatorio = new Relatorio(clienteLogado,mudaSelecionado,estadoDaMuda,sugestao);
                        System.out.println("| prévia do relatório ");
                        serviceRelatorio.imprimirRelatorio(relatorio);
                        serviceRelatorio.adicionar(relatorio);
                        menuCliente();
                        break;
                    case 2:
                        System.out.println("| Segue relatórios abaixo");
                        serviceRelatorio.buscarPorCliente(clienteLogado);

                        menuCliente();
                        break;
                    case 3:
                        System.out.println("| Lista de relatórios");
                        serviceRelatorio.buscarPorCliente(clienteLogado);
                        int relatorioSelecionado = Teclado.nextInt("| Seleciona o ID do relatorio");
                        System.out.println("| Editando o relatorio");
                        serviceCliente.imprimirMudasCliente(clienteLogado);
                        int idMudaEscolhida =  Teclado.nextInt("| Selecione o ID da muda");
                        Muda mudaRelatorioEditado = serviceMudas.procurarPorID(idMudaEscolhida);
                        String estadoDaMudaEditado = Teclado.nextString("| Diga para nós, como está a muda?");
                        String sugestaoEditada = Teclado.nextString("| digite alguma sugestão para nós");
                        Relatorio relatorioEditado = new Relatorio(clienteLogado,mudaRelatorioEditado,estadoDaMudaEditado,sugestaoEditada);

                        System.out.println("| prévia do relatório ");
                        System.out.println(relatorioEditado.toString());

                        serviceRelatorio.editar(relatorioSelecionado,relatorioEditado);

                        menuCliente();
                        break;
                    case 4:
                        menuCliente();
                        break;
                    default:
                        System.out.println(OPCAO_INVALIDA);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void menuClienteMudas() {
        int opcao = 0;

        while (opcao != 3) {
            try {
                System.out.println(QUEBRA_DE_LINHA);
                System.out.println("| Menu de Mudas");
                System.out.println("| 1 - Solicitar muda");
                System.out.println("| 2 - Minhas mudas");
                System.out.println("| 3 - Voltar");
                opcao = Teclado.nextInt("| Digite sua opção:");

                switch (opcao) {
                    case 1:
                        menuSolicitarMuda();
                        break;
                    case 2:
                        menuMinhasMudas();
                        break;
                    case 3:
                        menuCliente();
                    default:
                        System.out.println(OPCAO_INVALIDA);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void menuEspecialista() {
        int opcao = 0;
        while (opcao != 6) {
            try {
                System.out.println(QUEBRA_DE_LINHA);
                System.out.println("| Bem-vindo " + usuarioCadastrado.getNome());
                System.out.println("| 1 - Relatório");
                System.out.println("| 2 - Editar Contato");
                System.out.println("| 3 - Sair");
                opcao = Teclado.nextInt("| Digite sua opção:");

                switch (opcao) {
                    case 1:
                        menuEspecialistaRelatorio();
                        break;
                    case 2:
                        menuEditarContato();
                        break;
                    case 3:
                        menuIncial();
                        System.out.println("| Saindo da conta...");
                        break;
                    default:
                        System.out.println(OPCAO_INVALIDA);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
    private static void menuAdmin() {
        int opcao = 0;
        while (opcao != 8) {
            try {
                System.out.println(QUEBRA_DE_LINHA);
                System.out.println("| Bem-vindo " + usuarioCadastrado.getNome());
                System.out.println("| 1 - Listar todos os clientes");
                System.out.println("| 2 - Listar todos os especialistas");
                System.out.println("| 3 - Listar todos os usuários");
                System.out.println("| 4 - Listar todos os relatórios");
                System.out.println("| 5 - Entregas");
                System.out.println("| 6 - Mudas");
                System.out.println("| 7 - Editar minha conta");
                System.out.println("| 8 - Sair");
                opcao = Teclado.nextInt("| Digite sua opção:");

                switch (opcao) {
                    case 1:
                        System.out.println("| Listando todos os clientes: ");
                        System.out.println(serviceCliente.listarTodos());
                        break;
                    case 2:
                        System.out.println("| Listando todos os especialistas: ");
                        System.out.println(serviceEspecialista.listarTodos());
                        break;
                    case 3:
                        System.out.println("| Listando todos os usuários: ");
                        System.out.println("\n| Administradores: ");
                        System.out.println(serviceAdmin.listarTodos());
                        System.out.println("\n| Clientes:");
                        System.out.println(serviceCliente.listarTodos());
                        System.out.println("\n| Especialistas:");
                        System.out.println(serviceEspecialista.listarTodos());
                        break;
                    case 4:
                        System.out.println("| Listando todos os relatórios: ");
                        System.out.println(serviceRelatorio.listarTodos());
                        break;
                    case 5:
                        menuAdminEntregas();
                        break;
                    case 6:
                        menuAdminMudas();
                        break;
                    case 7:
                        String nome = Teclado.nextString("| Digite seu novo nome: ");
                        String email = Teclado.nextString("| Digite seu novo email: ");
                        String senha = Teclado.nextString("| Digite sua nova senha: ");


                        usuarioCadastrado.setNome(nome);
                        usuarioCadastrado.setEmail(email);
                        usuarioCadastrado.setSenha(senha);

                        System.out.println("| Conta editada com sucesso!");
                        break;
                    case 8:

                        System.out.println("| Saindo da conta...");
                        break;
                    default:
                        System.out.println(OPCAO_INVALIDA);
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void menuAdminEntregas() {
        int opcao = 0;
        while (opcao != 3) {
            try {
                System.out.println(QUEBRA_DE_LINHA);
                System.out.println("| Menu entregas");
                System.out.println("| 1 - Listar todas as entregas");
                System.out.println("| 2 - Editar Status entrega");
                System.out.println("| 3 - Voltar");
                opcao = Teclado.nextInt("| Digite sua opção:");

                switch (opcao) {
                    case 1:
                        System.out.println("| Listando todas as entregas: ");
                        System.out.println(serviceEntrega.listarTodos());
                        break;
                    case 2:
                        int id = Teclado.nextInt("| Digite o ID da entrega escolhida");
                        Entrega entregaEscolhida = serviceEntrega.procurarPorID(id);
                        System.out.println("| Digite o status da entrega escolhida");
                        System.out.println("| 0 - Recebido");
                        System.out.println("| 1 - Enviado");
                        System.out.println("| 2 - Entregue");
                        int status = Teclado.nextInt("| Digite o novo status da entrega escolhida");
                        switch (status) {
                            case 1:
                                entregaEscolhida.setStatus(StatusEntrega.RECEBIDO);
                                serviceEntrega.editar(id, entregaEscolhida);
                                break;

                            case 2:
                                entregaEscolhida.setStatus(StatusEntrega.ENVIADO);
                                serviceEntrega.editar(id, entregaEscolhida);
                                break;

                            case 3:
                                entregaEscolhida.setStatus(StatusEntrega.ENTREGUE);
                                serviceEntrega.editar(id, entregaEscolhida);
                                break;
                            default:
                                System.out.println(OPCAO_INVALIDA);
                        }

                        break;
                    case 3:

                        System.out.println("| Voltando...");
                        break;
                    default:
                        System.out.println(OPCAO_INVALIDA);
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

    }

    private static void menuAdminMudas() {

    }

    private static void menuEditarContato() {
        try {
            Especialista especialista = serviceEspecialista.procurarPorID(usuarioCadastrado.getId());

            System.out.println(QUEBRA_DE_LINHA);
            System.out.println("| Você está editando este contato:");
            System.out.println(especialista.getContato());

            String decricao = Teclado.nextString("| Digite a descrição dele:");
            String numero = Teclado.nextString("| Digite seu telefone:");

            int tipoContato = 0;
            tipoContato = getTipoContato(tipoContato);

            Contato contatoEditado = new Contato();

            contatoEditado.setDescricao(decricao);
            contatoEditado.setNumero(numero);
            contatoEditado.setTipo(tipoContato);

            serviceContato.editar(especialista.getContato().getId(), contatoEditado);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static int getTipoContato(int tipoContato) {
        boolean condicao = true;
        while (condicao) {
            try {
                System.out.println("| Digite seu tipo de Contato:");
                System.out.println("| 1 - Residencial");
                System.out.println("| 2 - Comercial");
                tipoContato = Teclado.nextInt("| Digite o seu tipo:");

                condicao = tipoContato < 1 || tipoContato > 2;

                if (condicao) {
                    System.err.println("Digite um tipo válido!");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return tipoContato;
    }

    private static void menuEspecialistaRelatorio() {
        int opcao = 0;

        while (opcao != 3) {
            try {
                System.out.println(QUEBRA_DE_LINHA);
                System.out.println("| 1 - Relatórios abertos(Avaliar Relatório)");
                System.out.println("| 2 - Meus Relatórios");
                System.out.println("| 3 - Voltar");

                opcao = Teclado.nextInt("| Digite a opção:");

                switch (opcao) {
                    case 1:
                        menuRelatoriosAbertos();
                        break;
                    case 2:
                        meusRelatorios();
                        break;
                    case 3:
                        menuEspecialista();
                        break;
                    default:
                        System.err.println(OPCAO_INVALIDA);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void meusRelatorios() {
        System.out.println(QUEBRA_DE_LINHA);
        System.out.println("| Meus Relatórios");
        List<Relatorio> relatorios = serviceEspecialista.procurarRelatorioPorEmail(usuarioCadastrado.getEmail());

        if (relatorios.isEmpty()) {
            System.out.println("Você não tem Relatórios disponíveis!");
        } else {
            for (Relatorio relatorio : relatorios) {
                System.out.println(relatorio);
            }
        }
    }

    private static void menuRelatoriosAbertos() {
        System.out.println(QUEBRA_DE_LINHA);
        System.out.println("| Relatório sem avaliação");
        List<Relatorio> relatorios = serviceRelatorio.procurarRelatoriosSemAvaliador();

        if (relatorios.isEmpty()) {
            System.out.println("Sem Relatórios disponíveis!");
        } else {
            System.out.println("| Selecione o relatório pelo seu ID:");

            for (Relatorio relatorio : relatorios) {
                System.out.println(relatorio);
            }

            try {
                Especialista especialista = serviceEspecialista.procurarPorID(usuarioCadastrado.getId());
                Relatorio relatorioSelecionado = null;

                int idRelatorio = Teclado.nextInt("| Digite o ID do Relátorio que deseja avaliar:");

                for (Relatorio relatorio: relatorios) {
                    if (relatorio.getId() == idRelatorio) {
                        relatorioSelecionado = relatorio;
                    }
                }

                if (Objects.nonNull(relatorioSelecionado)) {
                    System.out.println(QUEBRA_DE_LINHA);
                    relatorioSelecionado.setAvaliador(especialista);
                    System.out.println("| Muda para avialiação");
                    System.out.println(relatorioSelecionado.getMuda());

                    String sugestao = Teclado.nextString("| Digite sua sugestão:");
                    double notaFinal = Teclado.nextDouble("| Digite sua nota para a Muda:");

                    relatorioSelecionado.setSugestoes(sugestao);
                    relatorioSelecionado.setAvaliacaoEspecialista(notaFinal);

                    serviceRelatorio.editar(relatorioSelecionado.getId(), relatorioSelecionado);
                } else {
                    System.out.println("| Nenhum relatório foi selecionado!");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
