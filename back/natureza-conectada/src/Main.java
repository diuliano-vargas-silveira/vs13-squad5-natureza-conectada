// import utils.Menu;


import enums.TipoUsuario;
import models.Cliente;
import models.Usuario;
import services.ServiceUsuario;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Menu.rodarAplicacao();

        ServiceUsuario serviceUsuario = new ServiceUsuario();

        try {
            Cliente cliente = new Cliente();

            cliente.setEmail("diuliano.vargas@example.com");
            cliente.setNome("Diuliano Vargas");
            cliente.setTipoUsuario(TipoUsuario.CLIENTE);
            cliente.setSenha("senhadificil");

            Usuario usuario = serviceUsuario.adicionarUsuario(cliente);
            System.out.println(usuario);

            System.out.println(serviceUsuario.logar(cliente.getEmail(), cliente.getSenha()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}