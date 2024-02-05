package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioRequestDTO {


    @Schema(description = "Id do cliente que gerou o relatório",required = true)
    @NotNull(message = "Id do cliente que solicitou não pode ser nulo")
    private Integer idCliente;

    @Schema(description = "Id do especialista que avaliou o relatório")
    private Integer idEspecialista;

    @Schema(description = "Id da muda que foi solicitado avaliação",required = true)
    @NotNull(message = "Id do cliente que solicitou não pode ser nulo")
    private Integer idMuda;


    @Schema(description = "Um breve resumo de como está o estado da muda atualmente",required = true)
    @NotNull(message = "Id do cliente que solicitou não pode ser nulo")
    private String estadoMuda;

    @Schema(description = "Sugestões dadas pelo especialista de melhoria nos cuidados da muda",required = true)
    private String sugestoes;

    @Schema(description = "Avaliação do especialista, podendo ser de 0 a 10",required = true)
    private double avaliacao;
}
