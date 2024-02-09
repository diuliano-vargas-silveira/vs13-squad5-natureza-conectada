package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IClienteController;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ClienteCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceCliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ClienteController implements IClienteController {

    private final ServiceCliente serviceCliente;

    @PostMapping
    public ResponseEntity<ClienteDTO> adicionar(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO) throws Exception {
        log.debug("Criando cliente");

        ClienteDTO cliente = serviceCliente.adicionar(clienteCreateDTO);
        log.debug("Contato cliente");

        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    //TODO - Buscar usuário logado
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> editar(@PathVariable("id") Integer id, @Valid @RequestBody ClienteCreateDTO cliente) throws Exception {
        var clienteEditado = serviceCliente.editar(id, cliente);

        return new ResponseEntity<>(clienteEditado, HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Void> desativarCliente(@PathVariable("id") Integer id) throws Exception {
        serviceCliente.remover(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> listarClientes(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) throws Exception {

        return ResponseEntity.ok().body(serviceCliente.listarTodos(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> procurarPorId(@PathVariable("id") Integer id) throws Exception {
        log.debug("Procurando contato");
        var cliente = serviceCliente.procurarPorId(id);


        log.debug("Contato encontrado");
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ClienteDTO>> procurarClienteAtivos() {
        var clientesAtivos = serviceCliente.listarClientesAtivos();

        return new ResponseEntity<>(clientesAtivos, HttpStatus.OK);
    }

}
