// import utils.Menu;


import enums.Estados;
import enums.TipoUsuario;
import models.Cliente;
import models.Especialista;
import models.Usuario;
import services.ServiceEspecialista;
import services.ServiceUsuario;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Menu.rodarAplicacao();

        ServiceEspecialista serviceEspecialista = new ServiceEspecialista();

        try {
            Especialista especialista = new Especialista();

            especialista.setNome("Diuliano Vargas");
            especialista.setEmail("diulianovargas@gmail.com");
            especialista.setSenha("senhadificil");
            especialista.setDocumento("00000000000");
            especialista.setEspecializacao("informática");
            especialista.setTipoUsuario(TipoUsuario.ESPECIALISTA);
            especialista.setRegiaoResponsavel(Estados.RS);

//            serviceEspecialista.adicionar(especialista);

            especialista.setEspecializacao("Grêmio");

            serviceEspecialista.editar(2, especialista);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}