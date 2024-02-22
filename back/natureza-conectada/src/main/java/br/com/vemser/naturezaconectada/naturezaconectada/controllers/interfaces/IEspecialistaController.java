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

    @Operation(summary = "Listar Especialista Completo por ID ", description = "Este metódo retorna os dados pessoais do Especialista, então so pode ser usado por ele")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um especialista"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "404", description = "não há especialista com este Id no banco de dados")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EspecialistaCreateDTO> buscarPorIdCompleto(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Listar Especialista  por ID ", description = "Este metódo retorna os dados não - pessoais do Especialista, pode ser usado pelo admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um especialista"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "404", description = "não há especialista com este Id no banco de dados")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EspecialistaDTO> buscarPorId(@PathVariable Integer id) throws Exception;

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
    public ResponseEntity<EspecialistaCreateDTO> atualizarEspecialista(@PathVariable Integer idEspecialista, @RequestBody @Valid EspecialistaDTO dto) throws Exception;

    @Operation(summary = "Desativa um Especialista", description = "Busca um Especialista por ID e Desativa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna vazio Status  200 "),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "Especialista não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> mudarAtivoEspecialista(@PathVariable Integer id) throws Exception;

}