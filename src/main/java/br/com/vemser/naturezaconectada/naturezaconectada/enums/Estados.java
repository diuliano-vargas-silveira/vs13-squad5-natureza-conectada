package br.com.vemser.naturezaconectada.naturezaconectada.enums;


import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.EnumNaoExistente;

public enum Estados {
    AC("Acre"),

    SP("São Paulo"),

    RS("Rio Grande do Sul");

//    AL("Alagoas"),
//    AP("Amapá"),
//    AM("Amazonas"),
//    BA("Bahia"),
//    CE("Ceará"),
//    DF("Distrito Federal"),
//    ES("Espírito Santo"),
//    GO("Goiás"),
//    MA("Maranhão"),
//    MT("Mato Grosso"),
//    MS("Mato Grosso do Sul"),
//    MG("Minas Gerais"),
//    PA("Pará"),
//    PB("Paraíba"),
//    PR("Paraná"),
//    PE("Pernambuco"),
//    PI("Piauí"),
//    RJ("Rio de Janeiro"),
//    RN("Rio Grande do Norte"),
//
//    RO("Rondônia"),
//    RR("Roraima"),
//    SC("Santa Catarina"),
//
//    SE("Sergipe"),
//    TO("Tocantins");

    private final String nome;

    Estados(String nome) {
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public static Estados ofTipo(Integer tipo) throws EnumNaoExistente {
        return switch (tipo) {
            case 1 -> Estados.AC;
            case 2 -> Estados.RS;
            case 3-> Estados.SP;
            default -> throw new EnumNaoExistente("Este tipo de muda não existe!");
        };
    }
}
