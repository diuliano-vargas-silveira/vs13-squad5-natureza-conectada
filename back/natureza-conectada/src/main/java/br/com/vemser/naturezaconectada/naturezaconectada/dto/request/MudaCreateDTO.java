package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.TamanhoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoMuda;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MudaCreateDTO {
    @Hidden
    private Integer id;

    @Schema(description = "Quantidade de mudas", required = true, example = "41")
    @NotNull(message = "Quantidade não pode ser nulo ")
    private int quantidade;

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

    @Schema(description = "Ambiente ideal da muda", required = true, example = "árido")
    @NotBlank(message = "Ambiente ideal não pode ser vazio!")
    private String ambienteIdeal;

    @Schema(description = "Descrição da muda", example = "arvore grande com folhas pequenas")
    private String descricao;

}