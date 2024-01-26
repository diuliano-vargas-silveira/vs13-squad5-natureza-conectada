package br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ContatoCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ContatoDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.Exception;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

public interface IContatoController {

    @Operation(summary = "Criar contato", description = "Cria contato com o id do cliente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o contato criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idCliente}")
    public ResponseEntity<ContatoDTO> adicionar(@PathVariable("idCliente") Integer idCliente, @Valid @RequestBody ContatoCreateDTO contato) throws Exception;

    @Operation(summary = "Atualizar contato", description = "Atualiza o contato pelo id do contato")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o contato atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idContato}")
    public ResponseEntity<ContatoDTO> editar(@PathVariable("idContato") Integer idContato, @Valid @RequestBody ContatoCreateDTO contatoAtualizado) throws Exception;

    @Operation(summary = "Excluir contato", description = "Exclui o contato pelo id do contato")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Exclui o contato"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idContato}")
    public ResponseEntity<Void> remover(@PathVariable("idContato") Integer idContato) throws SQLException;

    @Operation(summary = "Listar contatos", description = "Lista todos os contatos do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de contatos"),
                    @ApiResponse(responseCode = "204", description = "Nenhum contato encontrado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<ContatoDTO>> listarTodos() throws SQLException;

    @Operation(summary = "Listar contatos por id cliente", description = "Lista todos os contatos pelo id do cliente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de contatos do cliente"),
                    @ApiResponse(responseCode = "204", description = "Nenhum contato encontrado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCliente}")
    public ResponseEntity<List<ContatoDTO>> procurarPorIdCliente(@PathVariable("idCliente") Integer idCliente) throws Exception;
}
