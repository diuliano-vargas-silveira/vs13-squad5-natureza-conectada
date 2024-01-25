package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioResponseDTO {

    @Schema(description = "Identificador do usuário", example = "1")
    private int id;
    @Schema(description = "Nome do usuário", example = "Lucas Alves")
    private String nome;
    @Schema(description = "E-mail", example = "lucas@gmail.com")
    private String email;
    @Schema(description = "Tipo do usuário", example = "ADMIN")
    private TipoUsuario tipoUsuario;
}
