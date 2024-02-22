package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class RelatorioResponseAdmin {
    private Integer id;

    private Integer idcliente;

    private String nomeCliente;

    private Integer idAvaliador;

    private String nomeAvaliador;

    private Muda muda;

    private String estadoMuda;

    private String sugestoes;

    private Double avaliacao;


}
