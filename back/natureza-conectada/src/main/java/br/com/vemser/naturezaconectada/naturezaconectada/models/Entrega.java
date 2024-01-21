package br.com.vemser.naturezaconectada.naturezaconectada.models;


import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Entrega {

    private int id;

    @NotEmpty(message = "Mudas não pode estar vazia!")
    private List<Muda> mudas = new ArrayList<>();

    @NotNull(message = "Status da entrega precisa ser preenchido!")
    private StatusEntrega status;

    @NotNull(message = "Cliente não pode ser nulo!")
    private Cliente cliente;

}