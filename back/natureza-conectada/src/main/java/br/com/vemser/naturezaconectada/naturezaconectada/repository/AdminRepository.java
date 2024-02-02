package br.com.vemser.naturezaconectada.naturezaconectada.repository;


import br.com.vemser.naturezaconectada.naturezaconectada.models.Admin;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class AdminRepository {

    public Admin adicionar(Admin admin) throws Exception {
        return null;
    }

    public boolean remover(Integer id) throws Exception {
        return false;
    }

    public boolean editar(Integer id, Admin adminEditado) throws Exception {
        return false;
    }

    public List<Admin> listar() throws Exception {
        return null;
    }

    public Admin procurarPorId(int id) throws Exception {
        return null;
    }

}