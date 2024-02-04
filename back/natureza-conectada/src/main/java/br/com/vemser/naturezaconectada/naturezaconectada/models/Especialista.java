package br.com.vemser.naturezaconectada.naturezaconectada.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Especialista")
@Table(name = "ESPECIALISTA")
@DiscriminatorValue("ESPECIALISTA")
public class Especialista extends Usuario {

    @Column(name = "DOCUMENTO")
    private String documento;
    @Column(name = "ESPECIALIZACAO")
    private String especializacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "especialista", orphanRemoval = true)
    private List<Relatorio> relatorios;

}
