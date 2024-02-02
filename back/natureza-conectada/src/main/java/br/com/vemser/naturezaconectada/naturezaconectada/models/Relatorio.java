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
    @NotNull(message = "Dono não pode ser nulo!")
    @Column(name = "ID_CLIENTE")
    private Cliente dono;
    private Especialista avaliador;
    @NotNull(message = "Muda não pode ser nula!")
    @Column(name = "ID_MUDA")
    private Muda muda;
    @Column(name = "ESTADO_MUDA")
    private String estadoMuda;
    private String sugestoes;
    private double avaliacaoEspecialista;

}
