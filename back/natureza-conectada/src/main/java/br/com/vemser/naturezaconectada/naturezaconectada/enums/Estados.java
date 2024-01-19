package br.com.vemser.naturezaconectada.naturezaconectada.enums;


import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.EnumNaoExistente;

public enum Estados {
    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraíba"),
    PR("Paraná"),
    PE("Pernambuco"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins");

    private final String nome;

    Estados(String nome) {
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public static Estados ofTipo(Integer tipo) {
        return switch (tipo) {
            case 1 -> Estados.AC;
            case 2 -> Estados.AL;
            case 3 -> Estados.AP;
            case 4 -> Estados.AM;
            case 5 -> Estados.BA;
            case 6 -> Estados.CE;
            case 7 -> Estados.DF;
            case 8 -> Estados.ES;
            case 9 -> Estados.GO;
            case 10 -> Estados.MA;
            case 11 -> Estados.MT;
            case 12 -> Estados.MS;
            case 13 -> Estados.MG;
            case 14 -> Estados.PA;
            case 15 -> Estados.PB;
            case 16 -> Estados.PR;
            case 17 -> Estados.PE;
            case 18 -> Estados.PI;
            case 19 -> Estados.RJ;
            case 20 -> Estados.RN;
            case 21 -> Estados.RS;
            case 22 -> Estados.RO;
            case 23 -> Estados.RR;
            case 24 -> Estados.SC;
            case 25-> Estados.SP;
            case 26 -> Estados.SE;
            case 27-> Estados.TO;
            default -> throw new EnumNaoExistente("Este tipo de muda não existe!");
        };
    }
}
