package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RelatorioCreateDTO {
    private int id;

    @NotNull(message = "Dono não pode ser nulo!")
    private Cliente dono;

    private Especialista avaliador;

    @NotNull(message = "Muda não pode ser nula!")
    private Muda muda;

    @NotBlank(message = "Estado da muda não pode ser nulo")
    private String estadoMuda;

    private String sugestoes;

    private double avaliacaoEspecialista;
}
