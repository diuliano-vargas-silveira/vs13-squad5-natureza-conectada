package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import io.swagger.v3.oas.annotations.Hidden;
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


    private int idEndereco;

    private Integer idCliente;

    @Schema(description = "CEP do Endereço (8 caracteres)", required = true, example = "12345678")
    private String cep;

    @Schema(description = "Logradouro", required = true, example = "Rua ABC")
    private String logradouro;

    @Schema(description = "Número do Endereço", required = true, example = "123")
    private String numero;

    @NotNull
    @Schema(description = "Complemento do Endereço", example = "Apto 717")
    private String complemento;

    @Schema(description = "Cidade", required = true, example = "Porto Alegre")
    private String cidade;

    @Schema(description = "Estado", required = true, example = "RS")
    private Estados estado;

    @Schema(description = "Tipo de Endereço", required = true)
    private Tipo tipo;

    private Ecossistema ecossistema;

    private Ativo ativo;

}