package br.com.vemser.naturezaconectada.naturezaconectada.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity(name = "Relatorio")
@Table(name = "RELATORIO")
public class RelatorioMuda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELATORIO_SEQ")
    @SequenceGenerator(name = "RELATORIO_SEQ", sequenceName = "SEQ_RELATORIO", allocationSize = 1)
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
    private Double avaliacao;

}
