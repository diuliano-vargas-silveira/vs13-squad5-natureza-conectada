package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class EntregaRepository {

    public Entrega adicionar(Entrega entrega, Integer idEndereco) throws Exception {
        return null;
    }

    private void adicionarMudas(Connection conexao, int idEntrega, List<Muda> mudas) throws SQLException {
    }

    public Muda remover(Integer id) throws SQLException {
        return null;
    }

    public Entrega editarStatus(int idEntrega, StatusEntrega status) throws Exception {
        return null;
    }

    public void atualizarMudas( int idEntrega, List<Muda> mudas) throws SQLException {
    }

    public List<Entrega> listar() throws Exception {
        return null;
    }

    private Cliente obterClienteDaEntrega(Connection conexao, int idEntrega) throws SQLException {
        return null;
    }

    public Entrega procurarPorId(int idEntrega) throws Exception {
        return null;
    }
}