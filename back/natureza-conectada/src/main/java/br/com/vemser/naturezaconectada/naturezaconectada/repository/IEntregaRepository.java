package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEntregaRepository extends JpaRepository<Entrega, Integer> {

    @Query("UPDATE Entrega SET STATUS = :status WHERE ID_ENTREGA = :idEntrega")
    Entrega editarStatus(@Param("status") StatusEntrega status, @Param("idEntrega") Integer idEntrega);

//    void atualizarMudas(int idEntrega, List<Muda> mudas);

}
