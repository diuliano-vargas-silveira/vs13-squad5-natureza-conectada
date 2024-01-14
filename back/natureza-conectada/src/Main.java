// import utils.Menu;


import models.Usuario;
import services.ServiceUsuario;

public class Main {

    public static void main(String[] args) {

        // Menu.rodarAplicacao();

        ServiceUsuario serviceUsuario = new ServiceUsuario();

        try {
            Usuario usuario = serviceUsuario.logar("diuliano.vargas@example.com", "senhadificil");
            System.out.println(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}