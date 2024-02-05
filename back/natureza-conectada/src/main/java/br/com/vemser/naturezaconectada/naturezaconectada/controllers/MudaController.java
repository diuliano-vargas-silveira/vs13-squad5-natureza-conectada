package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IMudaController;
//import br.com.vemser.naturezaconectada.naturezaconectada.dto.relatorios.RelatorioMudasDoadas;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.relatorios.RelatorioMudasDoadas;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.MudaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.MudaDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceMudas;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/mudas")
@RequiredArgsConstructor
public class MudaController implements IMudaController {

    private final ServiceMudas serviceMudas;

    @GetMapping
    public ResponseEntity<Page<MudaCreateDTO>> listarMudas(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) throws Exception {
        return new ResponseEntity<>(this.serviceMudas.listarTodasMudas(paginacao), HttpStatus.OK);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<MudaDTO>> listarAtivas() throws Exception {
        return new ResponseEntity<>(this.serviceMudas.listarMudasAtivas(), HttpStatus.OK);
    }

    @GetMapping("/{idMuda}")
    public ResponseEntity<MudaDTO> buscarPorId(@PathVariable Integer idMuda) throws Exception {
        return new ResponseEntity<>(this.serviceMudas.procurarPorIdDto(idMuda), HttpStatus.OK);
    }

    @GetMapping("/eco")
    public ResponseEntity<List<MudaDTO>> buscarPorEco(@RequestParam Ecossistema eco) throws Exception {
        return new ResponseEntity<>(this.serviceMudas.buscarPorEco(eco), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MudaCreateDTO> novaMuda(@Valid @RequestBody MudaCreateDTO dto) throws Exception {
        return new ResponseEntity<>(this.serviceMudas.novaMuda(dto), HttpStatus.OK);
    }

    @PutMapping("/{idMuda}")
    public ResponseEntity<MudaCreateDTO> atualizarMuda(@PathVariable Integer idMuda, @Valid @RequestBody MudaCreateDTO dto) throws Exception {
        return new ResponseEntity<>(this.serviceMudas.editarmuda(idMuda, dto), HttpStatus.OK);

    }

    @DeleteMapping("/{idMuda}")
    public ResponseEntity<Void> mudarAtivoMuda(@PathVariable Integer idMuda) throws Exception {
        this.serviceMudas.mudarAtivoMuda(idMuda);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/doacoes")
    public List<RelatorioMudasDoadas> mudasDoadas() {
        return this.serviceMudas.mudasDoadas();
    }
}