package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.RelatorioRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceRelatorioMuda;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relatorio")
@RequiredArgsConstructor
public class RelatorioController {

    private final ServiceRelatorioMuda serviceRelatorioMuda;

    @PostMapping
    public RelatorioClienteDTO adicionar (RelatorioRequestDTO dto) throws Exception {
        return this.serviceRelatorioMuda.adicionar(dto);
    }

    @GetMapping("/espec/{idEspecialista}")
    public void relatorioEspecialista(){}

    @GetMapping("/aberto")
    public void  buscarRelatorioAbertos(){}

    @PutMapping("/avaliacao")
    public void avaliarRelatorio(){}
    @GetMapping()
    public void listarTodos(){}

}
