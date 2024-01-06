package utils;

import database.BancoDeDados;
import enums.TipoUsuario;
import models.Cliente;
import models.Especialista;
import models.Usuario;
import services.ServiceCliente;
import services.ServiceEspecialista;
import services.ServiceUsuario;

public class Menu {
    private static int opcaoMenuIncial = 0;
    private static Usuario usuarioCadastrado;
    private static final String QUEBRA_DE_LINHA = "| -------------------------------------------------------------------------- |";
    private static final String OPCAO_INVALIDA = "| Opção inválida! Digite novamente.";
    private static final int SAIR_PROGRAMA = 3;
    private static final ServiceCliente serviceCliente = new ServiceCliente();
    private static final ServiceEspecialista serviceEspecialista = new ServiceEspecialista();
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
                    System.out.println("Vlw flww");
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
            System.out.println("| Escolha qual tipo de Usuário você é: ");
            System.out.println("| 1 - Cliente");
            System.out.println("| 2 - Especialista");
            String email = Teclado.nextString("| Digite seu email:");
            String senha = Teclado.nextString("| Digite sua senha:");

            usuarioCadastrado = serviceUsuario.logar(email, senha);

        } catch(Exception e) {
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

}
