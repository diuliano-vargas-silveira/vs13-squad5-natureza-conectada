package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EntregaRequestDTO {

    @Schema(description = "Identificador da entrega", required = false, hidden = true, example = "1")
    private Integer id;
    @Schema(description = "Status da entrega", required = false, hidden = true, example = "ENTREGUE")
    @Enumerated(EnumType.STRING)
    private StatusEntrega status;
    @Schema(description = "Data do pedido gerado", required = false, hidden = true, example = "2024-10-10")
    private LocalDate dataPedido;
    @Schema(description = "Data da entrega ao cliente", required = false, hidden = true, example = "2024-10-10")
    private LocalDate dataEntregue;
    @Schema(description = "Endereço do cliente", required = false, hidden = true, example = "{'rua': 'Aimorés', 'numero': 100, 'cidade': 'Belo Horizonte', 'estado': 'MG', 'pais': 'Brasil'}")
    private Endereco endereco;
    @Schema(description = "Identificador do cliente", required = true, example = "1")
    @NotNull(message = "Id do cliente não pode ser nulo!")
    private Integer idCliente;
    @Schema(description = "Lista de mudas", required = true, example = "[{'id': 1,'quantidade': 5},{'id': 2,'quantidade': 3}]")
    @NotEmpty(message = "Mudas não pode estar vazia!")
    private List<Muda> mudas = new ArrayList<>();


}
