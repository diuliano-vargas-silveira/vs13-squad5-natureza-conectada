package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteResponseDTO extends UsuarioResponseDTO {

    private String cpf;

    private Ativo ativo;

    private List<Endereco> enderecos = new ArrayList<>();

    private List<Contato> contatos = new ArrayList<>();

    @JsonIgnore
    private List<Muda> mudas;

    @JsonIgnore
    private List<Entrega> entregas;
}
