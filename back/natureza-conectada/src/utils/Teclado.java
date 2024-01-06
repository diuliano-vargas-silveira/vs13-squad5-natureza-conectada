package utils;

import exceptions.ErroDigitacaoException;

import java.util.Scanner;

public class Teclado {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String ESPACO = " ";

    public static String nextString(String mensagem) {
        System.out.print(mensagem + ESPACO);
        String input = scanner.nextLine();

        boolean isVazio = isInputVazio(input);

        if (isVazio) {
            throw new ErroDigitacaoException();
        }

        return input;
    }

    public static Integer nextInt(String mensagem) throws NumberFormatException {
        System.out.print(mensagem + ESPACO);
        String input = scanner.nextLine();

        boolean isValido = isInputVazio(input);

        if (isValido) {
            throw new ErroDigitacaoException();
        }

        Integer inputInteiro = Integer.parseInt(input);

        return inputInteiro;
    }

    public static Double nextDouble(String mensagem) throws NumberFormatException {
        System.out.print(mensagem + ESPACO);
        String input = scanner.nextLine();

        boolean isValido = isInputVazio(input);

        if (isValido) {
            throw new ErroDigitacaoException();
        }

        Double inputFlutuante = Double.parseDouble(input);

        return inputFlutuante;
    }


    private static boolean isInputVazio(String input) {
        return input.isEmpty() || input.isBlank();
    }
}
