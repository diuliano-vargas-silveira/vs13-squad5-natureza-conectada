package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EspecialistaCreateDTO extends UsuarioRequestDTO{

    private int idEspecialista;

    private Contato contato;
    @Schema(description = "Documento, um CPF valido", required = true, example = "83303022020")
    @NotBlank(message = "Documento não pode ser vazia!")
    @CPF(message = "Cpf inválido!")
    private String documento;

  @Hidden
    private String especializacao;

    @Schema(description = "Região responsável (Apenas a Sigla do Estado Maiusculo)", required = true, example = "AC")
    private Estados regiaoResponsavel;

}
