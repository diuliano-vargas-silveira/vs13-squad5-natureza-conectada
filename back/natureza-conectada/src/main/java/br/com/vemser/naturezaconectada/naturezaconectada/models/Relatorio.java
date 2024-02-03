package br.com.vemser.naturezaconectada.naturezaconectada.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Relatorio")
@Table(name = "RELATORIO")
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELATORIO_SEQ")
    @SequenceGenerator(name = "RELATORIO", sequenceName = "SEQ_RELATORIO", allocationSize = 1)
    @Column(name = "ID_RELATORIO")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_USUARIO")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESPECIALISTA", referencedColumnName = "ID_USUARIO")
    private Especialista especialista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MUDA", referencedColumnName = "ID_MUDA")
    private Muda muda;


    @Column(name = "ESTADO_MUDA")
    private String estadoMuda;

    @Column(name = "SUGESTOES")
    private String sugestoes;

    @Column(name = "AVALIACAO")
    private double avaliacaoEspecialista;

}
