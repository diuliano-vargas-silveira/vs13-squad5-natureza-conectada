package utils;

import exceptions.ErroDigitacaoException;

import java.util.Scanner;

public class Teclado {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String ESPACO = " ";

    public static String nextString(String mensagem) {
        while (true) {
            try {
                System.out.println(mensagem + ESPACO);
                String input = scanner.nextLine();

                boolean isVazio = isInputVazio(input);

                if (isVazio) {
                    throw new ErroDigitacaoException();
                }

                return input;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Integer nextInt(String mensagem) throws NumberFormatException {
        while (true) {
            try {
                String input = nextString(mensagem);

                return Integer.parseInt(input);
            } catch (Exception e) {
                System.err.println("Número inválido, por favor digite novamente!");
            }
        }
    }

    public static Double nextDouble(String mensagem) throws NumberFormatException {
        while (true) {
            try {
                String input = nextString(mensagem);

                return Double.parseDouble(input);
            } catch (Exception e) {
                System.err.println("Número inválido, por favor digite novamente!");
            }
        }
    }


    private static boolean isInputVazio(String input) {
        return input.isEmpty() || input.isBlank();
    }
}
