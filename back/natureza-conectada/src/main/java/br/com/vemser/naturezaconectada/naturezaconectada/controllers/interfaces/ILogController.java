package br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.LogMudasCriadasDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.LogUsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ILogController {

    @Operation(summary = "Total de usuários por tipo", description = "Retorna total de usuários criados por tipo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna total de usuários criados por tipo"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<LogUsuarioDTO>> totalPorTipo();

    @Operation(summary = "Total de mudas cadastradas por admin", description = "Retorna total de mudas cadastradas por admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna total de mudas cadastradas por admin"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("admin")
    public ResponseEntity<List<LogMudasCriadasDTO>> totalPorTipo(@RequestParam("nome")String nome);
}
