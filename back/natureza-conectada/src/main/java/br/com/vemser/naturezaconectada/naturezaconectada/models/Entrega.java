package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(name = "STATUS")
    private StatusEntrega status;

    private LocalDate dataPedido;
    private LocalDate dataEntrega;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_USUARIO")
    private Cliente cliente;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "ENTREGA_MUDA",
            joinColumns = @JoinColumn(name = "ID_ENTREGA"),
            inverseJoinColumns = @JoinColumn(name = "ID_MUDA"))
    private List<Muda> mudas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENDERECO", referencedColumnName = "ID_ENDERECO")
    private Endereco endereco;


}