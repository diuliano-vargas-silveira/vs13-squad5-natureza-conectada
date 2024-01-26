package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Endereco {

    private static final String PAIS = "Brasil";
    private int idEndereco;

    @NotBlank(message = "Cep não pode estar vazio!")
    private String cep;

    @NotBlank(message = "Logradouro não pode estar vazio!")
    private String logradouro;

    @NotBlank(message = "Número não pode estar vazio!")
    private String numero;

    private String complemento;

    @NotBlank(message = "Cidade não pode estar vazia!")
    private String cidade;

    @NotNull(message = "Estado não pode vir nulo!")
    private Estados estado;

    @NotNull(message = "Cliente não pode ser nulo!")
    private Integer idCliente;

    @NotNull(message = "Endereço não pode ser nulo!")
    private Tipo tipo;

}