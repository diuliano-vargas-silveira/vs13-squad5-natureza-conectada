package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudaRepository extends JpaRepository<Muda, Integer> {


    List<Muda> findByAtivoIs(Ativo ativo);

    List<Muda> findByEcossistemaIs(Ecossistema ecossistema);


}