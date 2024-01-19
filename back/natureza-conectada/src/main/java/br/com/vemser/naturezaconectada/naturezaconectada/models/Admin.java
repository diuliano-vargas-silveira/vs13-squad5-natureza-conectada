package br.com.vemser.naturezaconectada.naturezaconectada.models;


import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;

public class Admin extends Usuario{

    private int idAdmin;

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Admin() {
        super.setTipoUsuario(TipoUsuario.ADMIN);
    }

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
