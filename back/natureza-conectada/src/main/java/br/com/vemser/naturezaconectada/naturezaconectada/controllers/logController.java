package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.LogMudasCriadasDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.LogUsuarioDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.models.LogContaUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.models.LogMudasCriadas;
import br.com.vemser.naturezaconectada.naturezaconectada.models.LogUsuarios;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceLog;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class logController {
    private final ServiceLog log;

    @PostMapping
    public ResponseEntity<Void> criarLogNovoUsuario(LogUsuarios log){
        this.log.criarLogUsuario(log);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/novaMuda")
    public ResponseEntity<Void> criarLogNovaMuda(LogMudasCriadas log){
        this.log.criarLogMudas(log);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<LogUsuarioDTO>> totalPorTipo(){
        return new ResponseEntity<>(this.log.totalDeUsuarioPorTipo(), HttpStatus.OK);
    }
    @GetMapping("admin")
    public ResponseEntity<List<LogMudasCriadasDTO>> totalPorTipo(@RequestParam("nome")String nome){
        return new ResponseEntity<>(this.log.listarMudasCriadasPorNome(nome),HttpStatus.OK);
    }
}
