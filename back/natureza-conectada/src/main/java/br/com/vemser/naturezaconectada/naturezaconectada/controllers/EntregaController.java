package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IEntregaController;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EntregaRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EntregaResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceEntrega;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/entrega")
@Validated
@Slf4j
public class EntregaController implements IEntregaController {

    private final ServiceEntrega entregaService;

    @PostMapping("/{idEndereco}")
    public ResponseEntity<EntregaResponseDTO> adicionar(@Valid @RequestBody EntregaRequestDTO entregaRequestDTO, @NotNull @PathVariable("idEndereco") Integer idEndereco) throws Exception {
        log.info("Criando entrega...");
        return new ResponseEntity<>(entregaService.adicionar(entregaRequestDTO, idEndereco), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EntregaResponseDTO>> listarTodos() throws Exception {
        return ResponseEntity.ok().body(entregaService.listarTodos());
    }

    @GetMapping("/{idEntrega}")
    public ResponseEntity<EntregaResponseDTO> procurarPorId(@PathVariable Integer idEntrega) throws Exception {
        return ResponseEntity.ok().body(entregaService.procurarPorId(idEntrega));
    }

    @PutMapping("/{idEntrega}")
    public ResponseEntity<EntregaResponseDTO> editarMudasDaEntrega(@PathVariable("idEntrega") Integer id, @Valid @RequestBody EntregaRequestDTO entregaRequestDTO) throws Exception {
        log.info("Atualizando entrega...");
        return ResponseEntity.ok().body(entregaService.editarMudasEntrega(id, entregaRequestDTO));
    }

    @PutMapping("/status/{idEntrega}")
    public ResponseEntity<EntregaResponseDTO> editarStatus(@PathVariable("idEntrega") Integer id, @Valid @RequestParam String status) throws Exception {
        log.info("Atualizando entrega...");
        return ResponseEntity.ok().body(entregaService.mudarStatusEntrega(id, status));
    }
}

