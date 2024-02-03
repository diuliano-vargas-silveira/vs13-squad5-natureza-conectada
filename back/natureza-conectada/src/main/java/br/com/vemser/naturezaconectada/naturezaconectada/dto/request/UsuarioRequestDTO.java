package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioRequestDTO {

    private static final String PASSWORD_VALIDATION_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[a-zA-Z]).{8,}$";

    @Schema(description = "Identificador do usuário", required = false, hidden = true, example = "1")
    private Integer id;

    @Schema(description = "Ativo", example = "A")
    private Ativo ativo;

    @Schema(description = "Nome do usuário", required = true, example = "Lucas Alves")
    @NotBlank(message = "Nome não pode estar vazio!")
    private String nome;

    @Schema(description = "E-mail", required = true, example = "lucas@gmail.com")
    @NotBlank(message = "E-mail não pode estar vazio!")
    @Email(message = "Formato de e-mail inválido!")
    private String email;

    @Schema(description = "Senha", required = true, example = "lucasalves1")
    @NotBlank(message = "Senha não pode estar vazia!")
    @Length(min = 8, max = 255, message = "Tamanho de senha inválido, tenha no mínimo 8 caractéres!")
    @Pattern(regexp = PASSWORD_VALIDATION_REGEX,
            message = "Senha com formato inválido, você precisa de pelo menos 1 número e 1 letra, com no mínimo 8 de tamanho!")
    private String senha;

    @Schema(description = "Tipo do usuário", required = false, hidden = true, example = "ADMIN")
    private TipoUsuario tipoUsuario;
}
