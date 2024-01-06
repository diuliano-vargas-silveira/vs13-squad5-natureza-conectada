package utils;

import exceptions.ErroDigitacaoException;

import java.util.Scanner;

public class Teclado {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String ESPACO = " ";

    public static String nextString(String mensagem) {
        String input = "";
        boolean isVazio = true;

        while (isVazio) {
            try {
                System.out.println(mensagem + ESPACO);
                input = scanner.nextLine();

                isVazio = isInputVazio(input);

                if (isVazio) {
                    throw new ErroDigitacaoException();
                }
            } catch (ErroDigitacaoException e) {
                System.err.println(e.getMessage());
            }
        }

        return input;
    }

    public static Integer nextInt(String mensagem) throws NumberFormatException {
        String input = "";
        boolean isValido = true;
        Integer inputInteiro = null;

        while (isValido) {
            try {
                System.out.println(mensagem + ESPACO);
                input = scanner.nextLine();

                isValido = isInputVazio(input);

                if (isValido) {
                    throw new ErroDigitacaoException();
                }

                inputInteiro = Integer.parseInt(input);
            } catch (ErroDigitacaoException e) {
                System.err.println(e.getMessage());
            } catch (NumberFormatException e) {
                isValido = true;
                System.err.println("Número inválido! Digite novamente.");
            }
        }

        return inputInteiro;
    }

    public static Double nextDouble(String mensagem) throws NumberFormatException {
        String input = "";
        boolean isValido = true;
        Double inputFlutuante = null;

        while (isValido) {
            try {
                System.out.println(mensagem + ESPACO);
                input = scanner.nextLine();

                isValido = isInputVazio(input);

                if (isValido) {
                    throw new ErroDigitacaoException();
                }

                inputFlutuante = Double.parseDouble(input);
            } catch (ErroDigitacaoException e) {
                System.err.println(e.getMessage());
            } catch (NumberFormatException e) {
                isValido = true;
                System.err.println("Número inválido! Digite novamente.");
            }
        }

        return inputFlutuante;
    }


    private static boolean isInputVazio(String input) {
        return input.isEmpty() || input.isBlank();
    }
}
