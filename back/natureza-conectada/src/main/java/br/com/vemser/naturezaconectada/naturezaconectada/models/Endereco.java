package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Endereco {

    private static final String PAIS = "Brasil";
    private Integer idEndereco;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cidade;
    private Estados estado;
    private Integer idCliente;
    private Tipo tipo;
    private Ecossistema ecossistema;
    private Ativo ativo;

}