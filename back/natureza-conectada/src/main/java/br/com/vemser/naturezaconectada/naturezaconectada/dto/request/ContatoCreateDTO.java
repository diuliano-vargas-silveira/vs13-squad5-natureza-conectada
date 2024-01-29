package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContatoCreateDTO {

    private Integer id;
    private Integer idCliente;

    @NotBlank(message = "Descrição não pode ser vazia!")
    @Length(max = 255, message = "Mensagem muito longa!")
    private String descricao;

    @NotBlank(message = "Número não pode ser vazio!")
    @Length(min = 11, max = 11, message = "Número tem que ter tamanho de 11!")
    private String numero;

    @NotNull(message = "Tipo de Contato não pode ser nulo!")
    private Tipo tipo;
}
