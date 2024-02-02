package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Especialista")
@Table(name = "ESPECIALISTA")
public class Especialista extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESPECIALISTA_SEQ")
    @SequenceGenerator(name = "ESPECIALISTA_SEQ", sequenceName = "SEQ_ESPECIALISTA", allocationSize = 1)
    @Column(name = "ID_ESPECIALISTA")
    private Integer idEspecialista;
    private Contato contato;
    @Column(name = "DOCUMENTO")
    private String documento;
    @Column(name = "ESPECIALIZACAO")
    private String especializacao;
    @Column(name = "ID_ESTADO")
    private Estados regiaoResponsavel;

}
