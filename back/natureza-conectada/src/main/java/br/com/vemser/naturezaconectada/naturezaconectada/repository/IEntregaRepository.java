package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public interface IEntregaRepository extends JpaRepository<Entrega, Integer> {

    Entrega editarStatus(int idEntrega, StatusEntrega status);

    void atualizarMudas( int idEntrega, List<Muda> mudas);

    Cliente obterClienteDaEntrega(Connection conexao, int idEntrega);
}
