import models.Contato;
import models.Endereco;
import models.Muda;

public class Main {

    public static void main(String[] args) {

        try {
//            Endereco end = new Endereco(123, "777-6666", "Rua Abc", "123", "CASA", "PORTO ALEGRE", "RS", 1);
//            Contato cont = new Contato(123, "models.Contato 1", "9873424234", 3);
//            System.out.println(end);
//            System.out.println(cont);



        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}