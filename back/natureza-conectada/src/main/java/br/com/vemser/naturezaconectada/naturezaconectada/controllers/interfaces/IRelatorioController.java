package br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.AvaliacaoDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.RelatorioRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioResponseAdmin;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioResponseEspecialista;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface IRelatorioController {
    @Operation(summary = "Cria um novo relatório", description = "Cria um novo Relatorio no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o Relatorio criada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao criar Relatorio  no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "Algum erro de regra de negocio")
            }
    )
    @PostMapping
    public ResponseEntity<RelatorioClienteDTO> adicionar(@Valid @RequestBody RelatorioRequestDTO dto) throws Exception;

    @Operation(summary = "Lista os relatorio de um especialista especifico", description = "Busca os relatorio de um especialista especifico")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista dos relatorio de um especialista especifico"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "não há  relatorios no banco de dados")
            }
    )
    @GetMapping("/espec/{idEspecialista}")
    public ResponseEntity<List<RelatorioResponseEspecialista>> relatorioEspecialista(@PathVariable Integer idEspecialista) throws Exception;


    @Operation(summary = "Lista os relatorios abertos", description = "Busca  os relatorios abertos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de relatorios abertos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "não há relatorios abertos no banco de dados")
            }
    )
    @GetMapping("/aberto")
    public ResponseEntity<List<RelatorioResponseEspecialista>> buscarRelatorioAbertos();

    @Operation(summary = "Avalia um relatorio", description = "Busca uma relatorio aberto e avalia ele")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "não há relatorio com este id  no banco de dados")
            }
    )

    @PutMapping("/avaliacao/{idRelatorio}")
    public ResponseEntity<Void> avaliarRelatorio(@PathVariable Integer idRelatorio, @RequestBody @Valid AvaliacaoDTO dto) throws Exception;

    @Operation(summary = "Lista todos os relatorios", description = "Busca uma Lista todos os relatorios")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de todos os relatorios"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "não há relatorios no banco de dados")
            }
    )
    @GetMapping()
    public ResponseEntity<List<RelatorioResponseAdmin>> listarTodos();

    @Operation(summary = "Edita um relatorio", description = "Busca um relatorio e edita")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Busca um relatorio e edita"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "não há relatorio com este ID no banco de dados")
            }
    )
    @PutMapping("/{idRelatorio}")
    public ResponseEntity<RelatorioClienteDTO> editarRelatorio(@PathVariable Integer idRelatorio, @RequestBody @Valid RelatorioRequestDTO dto) throws Exception;

    @Operation(summary = "Lista os relatorios de um cliente ", description = "Lista os relatorios de um cliente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma Lista os relatorios de um cliente"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "não há relatorios no banco de dados")
            }
    )
    @GetMapping("/cliente/{id}")
    public ResponseEntity<RelatorioClienteDTO> relatorioCliente(@PathVariable Integer id) throws Exception ;
}
