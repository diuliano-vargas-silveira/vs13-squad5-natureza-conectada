package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IAdminController;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.AdminRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.AdminResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceAdmin;
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
@RequestMapping("/admin")
@Validated
@Slf4j
public class AdminController implements IAdminController {

    private final ServiceAdmin adminService;

    @PostMapping
    public ResponseEntity<AdminResponseDTO> adicionar(@Valid @RequestBody AdminRequestDTO adminRequestDTO) throws java.lang.Exception {
        log.info("Criando admin...");
        return new ResponseEntity<>(adminService.adicionar(adminRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> listarTodos() throws Exception {
        return ResponseEntity.ok().body(adminService.listarTodos());
    }

    @GetMapping("/{idAdmin}")
    public ResponseEntity<AdminResponseDTO> procurarPorId(@PathVariable int idAdmin) throws Exception {
        return ResponseEntity.ok().body(adminService.procurarPorId(idAdmin));
    }

    @PutMapping("/{idAdmin}")
    public ResponseEntity<AdminResponseDTO> editar(@PathVariable("idAdmin") int id, @Valid @RequestBody AdminRequestDTO adminRequestDTO) throws java.lang.Exception {
        log.info("Atualizando admin...");
        return ResponseEntity.ok().body(adminService.editar(id, adminRequestDTO));
    }

    @PutMapping("/{idAdmin}/status")
    public ResponseEntity<AdminResponseDTO> alterarStatus(@PathVariable("idAdmin") int id, @RequestBody AdminRequestDTO adminRequestDTO) throws java.lang.Exception {
        log.info("Alterando status do admin...");
        return ResponseEntity.ok().body(adminService.alterarStatusAtivo(id, adminRequestDTO));
    }
}

