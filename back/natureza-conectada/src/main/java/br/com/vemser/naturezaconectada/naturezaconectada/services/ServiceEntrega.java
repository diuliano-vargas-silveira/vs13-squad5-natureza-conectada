package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EntregaRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.AdminResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EntregaResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Admin;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EntregaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceEntrega {

    private final EntregaRepository entregaRepository;
    private final ServiceCliente serviceCliente;
    private final ObjectMapper objectMapper;

    public EntregaResponseDTO adicionar(EntregaRequestDTO entregaRequestDTO, int idEndereco) throws RegraDeNegocioException {
        // Realizar verificação do endereço por id
        // Realizar verificação do cliente por id
        // Realizar verificação da muda por id

        try {
            entregaRequestDTO.setStatus(StatusEntrega.RECEBIDO);
            Entrega entrega = objectMapper.convertValue(entregaRequestDTO, Entrega.class);
            Entrega entregaProcessada = entregaRepository.adicionar(entrega, idEndereco);

            return objectMapper.convertValue(entregaProcessada, EntregaResponseDTO.class);
        } catch (ErroNoBancoDeDados e) {
            throw new RegraDeNegocioException("Erro ao adicionar entrega.");
        }
    }

    public EntregaResponseDTO editar(int id, EntregaRequestDTO entregaRequestDTO) throws ErroNoBancoDeDados {
        try {
            Entrega entrega = procurar(id);

            Entrega entregaEntity = objectMapper.convertValue(entregaRequestDTO, Entrega.class);
            Entrega entregaProcessada = entregaRepository.editar(id, entregaEntity);
            entregaProcessada.setId(id);

            return objectMapper.convertValue(entregaProcessada, EntregaResponseDTO.class);
        } catch (Exception e) {
            throw new ErroNoBancoDeDados("Erro ao editar entrega com Id: " + id);
        }
    }

    public List<EntregaResponseDTO> listarTodos() throws ErroNoBancoDeDados {
        try {
            List<Entrega> entregas = entregaRepository.listar();

            return entregas.stream()
                    .map(entrega -> objectMapper.convertValue(entrega, EntregaResponseDTO.class))
                    .collect(Collectors.toList());
        } catch (ErroNoBancoDeDados e) {
            throw new ErroNoBancoDeDados("Erro ao listar todas as entregas");
        }
    }

    public EntregaResponseDTO procurarPorID(int id) throws ErroNoBancoDeDados {
        Entrega entrega = procurar(id);

        if (entrega == null) {
            throw new InformacaoNaoEncontrada("Não existe nenhuma entregada com o ID: " + id);
        }

        EntregaResponseDTO entregaResponseDTO = objectMapper.convertValue(entrega, EntregaResponseDTO.class);
        return entregaResponseDTO;
    }

    public void deletar(Integer id) throws ErroNoBancoDeDados {
        Entrega entrega = procurar(id);

        try {
            this.entregaRepository.remover(id);
        } catch (ErroNoBancoDeDados e) {
            throw new ErroNoBancoDeDados("Não foi encontrado entrega para o id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Entrega procurar(int id) throws ErroNoBancoDeDados {
        try {
            return entregaRepository.procurarPorId(id);
        } catch (Exception ex) {
            throw new ErroNoBancoDeDados("Nenhuma entrega encontrada para o Id: " + id);
        }
    }
}