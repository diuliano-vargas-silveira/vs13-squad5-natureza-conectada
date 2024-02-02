package br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EspecialistaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EspecialistaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface IEspecialistaController {


    @Operation(summary = "Listar Especialistas", description = "Lista todas os especialistas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de especialistas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "404", description = "não há especialistas no banco de dados")
            }
    )
    @GetMapping
    public ResponseEntity<List<EspecialistaDTO>> listarTodos() throws Exception;

    @Operation(summary = "Listar Especialistas por ID ", description = "Lista um especialista com base no id do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um especialistas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "404", description = "não há especialista com este Id no banco de dados")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EspecialistaCreateDTO> buscarPorId(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Criar novo Especialista", description = "Cria um novo especialista do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o especialista criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "Lança uma exceção de quebra de regra de negocio")
            }
    )
    @PostMapping
    public ResponseEntity<EspecialistaCreateDTO> novoEspecialista(@RequestBody @Valid EspecialistaCreateDTO dto) throws Exception;

    @Operation(summary = "Atualiza um Especialista", description = "Busca um Especialista por ID e atualiza")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o especialista Atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "Lança uma exceção de quebra de regra de negocio")
            }
    )
    @PutMapping("/{idEspecialista}")
    public ResponseEntity<EspecialistaCreateDTO> atualizarEspecialista(@PathVariable Integer idEspecialista, @RequestBody @Valid EspecialistaCreateDTO dto) throws Exception;
}