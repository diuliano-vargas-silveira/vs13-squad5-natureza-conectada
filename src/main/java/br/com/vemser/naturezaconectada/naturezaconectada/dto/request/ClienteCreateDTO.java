package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteCreateDTO extends UsuarioRequestDTO {

    private Integer idCliente;
    @Schema(description = "CPF", required = true, example = "12345678901")
    private String cpf;
    @NotNull(message = "Endereço não pode ser nulo")
    @Schema(description = "Endereços", required = true)
    private List<Endereco> enderecos = new ArrayList<>();
    @NotNull(message = "Contatos não pode ser nulo")
    @Schema(description = "Contatos", required = true)
    private List<Contato> contatos = new ArrayList<>();
    @Schema(description = "Mudas", required = true)
    private List<Muda> mudas = new ArrayList<>();
    @Schema(description = "Entregas", required = true)
    private List<Entrega> entregas = new ArrayList<>();
}