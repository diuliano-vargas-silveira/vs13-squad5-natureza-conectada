package br.com.vemser.naturezaconectada.naturezaconectada.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("CLIENTE")
@Entity(name = "Cliente")
@Table(name = "CLIENTE")
public class Cliente extends Usuario {
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "ENDERECO_CLIENTE",
            joinColumns = @JoinColumn(name = "ID_CLIENTE"),
            inverseJoinColumns = @JoinColumn(name = "ID_ENDERECO"))
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
    private List<Contato> contatos = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "CLIENTE_MUDA",
            joinColumns = @JoinColumn(name = "ID_CLIENTE"),
            inverseJoinColumns = @JoinColumn(name = "ID_MUDA"))
    private List<Muda> mudas;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Entrega> entregas;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<RelatorioMuda> relatorios;

}