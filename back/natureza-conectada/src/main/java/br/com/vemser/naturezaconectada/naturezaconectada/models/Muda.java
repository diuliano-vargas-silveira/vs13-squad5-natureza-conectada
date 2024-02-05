package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TamanhoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoMuda;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "ESTOQUE")
    private int estoque;

    @Column(name = "TIPO_MUDA")
    @Enumerated(EnumType.STRING)
    private TipoMuda tipo;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "NOME_CIENTIFICO")
    private String nomeCientifico;

    @Column(name = "PORTE")
    @Enumerated(EnumType.STRING)
    private TamanhoMuda porte;

    @Column(name = "ECOSSISTEMA")
    @Enumerated(EnumType.STRING)
    private Ecossistema ecossistema;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "ATIVO")
    private Ativo ativo;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "CLIENTE_MUDA",
            joinColumns = @JoinColumn(name = "ID_MUDA"),
            inverseJoinColumns = @JoinColumn(name = "ID_CLIENTE"))
    private List<Cliente> cliente;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "ENTREGA_MUDA",
            joinColumns = @JoinColumn(name = "ID_ENTREGA"),
            inverseJoinColumns = @JoinColumn(name = "ID_MUDA"))
    private List<Entrega> entregas;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "muda", orphanRemoval = true)
    private List<Relatorio> relatorios;
}