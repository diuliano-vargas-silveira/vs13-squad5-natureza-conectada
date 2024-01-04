package enums;

public enum TipoUsuario {
    ADMIN("ADMIN"),
    CLIENTE("CLIENTE"),
    ESPECIALISTA("ESPECIALISTA");

    private String nome;

    TipoUsuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
