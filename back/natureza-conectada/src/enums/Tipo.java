package enums;

import exceptions.EnumNaoExistente;

public enum Tipo {

    RESIDENCIAL, COMERCIAL;

    public static Tipo ofTipo(Integer tipo) {
        return switch (tipo) {
            case 1 -> Tipo.RESIDENCIAL;
            case 2 -> Tipo.COMERCIAL;
            default -> throw new EnumNaoExistente("Valor inexistente!");
        };
    }

}
