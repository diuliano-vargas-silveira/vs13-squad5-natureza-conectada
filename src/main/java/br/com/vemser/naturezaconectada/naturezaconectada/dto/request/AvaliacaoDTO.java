package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class AvaliacaoDTO {

    @NotBlank(message = "Sugestões não pode estar nulo")
    @Schema(description = "Sugestões dadas pelo especialista de melhoria nos cuidados da muda", required = true)
    private String sugestoes;

    @NotNull(message = "Avaliação não pode estar nulo")
    @Schema(description = "Avaliação do especialista, podendo ser de 0 a 10", required = true)
    private double avaliacao;


}
