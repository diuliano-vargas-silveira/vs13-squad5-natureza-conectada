package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

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
    @Enumerated(EnumType.STRING)
    private StatusEntrega status;

    @Column(name = "DATA_PEDIDO")
    private LocalDate dataPedido;

    @Column(name = "DATA_ENTREGUE")
    private LocalDate dataEntrega;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_USUARIO")
    private Cliente cliente;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENDERECO", referencedColumnName = "ID_ENDERECO")
    private Endereco endereco;
}