package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class LogContaUsuario {
    @Id
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    private Integer quantidade;
}
