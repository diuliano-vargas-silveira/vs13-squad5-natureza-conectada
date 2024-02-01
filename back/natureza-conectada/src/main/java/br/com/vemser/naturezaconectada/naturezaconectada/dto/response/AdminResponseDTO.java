package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminResponseDTO {
    @Schema(description = "Identificador do admin", example = "1")
    private Integer idAdmin;
}
