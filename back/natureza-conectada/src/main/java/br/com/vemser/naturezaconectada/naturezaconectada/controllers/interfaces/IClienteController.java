package br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ClienteRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ClienteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface IClienteController {

    @Operation(summary = "Cadastrar cliente", description = "Cadastra cliente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o cliente cadastrado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> adicionar(@Valid @RequestBody ClienteRequestDTO clienteCreateDTO) throws Exception;

    @Operation(summary = "Editar cliente", description = "Edita o cliente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o cliente editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> editar(@PathVariable("id") Integer id, @Valid @RequestBody ClienteRequestDTO cliente) throws Exception;

    @Operation(summary = "Listar clientes", description = "Lista os clientes cadastrados no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os clientes cadastrados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> listarClientes(@PageableDefault() Pageable paginacao) throws Exception;

    @Operation(summary = "Procurar cliente", description = "Procura cliente por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o cliente cadastrado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> procurarPorId(@PathVariable("id") Integer id) throws Exception;

    @Operation(summary = "Procurar clientes ativos", description = "Procura os clientes ativos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o clientes ativos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/ativos")
    public ResponseEntity<List<ClienteResponseDTO>> procurarClienteAtivos();

    @Operation(summary = "Desativar cliente", description = "Desativa o cliente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Vazio"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/status/{id}")
    public ResponseEntity<Void> desativarCliente(@PathVariable("id") Integer id) throws Exception;
}
