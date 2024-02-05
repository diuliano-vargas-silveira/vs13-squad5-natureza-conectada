package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
public class EnderecoCreateDTO {

    private static final String PAIS = "Brasil";
    private Integer idEndereco;
    private List<Cliente> clientes;
    @NotEmpty
    @NotNull
    @Size(min = 8, max = 8)
    @Schema(description = "CEP do Endereço (8 caracteres)", required = true, example = "12345678")
    private String cep;
    @NotEmpty
    @Size(min = 1, max = 100)
    @Schema(description = "Logradouro", required = true, example = "Rua ABC")
    private String logradouro;
    @NotNull
    @Positive
    @Schema(description = "Número do Endereço", required = true, example = "123")
    private String numero;
    @NotNull
    @Schema(description = "Complemento do Endereço", example = "Apto 717")
    private String complemento;
    @NotEmpty
    @NotNull
    @Size(min = 1, max = 250)
    @Schema(description = "Cidade", required = true, example = "Porto Alegre")
    private String cidade;
    @NotNull
    @Schema(description = "Estado", required = true, example = "RS")
    private Estados estado;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Schema(description = "Tipo de Endereço", required = true)
    private Tipo tipo;
    @Enumerated(EnumType.STRING)
    @Schema(description = "Ecossistema", required = true)
    private Ecossistema ecossistema;
    private Ativo ativo;

}