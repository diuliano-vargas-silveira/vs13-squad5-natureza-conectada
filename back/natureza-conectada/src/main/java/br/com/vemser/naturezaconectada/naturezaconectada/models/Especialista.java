package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Especialista extends Usuario {

    private int idEspecialista;

    private Contato contato;

    @NotBlank(message = "Documento não pode ser vazia!")
    @CPF(message = "Cpf inválido!")
    @CNPJ(message = "Cnpj inválido")
    private String documento;

    @NotBlank(message = "Especialização não pode ser vazia!")
    private String especializacao;

    @NotNull(message = "Região não pode ser nula!")
    private Estados regiaoResponsavel;

}
