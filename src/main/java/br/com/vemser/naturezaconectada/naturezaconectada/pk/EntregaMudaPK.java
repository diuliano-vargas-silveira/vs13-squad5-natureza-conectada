package br.com.vemser.naturezaconectada.naturezaconectada.pk;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class EntregaMudaPK implements Serializable {

    @Column(name="ID_ENTREGA")
    private Integer idEntrega;

    @Column(name="ID_MUDA")
    private Integer idMuda;

}
