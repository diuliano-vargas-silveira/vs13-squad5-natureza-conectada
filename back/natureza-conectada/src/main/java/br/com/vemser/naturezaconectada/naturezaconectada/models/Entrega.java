package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Entrega")
@Table(name = "ENTREGA")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENTREGA_SEQ")
    @SequenceGenerator(name = "ENTREGA_SEQ", sequenceName = "SEQ_ENTREGA", allocationSize = 1)
    @Column(name = "ID_ENTREGA")
    private Integer id;
    @Column(name = "ID_CLIENTE")
    private Cliente cliente;
    @Column(name = "ID_ENDERECO")
    private Integer idEndereco;
    @Column(name = "STATUS")
    private StatusEntrega status;
    private List<Muda> mudas = new ArrayList<>();


}