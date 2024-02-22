package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.models.LogMudasCriadas;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogMudasCriadasRepository extends MongoRepository<LogMudasCriadas,String> {

    List<LogMudasCriadas> findAllBynomeDoAdminLikeIgnoreCase(String nomeAdmin);

}
