package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Contato")
@Table(name = "CONTATO")
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTATO_SEQ")
    @SequenceGenerator(name = "CONTATO_SEQ", sequenceName = "SEQ_CONTATO", allocationSize = 1)
    @Column(name = "ID_CONTATO")
    private Integer id;
    @Column(name = "ID_USUARIO")
    private Integer idCliente;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "NUMERO")
    private String numero;
    @Column(name = "TIPO_CONTATO")
    private Tipo tipo;

}