package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
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
    private Integer idContato;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "NUMERO")
    private String numero;
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_CONTATO")
    private Tipo tipo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Cliente cliente;


}