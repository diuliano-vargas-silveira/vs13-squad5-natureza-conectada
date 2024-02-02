package br.com.vemser.naturezaconectada.naturezaconectada.models;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TamanhoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoMuda;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Muda")
@Table(name = "MUDA")
public class Muda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUDA_SEQ")
    @SequenceGenerator(name = "MUDA_SEQ", sequenceName = "SEQ_MUDA", allocationSize = 1)
    @Column(name = "ID_MUDA")
    private Integer id;
    @Column(name = "QUANTIDADE")
    private int quantidade;
    @Column(name = "TIPO_MUDA")
    private TipoMuda tipo;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "NOME_CIENTIFICO")
    private String nomeCientifico;
    @Column(name = "PORTE")
    private TamanhoMuda porte;
    @Column(name = "ECOSSISTEMA")
    private Ecossistema ecossistema;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "ATIVO")
    private Ativo ativo;

}

