package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
@AllArgsConstructor
public class EnderecoEntregaDTO {

    private static final String PAIS = "Brasil";
    private Integer idEndereco;
    @Schema(description = "CEP do Endereço (8 caracteres)", required = true, example = "12345678")
    private String cep;
    @Schema(description = "Logradouro", required = true, example = "Rua ABC")
    private String logradouro;
    @Schema(description = "Número do Endereço", required = true, example = "123")
    private String numero;
    @Schema(description = "Complemento do Endereço", example = "Apto 717")
    private String complemento;
    @Schema(description = "Cidade", required = true, example = "Porto Alegre")
    private String cidade;
    @Enumerated(EnumType.STRING)
    @Schema(description = "Estado", required = true, example = "RS")
    private Estados estado;

}