package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EntregaResponseDTO {

    @Schema(description = "Identificador da entrega", example = "1")
    private Integer id;
    @Schema(description = "Lista de mudas", example = "[{'id': 1,'quantidade': 5},{'id': 2,'quantidade': 3}]")
    private List<MudaDTO> mudas = new ArrayList<>();
    @Schema(description = "Status da entrega", example = "ENTREGUE")
    private StatusEntrega status;
    @Schema(description = "Informações do cliente", implementation = Cliente.class)
    private Cliente cliente;
}
