package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EntregaResponseDTO {

    @Schema(description = "Identificador da entrega", example = "1")
    private Integer id;
    @Schema(description = "Status da entrega", example = "ENTREGUE")
    @Enumerated(EnumType.STRING)
    private StatusEntrega status;
    @Schema(description = "Data do pedido gerado", example = "2024-10-10")
    private LocalDate dataPedido;
    @Schema(description = "Data da entrega ao cliente", example = "2024-10-10")
    private LocalDate dataEntregue;
    @Schema(description = "Informações do cliente", implementation = ClienteResponseDTO.class)
    private ClienteEntregaDTO cliente;
    @Schema(description = "Lista de mudas", example = "[{'id': 1,'quantidade': 5},{'id': 2,'quantidade': 3}]")
    private List<MudaDTO> mudas = new ArrayList<>();
    @Schema(description = "Endereço do cliente", example = "{'rua': 'Aimorés', 'numero': 100, 'cidade': 'Belo Horizonte', 'estado': 'MG', 'pais': 'Brasil'}")
    private EnderecoEntregaDTO endereco;

}
