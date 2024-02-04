package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ClienteRepository {


    public Cliente adicionar(Cliente cliente) throws Exception {
        return null;
    }

    public Cliente editar(Integer id, Cliente clienteEditado) throws Exception {
        return null;
    }

    public void remover(Integer id) throws Exception {
    }

    public List<Cliente> listarTodos() throws Exception {
        return null;
    }

    public List<Muda> listarMudasPorcliente(Integer idCliente) throws Exception {
        return null;
    }

    private List<Contato> procurarContatoPorIdCliente(Connection conexao, Integer idCliente) throws SQLException {
        return null;
    }

    private List<Endereco> procurarEnderecosPorIdCliente(Connection conexao, Integer idCliente) throws SQLException {
        return null;
    }

    private Cliente getCliente(ResultSet usuario) throws SQLException {
        return null;
    }

    public Cliente listarPorEmail(String email) throws Exception {
        return null;
    }

    public void inserirMudaEmCliente(Integer idCliente, Integer idMuda) throws Exception {
    }
}