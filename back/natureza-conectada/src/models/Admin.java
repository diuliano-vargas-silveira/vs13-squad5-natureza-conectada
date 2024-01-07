package models;

import enums.TipoUsuario;

public class Admin extends Usuario{
    public Admin(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.ADMIN);
    }

    @Override
    public String toString() {
        return "\nID: " + getId() +
                "\nNome: " + getNome() +
                "\nE-mail: " + getEmail();
    }
}
