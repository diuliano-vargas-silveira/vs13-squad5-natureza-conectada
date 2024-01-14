// import utils.Menu;

import enums.Estados;
import enums.Tipo;
import models.Endereco;
import models.Especialista;
import services.ServiceEndereco;
import services.ServiceEspecialista;

public class Main {

    public static void main(String[] args) {

        // Menu.rodarAplicacao();

        ServiceEspecialista serviceEspecialista = new ServiceEspecialista();
        ServiceEndereco serviceEndereco = new ServiceEndereco();

        try {
            Especialista especialista = serviceEspecialista.procurar(2);

            System.out.println(especialista);

            Endereco endereco = new Endereco();

            endereco.setTipo(Tipo.COMERCIAL);
            endereco.setLogradouro("Avenida do Nazário");
            endereco.setComplemento("");
            endereco.setEstado(Estados.RS);
            endereco.setCidade("Canoas");
            endereco.setNumero("1992");
            endereco.setCep("92035000");
            endereco.setUsuario(especialista);

            serviceEndereco.adicionar(endereco);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}