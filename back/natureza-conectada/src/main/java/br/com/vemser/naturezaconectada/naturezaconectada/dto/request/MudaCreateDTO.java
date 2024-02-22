package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TamanhoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoMuda;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Validated
public class MudaCreateDTO {
    @Schema(hidden = true)
    private Integer id;

    @Schema(description = "Quantidade de mudas em estoque", required = true, example = "41")
    @NotNull(message = "estoque não pode ser nulo ")
    private int estoque;

    @Schema(description = "Tipo de Muda (PLANTA ou ARVORE)", required = true, example = "ARVORE")
    @NotNull(message = "Tipo da Muda não pode ser nulo!")
    private TipoMuda tipo;

    @Schema(description = "Nome da muda", required = true, example = "Eucalipto")
    @NotBlank(message = "Nome da não pode ser vazia!")
    private String nome;

    @Schema(description = "Nome cientifico da muda", required = true, example = "Eucalyptus")
    @NotBlank(message = "Nome científico não pode ser vazio!")
    private String nomeCientifico;

    @Schema(description = "Tamanho da muda(PEQUENO,MEDIO,GRANDE)", required = true, example = "PEQUENO")
    @NotNull(message = "Porte da muda deve ser preenchido!")
    private TamanhoMuda porte;

    @Schema(description = "Ecossistema ideal da muda (COSTEIRO, MATA_ATLANTICA, CAMPOS_ARAUCARIAS)", required = true, example = "COSTEIRO")
    @NotNull(message = "Ecossistema ideal não pode ser vazio!")
    private Ecossistema ecossistema;

    @Schema(description = "Descrição da muda", example = "arvore grande com folhas pequenas")
    private String descricao;

    @Schema(hidden = true)
    private Ativo ativo;


}