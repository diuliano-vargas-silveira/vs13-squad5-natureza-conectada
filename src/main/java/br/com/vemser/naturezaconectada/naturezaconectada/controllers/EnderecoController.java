package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IEnderecoController;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EnderecoCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EnderecoDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceEndereco;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/endereco")
@Validated
@Slf4j
public class EnderecoController implements IEnderecoController {

    private final ServiceEndereco serviceEndereco;

    @PostMapping("/{idCliente}")
    public ResponseEntity<EnderecoDTO> adicionar(@PathVariable("idCliente") Integer idCliente, @Valid @RequestBody EnderecoCreateDTO endereco) throws Exception {
        log.debug("Criando endereço");

        var enderecoCriado = serviceEndereco.adicionar(endereco, idCliente);

        // email pro cliente

        log.debug("Endereço criado");
        return new ResponseEntity<>(enderecoCriado, HttpStatus.CREATED);
    }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> editar(@PathVariable("idEndereco") Integer idEndereco, @Valid @RequestBody EnderecoCreateDTO endereco) throws Exception {
        log.debug("Atualizando endereço");

        var enderecoAtualizado = serviceEndereco.editar(idEndereco, endereco);

        log.debug("Endereço atualizado");

        // email pro cliente

        return new ResponseEntity<>(enderecoAtualizado, HttpStatus.OK);
    }

    @PutMapping("/remover/{idEndereco}")
    public ResponseEntity<Void> remover(@PathVariable("idEndereco") Integer idEndereco) throws Exception {
        log.debug("Excluindo endereço");

        serviceEndereco.remover(idEndereco);

        log.debug("Endereço excluído");

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarTodos() throws Exception {
        var enderecos = serviceEndereco.listarTodos();

        if (enderecos == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(enderecos, HttpStatus.OK);
    }

    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> procurarPorIdEndereco(@PathVariable("idEndereco") Integer idEndereco) throws Exception {
        var endereco = serviceEndereco.procurarPorIdEndereco(idEndereco);

        if (endereco == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        log.debug("Endereço encontrado");

        return new ResponseEntity<>(endereco, HttpStatus.OK);
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<EnderecoDTO> ativarEndereco(@PathVariable("id") Integer id, @RequestParam String eco) throws Exception {
        log.debug("Atualizando endereço");

        EnderecoDTO enderecoAtualizado = serviceEndereco.ativarEndereco(id, eco);

        log.debug("Endereço atualizado");

        // email pro cliente

        return new ResponseEntity<>(enderecoAtualizado, HttpStatus.OK);
    }

    @GetMapping("/ativos")
    public List<EnderecoDTO> listarEnderecosPorAtivo() throws Exception {
        return serviceEndereco.listarEnderecosPorAtivo();
    }
}