/*
package utils;


import enums.*;
import models.*;
import services.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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

    private static  Scanner scanner = new Scanner(System.in);
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
            System.out.println("| 3 - Administrador");
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

                    String tipoContato = null;
                    int numeracaoFiltro = 0;
                    isValido = false;
                    while(!isValido){
                        tipoContato = Teclado.nextString("| Digite o tipo de telefone(RESIDENCIAL/COMERCIAL):").toUpperCase();
                        if(tipoContato.equals("RESIDENCIAL") || tipoContato.equals("COMERCIAL")){
                            isValido = true;
                            if(tipoContato.equals("RESIDENCIAL")){
                                numeracaoFiltro = 1;
                            }
                            else{
                                numeracaoFiltro = 2;
                            }
                        }
                        else {
                            System.out.println("| Tipo de contato inválido!");
                        }
                    }

                    Contato contato = new Contato(decricao, numero, numeracaoFiltro);



                    Especialista novoEspecialista = new Especialista();
                    novoEspecialista.setDocumento(documento);
                    novoEspecialista.setEspecializacao(especialidade);
                    novoEspecialista.setRegiaoResponsavel(Estados.valueOf(regiaoResponsavel));


                    serviceEspecialista.adicionar(novoEspecialista);
                    Usuario usuarioNovo = serviceUsuario.logar(novoEspecialista.getEmail(),novoEspecialista.getSenha());

                    serviceContato.adicionarContato(contato,usuarioNovo.getId());
                    break;
                default:
                    System.out.println(OPCAO_INVALIDA);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    private void iniciarApp(){
        while(true){
            try{
                System.out.println("| Você está no menu de login/cadastro, estamos felizes de ter você aqui! S2");
                System.out.println("| Escolha quais opções deseja acessar:\n | 1 - LOGIN \n| 2 - CADASTRAR");
                int opcao = scanner.nextInt();
            }catch(Exception erro){
                System.out.println("| Opção inválida!");
            }
        }
        while(true){
            try{
                System.out.println("| Escolha uma das opções disponiveis do sistema:");
                System.out.println("| 1 - Cadastrar Muda");
                System.out.println("| 2 - Cadastrar Usuario");
                System.out.println("| 3 - Cadastrar Cliente");
                System.out.println("| 4 - Cadastrar Entrega");
                System.out.println("| 5 - Cadastrar Contato");
                System.out.println("========= LISTAGEM  ========");
                System.out.println("| 6 - Todas as mudas");
                System.out.println("| 7 - Todas os usuarios");
                System.out.println("| 8 - Todos os clientes");
                System.out.println("| 9 - Todas as entregas");
                System.out.println("| 10 - Todos os contatos");

            }catch(Exception erro){
                System.out.println("| Opção inválida!");
            }
        }
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
                        System.out.println("| 1 - Recebido");
                        System.out.println("| 2 - Enviado");
                        System.out.println("| 3 - Entregue");
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
        int opcao = 0;
        while (opcao != 4) {
            try {
                System.out.println(QUEBRA_DE_LINHA);
                System.out.println("| Menu mudas");
                System.out.println("| 1 - Adicionar muda");
                System.out.println("| 2 - Deletar muda");
                System.out.println("| 3 - Editar muda");
                System.out.println("| 4 - Listar mudas");
                System.out.println("| 5 - Voltar");
                opcao = Teclado.nextInt("| Digite sua opção:");

                switch (opcao) {
                    case 1:
                        boolean isValido = false;
                        String tipoMuda = null;
                        while (!isValido) {
                            System.out.println("| Tipos de muda: ");
                            System.out.println("| PLANTA");
                            System.out.println("| ARVORE");
                            tipoMuda = Teclado.nextString("| O tipo da sua muda:");
                            for (TipoMuda mudas : TipoMuda.values()){
                                if (mudas.toString().equals(tipoMuda)){
                                    isValido = true;
                                    break;
                                }
                            }
                            if (!isValido) {
                                System.err.println("| Tipo de muda inválido ");
                            }
                        }

                        String nome = Teclado.nextString("| Digite o nome da sua muda: ");
                        String nomeCientifico = Teclado.nextString("| Digite o nome científico da sua muda: ");
                        String porteMuda = null;
                        boolean isValidoPorte = false;
                        while (!isValidoPorte) {
                            System.out.println("| Portes de muda: ");
                            System.out.println("| PEQUENO");
                            System.out.println("| MEDIO");
                            System.out.println("| GRANDE");
                            porteMuda = Teclado.nextString("| Digite o porte da sua muda: ");
                            for (TamanhoMuda portes : TamanhoMuda.values()){
                                if (portes.toString().equals(porteMuda)){
                                    isValidoPorte = true;
                                    break;
                                }
                            }
                            if (!isValidoPorte) {
                                System.err.println("| Porte de muda inválido ");
                            }
                        }
                        String ambienteIdeal = Teclado.nextString("| Digite o ambiente ideal da sua muda: ");
                        String descricao = Teclado.nextString("| Digite a descrição da sua muda: ");

                        Muda novaMuda = new Muda();
                        novaMuda.setTipo(TipoMuda.valueOf(tipoMuda));
                        novaMuda.setNome(nome);
                        novaMuda.setNomeCientifico(nomeCientifico);
                        novaMuda.setPorte(TamanhoMuda.valueOf(porteMuda));
                        novaMuda.setAmbienteIdeal(ambienteIdeal);
                        novaMuda.setDescricao(descricao);

                        serviceMudas.adicionar(novaMuda);
                        System.out.println("| Muda adicionada com sucesso!");
                        break;
                    case 2:
                        int id = Teclado.nextInt("| Digite o ID da muda a ser deletada:");
                        Muda mudaExistente = serviceMudas.procurarPorID(id);
                        serviceMudas.deletar(mudaExistente.getId());
                        System.out.println("| Muda deletada com sucesso!");
                        break;

                    case 3:
                        System.out.println("| Listando mudas:");
                        System.out.println(serviceMudas.listarTodos());
                        break;

                    case 4:
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

     );
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
*/
