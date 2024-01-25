package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IUsuarioController;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.UsuarioRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceUsuario;
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
@RequestMapping("/usuario")
@Validated
@Slf4j
public class UsuarioController implements IUsuarioController {

    private final ServiceUsuario usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> adicionarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        log.info("Criando usuario...");
        return new ResponseEntity<>(usuarioService.adicionarUsuario(usuarioRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> logar(@RequestParam String email, @RequestParam String senha) throws Exception {
        return ResponseEntity.ok().body(usuarioService.logar(email, senha));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() throws java.lang.Exception {
        return ResponseEntity.ok().body(usuarioService.listarTodos());
    }

    @GetMapping("/email")
    public ResponseEntity<UsuarioResponseDTO> procurarPorEmail(@RequestParam String email) throws Exception {
        return ResponseEntity.ok().body(usuarioService.procurarPorEmail(email));
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> editar(@PathVariable("idUsuario") int id, @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        log.info("Atualizando usuario...");
        return ResponseEntity.ok().body(usuarioService.editar(id, usuarioRequestDTO));
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deletar(@PathVariable("idUsuario") int id) throws Exception {
        log.info("Deletando usuario...");
        usuarioService.remover(id);
        return ResponseEntity.ok().build();
    }
}
