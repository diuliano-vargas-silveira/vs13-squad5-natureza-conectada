package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ClienteCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@Validated
@Slf4j
public class ClienteController {

    private final ServiceCliente serviceCliente;


    public ClienteController(ServiceCliente serviceCliente) {
        this.serviceCliente = serviceCliente;
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> adicionar(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO) throws Exception {
        log.debug("Criando cliente");

        ClienteDTO cliente = serviceCliente.adicionar(clienteCreateDTO);
        log.debug("Contato cliente");

        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() throws Exception {
        return new ResponseEntity<>(serviceCliente.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> procurarPorIdCliente(@PathVariable("idCliente") Integer idCliente) throws Exception {
        log.debug("Procurando contato");
        var cliente = serviceCliente.procurarPorIdCliente(idCliente);


        log.debug("Contato encontrado");
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
}
