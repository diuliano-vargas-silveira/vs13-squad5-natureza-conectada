// import utils.Menu;


import enums.Estados;
import enums.TipoUsuario;
import models.Cliente;
import models.Especialista;
import models.Usuario;
import services.ServiceCliente;
import services.ServiceEspecialista;
import services.ServiceUsuario;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Menu.rodarAplicacao();

        ServiceCliente serviceCliente = new ServiceCliente();


        try {
//            Cliente cliente1 = serviceCliente.procurarPorID(3);
//            Cliente cliente = new Cliente();
//            cliente.setId(cliente1.getId());
//
//            cliente.setNome("Willian");
//            cliente.setEmail("novoemail8@gmail.com");
//            cliente.setSenha("senhadifemailicil");
//            cliente.setCpf("12345678910");

            //serviceCliente.adicionar(cliente);serviceCliente

            serviceCliente.deletar(3);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}