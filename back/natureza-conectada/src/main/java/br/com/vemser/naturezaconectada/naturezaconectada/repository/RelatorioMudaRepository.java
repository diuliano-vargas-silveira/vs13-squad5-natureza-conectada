package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.models.RelatorioMuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioMudaRepository extends JpaRepository<RelatorioMuda, Integer> {

    public List<RelatorioMuda> findByEspecialistaIs(Especialista especialista);


    List<RelatorioMuda> findByAvaliacaoIsNull();

    List<RelatorioMuda> findByClienteIs(Cliente cliente);
}
