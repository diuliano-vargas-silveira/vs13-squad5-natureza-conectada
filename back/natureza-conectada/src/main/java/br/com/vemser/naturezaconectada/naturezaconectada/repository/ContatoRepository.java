package br.com.vemser.naturezaconectada.naturezaconectada.repository;


import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ContatoRepository {


    public Contato adicionar(Contato contato, Integer idCliente) throws Exception {
        return null;
    }

    public Contato editar(Integer id, Contato contato) throws Exception {
        return null;
    }

    public List<Contato> listarTodos() throws SQLException {
        return null;
    }

    public List<Contato> procurarContatoPorIdCliente(Integer idCliente) throws Exception {
        return null;
    }

    public void excluir(Integer idContato) throws Exception {
    }
}
