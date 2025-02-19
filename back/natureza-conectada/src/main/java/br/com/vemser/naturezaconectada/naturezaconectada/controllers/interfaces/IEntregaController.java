package br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EntregaRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EntregaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface IEntregaController {

    @Operation(summary = "Adicionar entrega", description = "Adiciona uma nova entrega")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Entrega adicionada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idEndereco}")
    ResponseEntity<EntregaResponseDTO> adicionar(@Valid @RequestBody EntregaRequestDTO entregaRequestDTO, @NotNull @PathVariable("idEndereco") Integer idEndereco) throws Exception;

    @Operation(summary = "Editar o Status da  entrega por ID", description = "Edita o status de uma entrega pelo ID especificado , Possibilidade de Status( RECEBIDO,ENVIADO ou ENTREGUE)")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Entrega editada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Entrega não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    public ResponseEntity<EntregaResponseDTO> editarStatus(@PathVariable("idEntrega") Integer id, @Valid @RequestParam String status) throws Exception;
    @Operation(summary = "Listar todas as entregas", description = "Lista todas as entregas cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista de entregas obtida com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<EntregaResponseDTO>> listarTodos() throws Exception;

    @Operation(summary = "Procurar entrega por ID", description = "Procura uma entrega pelo ID especificado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Entrega encontrada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Entrega não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEntrega}")
    ResponseEntity<EntregaResponseDTO> procurarPorId(@PathVariable Integer idEntrega) throws Exception;

}

