package br.com.vemser.naturezaconectada.naturezaconectada.enums;


import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.EnumNaoExistente;

public enum Tipo {

    RESIDENCIAL,
    COMERCIAL;

    public static Tipo ofTipo(Integer tipo) throws EnumNaoExistente {
        return switch (tipo) {
            case 1 -> Tipo.RESIDENCIAL;
            case 2 -> Tipo.COMERCIAL;
            default -> throw new EnumNaoExistente("Valor inexistente!");
        };
    }

    public static int getByCodigo(int tipoCodigo) {
        return 0;
    }

    public int getCodigo() {
        return 0;
    }
}
