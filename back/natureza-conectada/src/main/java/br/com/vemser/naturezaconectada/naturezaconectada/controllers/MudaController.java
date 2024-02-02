//package br.com.vemser.naturezaconectada.naturezaconectada.controllers;
//
//import br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces.IMudaController;
//import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.MudaCreateDTO;
//import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.MudaDTO;
//import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
//import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
//import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceMudas;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/mudas")
//@RequiredArgsConstructor
//public class MudaController implements IMudaController {
//
//    private final ServiceMudas serviceMudas;
//
//    @GetMapping
//    public List<MudaDTO> listarMudas() throws Exception {
//        return this.serviceMudas.listarMudas();
//    }
//    @GetMapping("/ativas")
//    public List<MudaDTO> listarAtivas() throws Exception {
//        return this.serviceMudas.listarMudasAtivas();
//    }
//    @GetMapping("/{idMuda}")
//    public ResponseEntity<MudaDTO> buscarPorId(@PathVariable Integer idMuda) throws Exception {
//        return new ResponseEntity<>(this.serviceMudas.buscarPorId(idMuda), HttpStatus.OK);
//    }
//    @GetMapping("/buscar")
//    public ResponseEntity<MudaDTO> buscarPorEco(@RequestParam Ecossistema eco) throws Exception {
//        return new ResponseEntity<>(this.serviceMudas.buscarPorEco(eco), HttpStatus.OK);
//    }
//    @PostMapping
//    public ResponseEntity<MudaCreateDTO> novaMuda(@RequestBody MudaCreateDTO dto) throws Exception {
//       return new ResponseEntity<>(this.serviceMudas.novaMuda(dto),HttpStatus.OK);
//    }
//    @PutMapping("/{idMuda}")
//    public ResponseEntity<MudaCreateDTO> atualizarMuda(@PathVariable Integer idMuda, @RequestBody MudaCreateDTO dto) throws Exception {
//        return new ResponseEntity<>(this.serviceMudas.editarmuda(idMuda,dto),HttpStatus.OK);
//
//    }
//    @DeleteMapping("/{idMuda}")
//    public ResponseEntity<Void> mudarAtivoMuda(@PathVariable Integer idMuda, @RequestParam Ativo ativo) throws Exception {
//        this.serviceMudas.mudarAtivoMuda(idMuda,ativo);
//
//        return ResponseEntity.ok().build();
//
//    }
//}
