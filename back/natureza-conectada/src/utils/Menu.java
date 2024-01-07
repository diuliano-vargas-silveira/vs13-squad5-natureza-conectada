package utils;

import enums.Estados;
import enums.StatusEntrega;
import models.*;
import services.*;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private static int opcaoMenuIncial = 0;
    private static Usuario usuarioCadastrado;
    private static final String QUEBRA_DE_LINHA = "| -------------------------------------------------------------------------- |";
    private static final String OPCAO_INVALIDA = "| Opção inválida! Digite novamente.";
    private static final int SAIR_PROGRAMA = 3;
    private static final ServiceCliente serviceCliente = new ServiceCliente();
    private static final ServiceEspecialista serviceEspecialista = new ServiceEspecialista();

    private static final ServiceRelatorio serviceRelatorio = new ServiceRelatorio();
    private static final ServiceMudas serviceMudas = new ServiceMudas();
    private static final ServiceUsuario serviceUsuario = new ServiceUsuario();
    private static final ServiceContato serviceContato = new ServiceContato();
    private static final ServiceEndereco serviceEndereco = new ServiceEndereco();

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

            menuCliente();
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

            String nome = Teclado.nextString("| Digite seu nome:");
            String email = Teclado.nextString("| Digite seu email:");
            String senha = Teclado.nextString("| Digite sua senha:");
            String documento = Teclado.nextString("| Digite seu CPF ou CNPJ:");

            switch (tipoUsuario) {
                case 1:
                    Cliente novoCliente = new Cliente();

                    novoCliente.setNome(nome);
                    novoCliente.setEmail(email);
                    novoCliente.setSenha(senha);
                    novoCliente.setCpf(documento);

                    serviceCliente.adicionar(novoCliente);
                    break;
                case 2:
                    String especialidade = Teclado.nextString("| Digite sua especialidade:");
                    boolean isValido = false;
                    String regiaoResponsavel = null;
                    while (!isValido){
                        regiaoResponsavel = Teclado.nextString("| Digite a sigla da sua região (exemplo: RS):");
                        for (Estados estados : Estados.values()){
                            if (estados.toString().equals(regiaoResponsavel)){
                                isValido = true;
                                break;
                            }
                        }
                        if (!isValido) {
                            System.err.println("| Região inválida ");
                        }
                    }



                    Especialista novoEspecialista = new Especialista();

                    novoEspecialista.setNome(nome);
                    novoEspecialista.setEmail(email);
                    novoEspecialista.setSenha(senha);
                    novoEspecialista.setDocumento(documento);
                    novoEspecialista.setEspecializacao(especialidade);
                    novoEspecialista.setRegiaoResponsavel(Estados.valueOf(regiaoResponsavel));
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
        //todo:modficar objeto entrega
        System.out.println(QUEBRA_DE_LINHA);
        System.out.println("| Lista de mudas");
        List<Muda> mudas = serviceMudas.listarTodos();

        int id = Teclado.nextInt("| Digite o ID da muda escolhida");
        Muda mudaEscolhida = serviceMudas.procurarPorID(id);

        Cliente clienteLogado = serviceCliente.procurarPorID(usuarioCadastrado.getId());

        System.out.println("| Escolha um endereço de entrega");
        clienteLogado.imprimirEnderecos();

        int idEnderecoEscolhido = Teclado.nextInt("| Digite o ID");
        Endereco enderecoEscolhido = serviceEndereco.procurarPorID(idEnderecoEscolhido);
        //Entrega novaEntrega = new Entrega(List.of(mudaEscolhida), StatusEntrega.ENTREGUE,clienteLogado);

        //serviceEntrega.adicionar(novaEntrega);
        //clienteLogado.adicionarEntregas(novaEntrega);










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
        while (!isValido){
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
        while (true) {
            if (tipo <= 2 && tipo >= 1) break;
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
        while (true) {
            if (tipo <= 2 && tipo >= 1) break;
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

}
