package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EspecialistaDTO  {

    private int idEspecialista;

    private int id;

    private String nome;

    private String especializacao;

    private Estados regiaoResponsavel;

}
