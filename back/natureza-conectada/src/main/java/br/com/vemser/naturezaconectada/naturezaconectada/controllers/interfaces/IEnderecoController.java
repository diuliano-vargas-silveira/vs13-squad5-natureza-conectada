package br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EnderecoRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EnderecoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface IEnderecoController {

    @Operation(summary = "Cadastrar endereço", description = "Cadastra endereço pelo id do cliente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o endereço cadastrado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idCliente}")
    public ResponseEntity<EnderecoResponseDTO> adicionar(@PathVariable("idCliente") Integer idCliente, @Valid @RequestBody EnderecoRequestDTO endereco) throws Exception;

    @Operation(summary = "Atualizar endereço", description = "Atualiza o endereço pelo id do endereço do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o endereço atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoResponseDTO> editar(@PathVariable("idEndereco") Integer idEndereco, @Valid @RequestBody EnderecoRequestDTO endereco) throws Exception;

    @Operation(summary = "Ativar endereço de cliente", description = "Ativa uma endereço no banco pelo id e insere um ecossistema ")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o endereço Ativado e com ecossistema"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Enum ecossistema não existe / não existe endereço com este id ")
            }
    )
    public ResponseEntity<EnderecoResponseDTO> ativarEndereco(@PathVariable("idEndereco") Integer idEndereco, @RequestParam String eco) throws Exception;
    @Operation(summary = "Excluir endereço", description = "Exclui o endereço pelo id do endereço do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Exclui o endereço pelo id endereço"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> remover(@PathVariable("idEndereco") Integer idEndereco) throws Exception;

    @Operation(summary = "Listar endereços", description = "Lista todos os endereços do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<EnderecoResponseDTO>> listarTodos() throws Exception;

    @Operation(summary = "Retornar endereço por id do endereço", description = "Retorna pelo id do endereço as infomações cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o endereço pelo id do endereço"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoResponseDTO> procurarPorIdEndereco(@PathVariable("idEndereco") Integer idEndereco) throws Exception;

    @Operation(summary = "Listar endereço ativos com id do cliente", description = "Lista todas os endereços cadastrados e ativos no banco com o id do cliente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços Ativos  pelo id do cliente"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public List<EnderecoResponseDTO> listarEnderecosPorAtivo() throws Exception;
}
