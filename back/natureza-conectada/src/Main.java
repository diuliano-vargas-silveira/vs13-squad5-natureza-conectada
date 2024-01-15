// import utils.Menu;

import utils.MockContainer;

public class Main {

    public static void main(String[] args) {

        ServiceMudas  serviceMudas = new ServiceMudas();


        try {
            MockContainer mockContainer = new MockContainer();
            mockContainer.iniciaObjetos();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}