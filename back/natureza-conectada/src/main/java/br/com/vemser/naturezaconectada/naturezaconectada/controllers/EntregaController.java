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
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/entrega")
@Validated
@Slf4j
public class EntregaController implements IEntregaController {

    private final ServiceEntrega entregaService;

    @PostMapping("/{idEndereco}")
    public ResponseEntity<EntregaResponseDTO> adicionar(@Valid @RequestBody EntregaRequestDTO entregaRequestDTO, @PathVariable("idEndereco") int idEndereco) throws Exception {
        log.info("Criando entrega...");
        return new ResponseEntity<>(entregaService.adicionar(entregaRequestDTO, idEndereco), HttpStatus.CREATED);
    }

    @PutMapping("/{idEntrega}")
    public ResponseEntity<EntregaResponseDTO> editarMudasDaEntrega(@PathVariable("idEntrega") int id, @Valid @RequestBody EntregaRequestDTO entregaRequestDTO) throws Exception {
        log.info("Atualizando entrega...");
        return ResponseEntity.ok().body(entregaService.editarMudasEntrega(id, entregaRequestDTO));
    }
    @PutMapping("/status/{idEntrega}")
    public ResponseEntity<EntregaResponseDTO> editarStatus(@PathVariable("idEntrega") int id, @Valid @RequestParam String status) throws Exception {
        log.info("Atualizando entrega...");
        return ResponseEntity.ok().body(entregaService.mudarStatusEntrega(id, status));
    }

    @GetMapping
    public ResponseEntity<List<EntregaResponseDTO>> listarTodos() throws Exception {
        return ResponseEntity.ok().body(entregaService.listarTodos());
    }

    @GetMapping("/{idEntrega}")
    public ResponseEntity<EntregaResponseDTO> procurarPorId(@PathVariable int idEntrega) throws Exception {
        return ResponseEntity.ok().body(entregaService.procurarPorID(idEntrega));
    }

    @DeleteMapping("/{idEntrega}")
    public ResponseEntity<Void> deletar(@PathVariable("idEntrega") int id) throws Exception {
        log.info("Deletando entrega...");
        entregaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}

