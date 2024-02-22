package br.com.vemser.naturezaconectada.naturezaconectada.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("log_mudas")
public class LogMudasCriadas {
    @Id
    private String id;

    private String nomeMuda;

    private String nomeDoAdmin;

    private LocalDateTime criadoEm;
}
