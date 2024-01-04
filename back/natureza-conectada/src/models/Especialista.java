package models;

import enums.TipoUsuario;

public class Especialista extends Cliente {
    public Especialista(int id, String nome, String email, String senha, TipoUsuario tipoUsuario) {
        super(id, nome, email, senha, tipoUsuario);
    }
}
