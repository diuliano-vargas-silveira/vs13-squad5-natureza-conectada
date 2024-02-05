package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.pk.EntregaMudaPK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity(name = "EntregaMuda")
@Table(name = "ENTREGA_MUDA")
public class EntregaMuda {

    @EmbeddedId
    private EntregaMudaPK entregaPK;

    @Column(name = "QUANTIDADE")
    private Integer quantidade;
}
