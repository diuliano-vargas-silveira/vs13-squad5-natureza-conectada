package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.models.EntregaMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.pk.EntregaMudaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntregaMudaRepository extends JpaRepository<EntregaMuda, EntregaMudaPK> {

    @Query(value = "SELECT * FROM vs_13_equipe_5.ENTREGA_MUDA em WHERE em.ID_ENTREGA = :idEntrega", nativeQuery = true)
    List<EntregaMuda> buscarMudasEntrega(@Param("idEntrega") Integer idEntrega);




}
