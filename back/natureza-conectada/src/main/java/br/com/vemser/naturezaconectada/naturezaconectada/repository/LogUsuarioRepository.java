package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.models.LogContaUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.models.LogUsuarios;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogUsuarioRepository extends MongoRepository<LogUsuarios, String> {


    @Aggregation(pipeline = {
            "{$unwind: '$tipoUsuario'}",
            "{$group: { _id: '$tipoUsuario', quantidade: { $sum: 1 } }}"
    })
    List<LogContaUsuario> groupByTipoUsuarioAndCount();
}
