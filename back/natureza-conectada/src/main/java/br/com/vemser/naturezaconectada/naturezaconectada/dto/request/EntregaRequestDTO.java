package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class EntregaRequestDTO {

    @Schema(description = "Identificador da entrega", required = false, hidden = true, example = "1")
    private int id;

    @Schema(description = "Informações do cliente", required = true, implementation = Cliente.class)
    @NotNull(message = "Cliente não pode ser nulo!")
    private Cliente cliente;

    @Schema(description = "Lista de mudas", required = true, example = "[{'id': 1,'quantidade': 5},{'id': 2,'quantidade': 3}]")
    @NotEmpty(message = "Mudas não pode estar vazia!")
    private List<Muda> mudas = new ArrayList<>();

    @Schema(description = "Status da entrega", hidden = true, example = "ENTREGUE")
    private StatusEntrega status;
}
