package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
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
@Entity(name = "Endereco")
@Table(name = "ENDERECO")
public class Endereco {

    private static final String PAIS = "Brasil";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENDERECO_SEQ")
    @SequenceGenerator(name = "ENDERECO_SEQ", sequenceName = "SEQ_ENDERECO", allocationSize = 1)
    @Column(name = "ID_ENDERECO")
    private Integer idEndereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO")
    private Estados estado;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "LOGRADOURO")
    private String logradouro;
    @Column(name = "NUMERO")
    private String numero;
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @Column(name = "CIDADE")
    private String cidade;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "TIPO")
//    private Tipo tipo;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "ECOSSISTEMA")
//    private Ecossistema ecossistema;

    @Enumerated(EnumType.STRING)
    @Column(name = "ATIVO")
    private Ativo ativo;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "ENDERECO_CLIENTE",
            joinColumns = @JoinColumn(name = "ID_ENDERECO"),
            inverseJoinColumns = @JoinColumn(name = "ID_CLIENTE"))
    private List<Cliente> clientes;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "endereco", orphanRemoval = true)
    private List<Entrega> entregas;

}