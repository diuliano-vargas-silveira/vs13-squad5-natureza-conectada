package models;

import enums.TipoUsuario;

public class Cliente extends Usuario {
    public Cliente(int id, String nome, String email, String senha, TipoUsuario tipoUsuario) {
        super(id, nome, email, senha, tipoUsuario);
    }
}
