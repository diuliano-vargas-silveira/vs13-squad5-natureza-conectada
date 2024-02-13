package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class RelatorioRequestDTO {


    @Schema(description = "Id da muda que foi solicitado avaliação", required = true)
    @NotNull(message = "Id do cliente que solicitou não pode ser nulo")
    private Integer idMuda;


    @Schema(description = "Um breve resumo de como está o estado da muda atualmente", required = true)
    @NotNull(message = "Id do cliente que solicitou não pode ser nulo")
    private String estadoMuda;
}


