package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.AvaliacaoDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.RelatorioRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioResponseAdmin;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioResponseEspecialista;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceRelatorioMuda;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/relatorio")
@RequiredArgsConstructor
public class RelatorioController {

    private final ServiceRelatorioMuda serviceRelatorioMuda;

    @PostMapping
    public ResponseEntity<RelatorioClienteDTO> adicionar(@Valid @RequestBody RelatorioRequestDTO dto) throws Exception {
        return new ResponseEntity<>(this.serviceRelatorioMuda.adicionar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/espec/{idEspecialista}")
    public ResponseEntity<List<RelatorioResponseEspecialista>> relatorioEspecialista(@PathVariable Integer idEspecialista) throws InformacaoNaoEncontrada {
        return ResponseEntity.ok().body(this.serviceRelatorioMuda.relatorioEspecialista(idEspecialista));
    }

    @GetMapping("/aberto")
    public ResponseEntity<List<RelatorioResponseEspecialista>> buscarRelatorioAbertos() {
        return ResponseEntity.ok().body(this.serviceRelatorioMuda.buscarAbertos());
    }

    @PutMapping("/avaliacao/{idRelatorio}")
    public ResponseEntity<Void> avaliarRelatorio(@PathVariable Integer idRelatorio, @RequestBody @Valid AvaliacaoDTO dto) throws Exception {
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<RelatorioResponseAdmin>> listarTodos() {
        return ResponseEntity.ok().body(this.serviceRelatorioMuda.listar());
    }

    @PutMapping("/{idRelatorio}")
    public ResponseEntity<RelatorioClienteDTO> editarRelatorio(@PathVariable Integer idRelatorio, @RequestBody @Valid RelatorioRequestDTO dto) throws Exception {
        return ResponseEntity.ok().body(this.serviceRelatorioMuda.editarRelatorio(idRelatorio, dto));
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<RelatorioClienteDTO> relatorioCliente(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok().body(this.serviceRelatorioMuda.buscarRelatorio(id));
    }

}
