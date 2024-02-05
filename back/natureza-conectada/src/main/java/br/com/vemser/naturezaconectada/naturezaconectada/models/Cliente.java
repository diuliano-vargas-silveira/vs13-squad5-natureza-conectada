package br.com.vemser.naturezaconectada.naturezaconectada.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("CLIENTE")
@Entity(name = "Cliente")
@Table(name = "CLIENTE")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Cliente extends Usuario {

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "ENDERECO_CLIENTE",
            joinColumns = @JoinColumn(name = "ID_CLIENTE"),
            inverseJoinColumns = @JoinColumn(name = "ID_ENDERECO"))
    private List<Endereco> enderecos;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
    private List<Contato> contatos;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "CLIENTE_MUDA",
            joinColumns = @JoinColumn(name = "ID_CLIENTE"),
            inverseJoinColumns = @JoinColumn(name = "ID_MUDA"))
    private List<Muda> mudas;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
    private List<Entrega> entregas;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
    private List<Relatorio> relatorios;
}