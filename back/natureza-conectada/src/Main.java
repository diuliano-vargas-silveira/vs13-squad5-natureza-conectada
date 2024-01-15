// import utils.Menu;

import models.Muda;
import services.ServiceMudas;

public class Main {

    public static void main(String[] args) {

        ServiceMudas  serviceMudas = new ServiceMudas();


        try {
            Muda novaMuda = new Muda(1,32,"Florzinha Feia","FLORIUS Feias",1,"umido","Uma flor feia para jogar fora");

            serviceMudas.editarmuda(5,novaMuda);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}