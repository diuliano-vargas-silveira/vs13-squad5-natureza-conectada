package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TamanhoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoMuda;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Retorno de muda ")
public class MudaDTO {

    private Integer id;

    private TipoMuda tipo;

    private String nome;

    private String nomeCientifico;

    private TamanhoMuda porte;

    private Ecossistema ecossistema;

    private String descricao;

    private int quantidade;
}
