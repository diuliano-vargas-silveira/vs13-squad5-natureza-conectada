package br.com.vemser.naturezaconectada.naturezaconectada.dto.request;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
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

    @NotBlank(message = "Documento não pode ser vazia!")
    @CPF(message = "Cpf inválido!")
    private String documento;

    @NotBlank(message = "Especialização não pode ser vazia!")
    private String especializacao;


    private Estados regiaoResponsavel;

}