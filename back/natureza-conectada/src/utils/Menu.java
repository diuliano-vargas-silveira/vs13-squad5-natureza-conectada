package utils;

import database.BancoDeDados;
import enums.TipoUsuario;
import models.Cliente;
import models.Especialista;
import models.Muda;
import models.Usuario;
import services.ServiceCliente;
import services.ServiceEspecialista;
import services.ServiceMudas;
import services.ServiceUsuario;

import java.util.List;

public class Menu {
    private static int opcaoMenuIncial = 0;
    private static Usuario usuarioCadastrado;
    private static final String QUEBRA_DE_LINHA = "| -------------------------------------------------------------------------- |";
    private static final String OPCAO_INVALIDA = "| Opção inválida! Digite novamente.";
    private static final int SAIR_PROGRAMA = 3;
    private static final ServiceCliente serviceCliente = new ServiceCliente();
    private static final ServiceEspecialista serviceEspecialista = new ServiceEspecialista();
    private static final ServiceMudas serviceMudas = new ServiceMudas();
    private static final ServiceUsuario serviceUsuario = new ServiceUsuario();

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
                    String regiaoResponsavel = Teclado.nextString("| Digite sua região:");

                    Especialista novoEspecialista = new Especialista();

                    novoEspecialista.setNome(nome);
                    novoEspecialista.setEmail(email);
                    novoEspecialista.setSenha(senha);
                    novoEspecialista.setDocumento(documento);
                    novoEspecialista.setEspecializacao(especialidade);
                    novoEspecialista.setRegiaoResponsavel(regiaoResponsavel);

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
    }

    private static void menuCadastrarEnderecos() {
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
    }

    private static void menuCadastrarContato() {
    }

    private static void menuClienteRelatorio() {
        int opcao = 0;

        // TODO: AJUSTAR O RELATÓRIO

        while (opcao != 1) {
            try {
                System.out.println(QUEBRA_DE_LINHA);
                System.out.println("| Menu de Relatório");
                System.out.println("| 1 - Voltar");
                opcao = Teclado.nextInt("| Digite sua opção:");

                switch (opcao) {
                    case 1:
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
