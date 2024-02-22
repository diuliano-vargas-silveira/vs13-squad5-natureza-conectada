package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IEspecialistaController;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EspecialistaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EspecialistaDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceEspecialista;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/especialista")
@RequiredArgsConstructor
public class EspecialistaController implements IEspecialistaController {

    private final ServiceEspecialista serviceEspecialista;

    @GetMapping
    public ResponseEntity<List<EspecialistaDTO>> listarTodos() throws Exception {
        return new ResponseEntity<>(serviceEspecialista.listarTodos(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EspecialistaCreateDTO> buscarPorIdCompleto(@PathVariable  Integer id) throws Exception {
        return new ResponseEntity<>(serviceEspecialista.procurarPorIdCompleto(id), HttpStatus.OK);
    }
    @GetMapping("/admin/{id}")
    public ResponseEntity<EspecialistaDTO> buscarPorId(@PathVariable  Integer id) throws Exception {
        return new ResponseEntity<>(serviceEspecialista.procurarPorIdBasica(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EspecialistaCreateDTO> novoEspecialista(@RequestBody @Valid EspecialistaCreateDTO dto) throws Exception {
        return new ResponseEntity<>(this.serviceEspecialista.adicionar(dto),HttpStatus.CREATED);
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<Void> mudarAtivoEspecialista(@PathVariable Integer id) throws Exception {
        this.serviceEspecialista.mudarAtivoEspecialista(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idEspecialista}")
    public ResponseEntity<EspecialistaCreateDTO> atualizarEspecialista (@PathVariable Integer idEspecialista,@RequestBody @Valid EspecialistaDTO dto) throws Exception {
        return new ResponseEntity<>(this.serviceEspecialista.editar(idEspecialista,dto),HttpStatus.OK);
    }

}