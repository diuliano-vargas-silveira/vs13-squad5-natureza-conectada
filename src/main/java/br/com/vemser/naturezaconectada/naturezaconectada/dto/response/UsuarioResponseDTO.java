package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioResponseDTO {

    @Schema(description = "Identificador do usuário", example = "1")
    private Integer id;
    @Schema(description = "Nome do usuário", example = "Lucas Alves")
    private String nome;
    @Schema(description = "E-mail", example = "lucas@gmail.com")
    private String email;
    @Schema(description = "Tipo do usuário", example = "ADMIN")
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Schema(description = "Ativo", example = "A")
    @Enumerated(EnumType.STRING)
    private Ativo ativo;

    @JsonIgnore
    private String senha;
    private String cpf;
}
