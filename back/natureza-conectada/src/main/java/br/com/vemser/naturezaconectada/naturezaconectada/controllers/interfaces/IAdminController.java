package br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.AdminRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.AdminResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.Exception;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface IAdminController {

    @Operation(summary = "Adicionar administrador", description = "Adiciona um novo administrador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Administrador adicionado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<AdminResponseDTO> adicionar(@Valid @RequestBody AdminRequestDTO adminRequestDTO) throws java.lang.Exception;

    @Operation(summary = "Listar todos os administradores", description = "Lista todos os administradores cadastrados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista de administradores obtida com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<AdminResponseDTO>> listarTodos() throws Exception;

    @Operation(summary = "Procurar administrador por ID", description = "Procura um administrador pelo ID especificado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Administrador encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Administrador não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idAdmin}")
    ResponseEntity<AdminResponseDTO> procurarPorId(@PathVariable int idAdmin) throws Exception;

    @Operation(summary = "Editar administrador por ID", description = "Edita um administrador pelo ID especificado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Administrador editado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Administrador não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idAdmin}")
    ResponseEntity<AdminResponseDTO> editar(@PathVariable("idAdmin") int id, @Valid @RequestBody AdminRequestDTO adminRequestDTO) throws java.lang.Exception;

    @Operation(summary = "Deletar administrador por ID", description = "Deleta um administrador pelo ID especificado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Administrador deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Administrador não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idAdmin}")
    ResponseEntity<Void> deletar(@PathVariable("idAdmin") int id) throws java.lang.Exception;
}
