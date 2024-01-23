package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.BancoDeDadosException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceEspecialista;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/especialista")
public class EspecialistaController {

    ServiceEspecialista serviceEspecialista;

    public EspecialistaController(ServiceEspecialista serviceEspecialista) {
        this.serviceEspecialista = serviceEspecialista;
    }

    @GetMapping
    public ResponseEntity<List<Especialista>> getAll() throws BancoDeDadosException {
        return new ResponseEntity<>(serviceEspecialista.listarTodos(), HttpStatus.OK);
    }
}