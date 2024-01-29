package br.com.vemser.naturezaconectada.naturezaconectada.models;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TamanhoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoMuda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Muda {

    private int id;

    private int quantidade;

    private TipoMuda tipo;

    private String nome;

    private String nomeCientifico;

    private TamanhoMuda porte;

    private Ecossistema ecossistema;

    private String descricao;

    private Ativo ativo;

}

