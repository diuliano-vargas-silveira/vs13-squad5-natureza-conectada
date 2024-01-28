package br.com.vemser.naturezaconectada.naturezaconectada.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente extends Usuario {

    private int idCliente;
    private String cpf;
    private List<Endereco> enderecos = new ArrayList<>();
    private List<Contato> contatos = new ArrayList<>();
    private List<Muda> mudas = new ArrayList<>();
    private List<Entrega> entregas = new ArrayList<>();
}