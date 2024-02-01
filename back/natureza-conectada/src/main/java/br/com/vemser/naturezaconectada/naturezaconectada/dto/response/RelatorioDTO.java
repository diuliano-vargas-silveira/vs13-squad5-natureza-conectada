package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RelatorioDTO {

    private Integer id;

    @NotNull(message = "Dono não pode ser nulo!")
    private Cliente dono;

    private String avaliador;

    @NotNull(message = "Muda não pode ser nula!")
    private Muda muda;

    @NotBlank(message = "Estado da muda não pode ser nulo")
    private String estadoMuda;

    private String sugestoes;

    private double avaliacaoEspecialista;

}
