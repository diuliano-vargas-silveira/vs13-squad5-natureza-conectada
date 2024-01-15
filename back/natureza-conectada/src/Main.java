import enums.Estados;
import exceptions.BancoDeDadosException;
import models.*;
import services.*;
import utils.Teclado;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static Usuario usuarioCadastrado = null;
    public static Cliente cliente = null;

    public static void main(String[] args) {

        String QUEBRA_DE_LINHA = "| -------------------------------------------------------------------------- |";

        int opcaoMenuIncial = 0;

        while (true) {
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
                        System.out.println("| Muito obrigado por usar o programa!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }


    public static void menuCadastrar() throws Exception {
        ServiceCliente serviceCliente = new ServiceCliente();
        ServiceEspecialista serviceEspecialista = new ServiceEspecialista();
        ServiceAdmin serviceAdmin = new ServiceAdmin();

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

                Especialista especialista = new Especialista();

                especialista.setNome(nome);
                especialista.setEmail(email);
                especialista.setSenha(senha);
                especialista.setDocumento(documento);
                especialista.setEspecializacao(especialidade);
                especialista.setRegiaoResponsavel(Estados.valueOf(regiaoResponsavel));

                serviceEspecialista.adicionar(especialista);
                break;
            case 3:
                nome = Teclado.nextString("| Digite seu nome:");
                email = Teclado.nextString("| Digite seu email:");
                senha = Teclado.nextString("| Digite sua senha:");

                Admin admin = new Admin();
                admin.setNome(nome);
                admin.setEmail(email);
                admin.setSenha(senha);

                serviceAdmin.adicionar(admin);
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    public static void menuLogar() throws BancoDeDadosException {
        ServiceUsuario serviceUsuario = new ServiceUsuario();

        System.out.println("| Você está no Login de Usuário, estamos\n| muito feliz de ter você de novo aqui! S2");
        String email = Teclado.nextString("| Digite seu email:");
        String senha = Teclado.nextString("| Digite sua senha:");

        usuarioCadastrado = serviceUsuario.logar(email, senha);

        switch (usuarioCadastrado.getTipoUsuario()) {
            case CLIENTE -> {
                menuCliente();
            }
//            case ESPECIALISTA -> {
//                menuEspecialista();
//            }
            case ADMIN -> {
                menuAdmin();
            }
        }
    }

    private static void menuAdmin() {
        ServiceAdmin serviceAdmin = new ServiceAdmin();
        ServiceContato serviceContato = new ServiceContato();
        ServiceEntrega serviceEntrega = new ServiceEntrega();
        ServiceUsuario serviceUsuario = new ServiceUsuario();
        ServiceCliente serviceCliente = new ServiceCliente();

        int opcao1 = 0;

        while (opcao1 != 3) {
            try {

                System.out.println("| Menu Admin");
                System.out.println("| Selecione uma opção");
                System.out.println("| 1 - Listar clientes");
                System.out.println("| 2 - Listar minhas entregas");
                System.out.println("| 3 - Sair da conta");

                opcao1 = Teclado.nextInt("Selecione sua opção");

                switch (opcao1) {
                    case 1:
                        List<Cliente> clientes = serviceCliente.listarTodos();

                        System.out.println("| Lista de clientes:\n");

                        for (Cliente cliente : clientes)
                            System.out.println(cliente);

                        break;
                    case 2:
                        System.out.println("| Lista de Entregas:");

                        serviceEntrega.listarTodos();
                        break;

                    case 3:
                        System.out.println("Saindo da conta.");
                        break;

                    default:
                        System.out.println("Opção inválida!");

                }
            } catch (BancoDeDadosException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void menuCliente() {
        ServiceContato serviceContato = new ServiceContato();
        ServiceCliente serviceCliente  = new ServiceCliente();
        ServiceEntrega serviceEntrega = new ServiceEntrega();
        int opcao = 0;

        while (opcao != 3) {
            try {

                System.out.println("| Menu cliente");
                System.out.println("| Selecione a opção");
                System.out.println("| 1 - Listar meus contatos");
                System.out.println("| 2 - Listar minhas entregas");
                System.out.println("| 3 - Sair da conta");

                opcao = Teclado.nextInt("Selecione sua opção");

                switch (opcao) {
                    case 1:
                        List<Contato> contatos = serviceContato.listaDeContatoPorCliente(usuarioCadastrado.getId());

                        for (Contato contato : contatos) {
                            System.out.println(contato);
                        }
                        break;
                    case 2:
                        serviceEntrega.listarTodos();

                        break;

                    case 3:
                        System.out.println("Saindo da conta.");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}