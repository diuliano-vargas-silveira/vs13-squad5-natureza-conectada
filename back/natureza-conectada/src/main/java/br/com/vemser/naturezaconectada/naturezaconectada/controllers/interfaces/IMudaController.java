package br.com.vemser.naturezaconectada.naturezaconectada.controllers.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.relatorios.RelatorioMudasDoadas;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.MudaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.MudaDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IMudaController {

    @Operation(summary = "Listar todas as  Mudas salvas no banco", description = "Lista todas as Mudas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de Mudas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "não há mudas no banco de dados")
            }
    )
    @GetMapping
    public ResponseEntity<Page<MudaCreateDTO>> listarMudas(@PageableDefault() Pageable paginacao) throws Exception;

    @Operation(summary = "Listar Mudas Ativas", description = "Lista todas as Mudas Ativas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de Mudas ativas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "não há mudas Ativas  no banco de dados")
            }
    )
    @GetMapping("/ativas")
    public ResponseEntity<List<MudaDTO>>listarAtivas() throws Exception ;

    @Operation(summary = "Lista Muda por ID", description = "Busca uma muda por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma Muda"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "não há muda com esse ID no banco de dados")
            }
    )
    @GetMapping("/{idMuda}")
    public ResponseEntity<MudaDTO> buscarPorId(@PathVariable Integer idMuda) throws Exception;

    @Operation(summary = "Lista Muda por ecossistema", description = "Busca uma muda por ecossistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de  Mudas por ecossistema"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao buscar no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "não há muda com deste ecossistema no banco de dados")
            }
    )
    @GetMapping("/buscar")
    public ResponseEntity<List<MudaDTO>> buscarPorEco(@RequestParam Ecossistema eco) throws Exception;

    @Operation(summary = "Cria uma nova muda", description = "Cria uma nova muda no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a Muda criada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao criar muda  no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "Algum erro de regra de negocio")
            }
    )
    @PostMapping
    public ResponseEntity<MudaCreateDTO> novaMuda(@RequestBody MudaCreateDTO dto) throws Exception;

    @Operation(summary = " Atualiza uma muda ", description = "Busca uma muda por ID e atualiza ")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a Muda atualizada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao atualizar muda  no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "Muda não existe no banco de dados")
            }
    )
    @PutMapping("/{idMuda}")
    public ResponseEntity<MudaCreateDTO> atualizarMuda(@PathVariable Integer idMuda, @RequestBody MudaCreateDTO dto) throws Exception;

    @Operation(summary = " Ativa e Desativa uma muda ", description = " Recebe o RequestParam ativo = ('D' -> desativado, 'A' -> Ativado ")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Vazio"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao Deletar muda no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "Muda não existe no banco de dados")
            }
    )
    @DeleteMapping("/{idMuda}")
    public ResponseEntity<Void> mudarAtivoMuda(@PathVariable Integer idMuda) throws Exception;

    @Operation(summary = "Quantidade de mudas doadas por espécie", description = "Retorna relatório com quantidade de mudas doadas por espécie")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista de relatório"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao Deletar muda no banco de dados"),
                    @ApiResponse(responseCode = "400", description = "Muda não existe no banco de dados")
            }
    )
    @GetMapping("/doacoes")
    public ResponseEntity<List<RelatorioMudasDoadas>> mudasDoadas();
}
