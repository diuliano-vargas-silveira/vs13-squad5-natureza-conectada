package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer> {


    Optional<Cliente> findByAtivoAndId(Ativo ativo, Integer id);


    @Query(value = "SELECT u.* FROM vs_13_equipe_5.USUARIO u " +
            "JOIN vs_13_equipe_5.ENTREGA e ON e.id_cliente = u.id_usuario " +
            "WHERE e.id_entrega = :idEntrega", nativeQuery = true)
    Cliente buscarClienteEntrega(@Param("idEntrega") Integer idEntrega);

}
