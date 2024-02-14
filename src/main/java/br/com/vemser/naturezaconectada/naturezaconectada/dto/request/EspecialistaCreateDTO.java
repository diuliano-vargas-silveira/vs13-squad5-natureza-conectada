package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import br.com.vemser.naturezaconectada.naturezaconectada.models.RelatorioMuda;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class EspecialistaCreateDTO extends UsuarioRequestDTO {

    @Schema(description = "Documento de idenificação da especialização, (carteirinha, certificado e etc)", required = true, example = "83303022020")
    @NotBlank(message = "Documento não pode ser vazia!")
    private String documento;

    @Schema(description = "Nome da  especialização", required = true, example = "BIOLOGIA")
    @NotBlank(message = "especialização não pode ser vazia!")
    private String especializacao;

    private List<RelatorioMuda> relatorioMudas;

}
