package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import lombok.*;

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
    @Column(name = "ID_ESTADO")
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
    @Column(name = "TIPO")
    private Tipo tipo;
    @Column(name = "ECOSSISTEMA")
    private Ecossistema ecossistema;
    @Column(name = "ATIVO")
    private Ativo ativo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "ENDERECO_CLIENTE",
            joinColumns = @JoinColumn(name = "ID_ENDERECO"),
            inverseJoinColumns = @JoinColumn(name = "ID_CLIENTE"))
    private List<Cliente> clientes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "endereco", orphanRemoval = true)
    private List<Entrega> entregas;



}