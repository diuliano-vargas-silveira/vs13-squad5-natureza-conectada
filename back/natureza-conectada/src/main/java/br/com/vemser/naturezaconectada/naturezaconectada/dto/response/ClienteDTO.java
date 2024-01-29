package br.com.vemser.naturezaconectada.naturezaconectada.dto.response;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDTO extends UsuarioResponseDTO {

    private Integer idCliente;

    private String cpf;
    private Ativo ativo;
    private List<Endereco> enderecos = new ArrayList<>();

    private List<Contato> contatos = new ArrayList<>();


    private List<Muda> mudas = new ArrayList<>();

@JsonIgnore
    private List<Entrega> entregas = new ArrayList<>();
}
