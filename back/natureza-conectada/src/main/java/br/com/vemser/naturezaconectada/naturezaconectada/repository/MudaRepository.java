package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MudaRepository extends JpaRepository<Muda, Integer> {


    public List<Muda> findByAtivoIs(Ativo ativo);

    public Optional<Muda> findByAtivoAndId(Ativo ativo, Integer id);

    public List<Muda> findByEcossistemaIs(Ecossistema ecossistema);

    public Optional<List<Muda>> findByClienteAndIdIs(Cliente cliente,Integer idMuda);




}