package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IEnderecoController;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EnderecoCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EnderecoDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
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
    public ResponseEntity<EnderecoDTO> atualizar(@PathVariable("idEndereco") Integer idEndereco, @Valid @RequestBody EnderecoCreateDTO endereco) throws Exception {
        log.debug("Atualizando endereço");

        var enderecoAtualizado = serviceEndereco.editar(idEndereco, endereco);

        log.debug("Endereço atualizado");

        // email pro cliente

        return new ResponseEntity<>(enderecoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> remover(@PathVariable("idEndereco") Integer idEndereco) throws Exception {
        log.debug("Excluindo endereço");

        var enderecoEncontrado = serviceEndereco.procurarPorIdEndereco(idEndereco);

        if (enderecoEncontrado == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        serviceEndereco.deletar(idEndereco);

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

    @GetMapping("/{idCliente}/cliente")
    public ResponseEntity<List<EnderecoDTO>> procurarPorIdCliente(@PathVariable("idCliente") Integer idCliente) throws Exception {
        var enderecos = serviceEndereco.procurarEnderecoPorIdCliente(idCliente);


        if (enderecos == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(enderecos, HttpStatus.OK);
    }
    @PutMapping("/ativar/{idEndereco}")
    public ResponseEntity<EnderecoDTO> ativarEndereco(@PathVariable("idEndereco") Integer idEndereco, @RequestParam String eco) throws Exception {
        log.debug("Atualizando endereço");

        EnderecoDTO enderecoAtualizado = serviceEndereco.ativarEndereco(idEndereco,eco);

        log.debug("Endereço atualizado");

        // email pro cliente

        return new ResponseEntity<>(enderecoAtualizado, HttpStatus.OK);
    }
}