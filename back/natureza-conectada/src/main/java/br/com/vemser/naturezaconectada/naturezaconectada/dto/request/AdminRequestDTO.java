package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminRequestDTO extends UsuarioRequestDTO {
    @Schema(description = "Identificador do admin", required = false, hidden = true, example = "1")
    private int idAdmin;
}
