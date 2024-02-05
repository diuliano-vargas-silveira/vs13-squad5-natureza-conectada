package br.com.vemser.naturezaconectada.naturezaconectada.enums;

public enum TipoUsuario {
    CLIENTE("CLIENTE"),
    ESPECIALISTA("ESPECIALISTA"),
    ADMIN("ADMIN");

    private String nome;

    TipoUsuario(String nome) {
        this.nome = nome;
    }

    public static TipoUsuario ofTipo(Integer tipo) {
        return switch (tipo) {
            case 1 -> TipoUsuario.CLIENTE;
            case 2 -> TipoUsuario.ESPECIALISTA;
            default -> TipoUsuario.ADMIN;
        };
    }

    public String getNome() {
        return nome;
    }
}
