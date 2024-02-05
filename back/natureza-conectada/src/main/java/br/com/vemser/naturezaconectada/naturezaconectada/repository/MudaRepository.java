package br.com.vemser.naturezaconectada.naturezaconectada.repository;

//import br.com.vemser.naturezaconectada.naturezaconectada.dto.relatorios.RelatorioMudasDoadas;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.relatorios.RelatorioMudasDoadas;
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

    @Query(value = "SELECT m.NOME,COUNT(m.NOME) AS doacoes  FROM VS_13_EQUIPE_5.muda m LEFT JOIN VS_13_EQUIPE_5.CLIENTE_MUDA cm ON m.ID_MUDA = CM.ID_MUDA GROUP BY(nome)\n",nativeQuery = true)
    public List<RelatorioMudasDoadas>mudasDoadas();






}