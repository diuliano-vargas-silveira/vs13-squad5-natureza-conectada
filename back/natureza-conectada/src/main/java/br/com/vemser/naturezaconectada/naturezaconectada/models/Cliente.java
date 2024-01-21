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

    @CPF(message = "CPF inválido!")
    @NotBlank(message = "CPF não pode ser vazio!")
    private String cpf;

    private List<Endereco> enderecos = new ArrayList<>();

    private List<Contato> contatos = new ArrayList<>();

    private List<Muda> mudas = new ArrayList<>();

    private List<Entrega> entregas = new ArrayList<>();

    private void imprimirLista(ArrayList<?> lista, String tipo) {
        System.out.println("Lista de " + tipo + ":");
        for (Object item : lista) {
            if (item != null) {
                System.out.println(item.toString());
            }
        }
        System.out.println();
    }
}