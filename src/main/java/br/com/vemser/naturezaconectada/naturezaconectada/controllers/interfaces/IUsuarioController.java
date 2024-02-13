package br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IUsuarioController {


    @Operation(summary = "Listar todos os usuários", description = "Recupera uma lista de todos os usuários cadastrados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class)))),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<UsuarioResponseDTO>> listarTodos() throws Exception;

    @Operation(summary = "Listar todos os usuários ativos", description = "Recupera uma lista de todos os usuários cadastrados e ativos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class)))),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/ativo")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuariosAtivos() throws Exception;


    @Operation(summary = "Buscar usuário por e-mail", description = "Recupera um usuário com base no endereço de e-mail")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/email")
    ResponseEntity<UsuarioResponseDTO> procurarPorEmail(@RequestParam String email) throws Exception;


}