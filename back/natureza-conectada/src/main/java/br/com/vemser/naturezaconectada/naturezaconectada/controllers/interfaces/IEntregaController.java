package br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EntregaRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EntregaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    ResponseEntity<EntregaResponseDTO> adicionar(@Valid @RequestBody EntregaRequestDTO entregaRequestDTO, @PathVariable("idEndereco") int idEndereco) throws Exception;

    @Operation(summary = "Editar entrega por ID", description = "Edita uma entrega pelo ID especificado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Entrega editada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Entrega não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idEntrega}")
    ResponseEntity<EntregaResponseDTO> editar(@PathVariable("idEntrega") int id, @Valid @RequestBody EntregaRequestDTO entregaRequestDTO) throws Exception;

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
    ResponseEntity<EntregaResponseDTO> procurarPorId(@PathVariable int idEntrega) throws Exception;

    @Operation(summary = "Deletar entrega por ID", description = "Deleta uma entrega pelo ID especificado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Entrega deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Entrega não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idEntrega}")
    ResponseEntity<Void> deletar(@PathVariable("idEntrega") int id) throws Exception;
}

