package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IUsuarioController;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceUsuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
@Validated
@Slf4j
public class UsuarioController implements IUsuarioController {

    private final ServiceUsuario usuarioService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> logar(@RequestParam String email, @RequestParam String senha) throws Exception {
        return ResponseEntity.ok().body(usuarioService.logar(email, senha));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() throws Exception {
        return ResponseEntity.ok().body(usuarioService.listarTodos());
    }

    @GetMapping("/ativo")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuariosAtivos() throws Exception {
        return ResponseEntity.ok().body(usuarioService.listarUsuariosAtivos());
    }

    @GetMapping("/email")
    public ResponseEntity<UsuarioResponseDTO> procurarPorEmail(@RequestParam String email) throws Exception {
        return ResponseEntity.ok().body(usuarioService.procurarPorEmail(email));
    }

}
