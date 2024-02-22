package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IContatoController;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ContatoRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ContatoResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceContato;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Validated
@Slf4j
public class ContatoController implements IContatoController {

    private final ServiceContato serviceContato;

    @PostMapping("/{idCliente}")
    public ResponseEntity<ContatoResponseDTO> adicionar(@PathVariable("idCliente") Integer idCliente, @Valid @RequestBody ContatoRequestDTO contato) throws Exception {
        log.debug("Criando contato");

        ContatoResponseDTO contatoCriado = serviceContato.adicionar(contato, idCliente);
        log.debug("Contato criado");

        return new ResponseEntity<>(contatoCriado, HttpStatus.CREATED);
    }

    @PutMapping("/{idContato}")
    public ResponseEntity<ContatoResponseDTO> editar(@PathVariable("idContato") Integer idContato, @Valid @RequestBody ContatoRequestDTO contatoAtualizado) throws Exception {
        log.debug("Contato atualizado");
        return new ResponseEntity<>(serviceContato.editar(idContato, contatoAtualizado), HttpStatus.OK);
    }

    @PutMapping("/remover/{idContato}")
    public ResponseEntity<Void> remover(@PathVariable("idContato") Integer idContato) throws Exception {
        serviceContato.remover(idContato);
        log.debug("Contato excluído");
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ContatoResponseDTO>> listarTodos() throws SQLException {
        var contatos = serviceContato.listarTodos();

        if (contatos == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(contatos, HttpStatus.OK);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<List<ContatoResponseDTO>> procurarPorIdCliente(@PathVariable("idCliente") Integer idCliente) throws Exception {
        log.debug("Procurando contato");
        var contatos = serviceContato.procurarPorIdCliente(idCliente);

        if (contatos == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        log.debug("Contato encontrado");
        return new ResponseEntity<>(contatos, HttpStatus.OK);
    }
}