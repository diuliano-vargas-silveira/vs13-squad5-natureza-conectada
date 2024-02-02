package br.com.vemser.naturezaconectada.naturezaconectada.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Relatorio {

    private Integer id;
    @NotNull(message = "Dono não pode ser nulo!")
    private Cliente dono;
    private Especialista avaliador;
    @NotNull(message = "Muda não pode ser nula!")
    private Muda muda;
    private String estadoMuda;
    private String sugestoes;
    private double avaliacaoEspecialista;

}
