package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IContatoController;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ContatoCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ContatoDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceContato;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/contato")
@Validated
@Slf4j
public class ContatoController {

    private final ServiceContato serviceContato;

    public ContatoController(ServiceContato serviceContato) {
        this.serviceContato = serviceContato;
    }

    @PostMapping("/{idCliente}")
    public ResponseEntity<ContatoDTO> adicionar(@PathVariable("idCliente") Integer idCliente, @Valid @RequestBody ContatoCreateDTO contato) throws Exception {
        log.debug("Criando contato");

        ContatoDTO contatoCriado = serviceContato.adicionar(contato, idCliente);
        log.debug("Contato criado");

        return new ResponseEntity<>(contatoCriado, HttpStatus.CREATED);
    }

    @PutMapping("/{idContato}")
    public ResponseEntity<ContatoDTO> editar(@PathVariable("idContato") Integer idContato, @Valid @RequestBody ContatoCreateDTO contatoAtualizado) throws Exception {
        log.debug("Contato atualizado");
        return new ResponseEntity<>(serviceContato.editar(idContato, contatoAtualizado), HttpStatus.OK);
    }

    @DeleteMapping("/{idContato}")
    public ResponseEntity<Void> remover(@PathVariable("idContato") Integer idContato) throws Exception {
        serviceContato.remover(idContato);
        log.debug("Contato excluído");
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ContatoDTO>> listarTodos() throws SQLException {
        var contatos = serviceContato.listarTodos();

        if (contatos == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(contatos, HttpStatus.OK);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<List<ContatoDTO>> procurarPorIdCliente(@PathVariable("idCliente") Integer idCliente) throws Exception {
        log.debug("Procurando contato");
        var contatos = serviceContato.procurarPorIdCliente(idCliente);

        if (contatos == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        log.debug("Contato encontrado");
        return new ResponseEntity<>(contatos, HttpStatus.OK);
    }
}