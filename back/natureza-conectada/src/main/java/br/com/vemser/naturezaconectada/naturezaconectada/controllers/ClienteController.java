package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IClienteController;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ClienteRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoEmail;
import br.com.vemser.naturezaconectada.naturezaconectada.services.EmailService;
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
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> adicionar(@Valid @RequestBody ClienteRequestDTO clienteCreateDTO) throws Exception {
        log.debug("Criando cliente");

        ClienteResponseDTO cliente = serviceCliente.adicionar(clienteCreateDTO);
        log.debug("Contato cliente");

        emailCreate(clienteCreateDTO);

        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> editar(@PathVariable("id") Integer id, @Valid @RequestBody ClienteRequestDTO cliente) throws Exception {
        var clienteEditado = serviceCliente.editar(id, cliente);

        return new ResponseEntity<>(clienteEditado, HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Void> desativarCliente(@PathVariable("id") Integer id) throws Exception {
        serviceCliente.remover(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> listarClientes(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) throws Exception {

        return ResponseEntity.ok().body(serviceCliente.listarTodos(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> procurarPorId(@PathVariable("id") Integer id) throws Exception {
        log.debug("Procurando contato");
        var cliente = serviceCliente.procurarPorId(id);


        log.debug("Contato encontrado");
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ClienteResponseDTO>> procurarClienteAtivos() {
        var clientesAtivos = serviceCliente.listarClientesAtivos();

        return new ResponseEntity<>(clientesAtivos, HttpStatus.OK);
    }

    @GetMapping("/email")
    public void emailCreate(ClienteRequestDTO clienteCriado) throws Exception {
        emailService.sendEmail(clienteCriado, TipoEmail.CRIACAO);

        log.info("E-mail enviado!");

    }

}
