package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class EnderecoDTO {

    private static final String PAIS = "Brasil";

    @NotNull
    private int idEndereco;

    @NotNull
    private Integer idCliente;

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

    @NotNull
    @Schema(description = "Tipo de Endereço", required = true)
    private Tipo tipo;

}