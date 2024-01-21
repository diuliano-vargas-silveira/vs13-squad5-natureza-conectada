package br.com.vemser.naturezaconectada.naturezaconectada.models;


import br.com.vemser.naturezaconectada.naturezaconectada.enums.TamanhoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoMuda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Muda {

    private int id;

    private int quantidade;

    @NotNull(message = "Tipo da Muda não pode ser nulo!")
    private TipoMuda tipo;

    @NotBlank(message = "Nome da não pode ser vazia!")
    private String nome;

    @NotBlank(message = "Nome científico não pode ser vazio!")
    private String nomeCientifico;

    @NotNull(message = "Porte da muda deve ser preenchido!")
    private TamanhoMuda porte;

    @NotBlank(message = "Ambiente ideal não pode ser vazio!")
    private String ambienteIdeal;

    private String descricao;

}

