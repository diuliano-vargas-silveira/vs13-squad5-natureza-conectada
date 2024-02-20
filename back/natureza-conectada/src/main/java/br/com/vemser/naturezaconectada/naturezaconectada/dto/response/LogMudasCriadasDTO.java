package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogMudasCriadasDTO {

    private String id;

    private String nomeMuda;

    private String nomeDoAdmin;

    private LocalDateTime criadoEm;
}
