package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Query(value = "SELECT ed.* FROM vs_13_equipe_5.ENDERECO ed " +
            "JOIN vs_13_equipe_5.ENTREGA e ON e.id_endereco = ed.id_endereco " +
            "WHERE e.id_entrega = :idEntrega", nativeQuery = true)
    Endereco buscarEnderecoEntrega(@Param("idEntrega") Integer idEntrega);
}
