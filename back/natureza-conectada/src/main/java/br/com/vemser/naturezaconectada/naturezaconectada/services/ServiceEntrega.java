package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EntregaRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EntregaResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EntregaRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.MudaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceEntrega {

    private final EntregaRepository entregaRepository;
    private final ServiceCliente serviceCliente;
    private final ServiceEndereco serviceEndereco;
    private final MudaRepository mudaRepository;
    private final ObjectMapper objectMapper;

    public EntregaResponseDTO adicionar(EntregaRequestDTO entregaRequestDTO, int idEndereco) throws RegraDeNegocioException {
        try {

            if (serviceEndereco.procurarPorIdEndereco(idEndereco) == null) {
                throw new InformacaoNaoEncontrada("Não existe nenhum endereço com o ID: " + idEndereco);
            }
            serviceCliente.procurarPorIdCliente(entregaRequestDTO.getCliente().getId());

            Set<Integer> idsMudas = new HashSet<>();
            for (Muda muda : entregaRequestDTO.getMudas()) {
                if (!idsMudas.add(muda.getId())) {
                    throw new RegraDeNegocioException("A mesma muda não pode ser adicionada mais de uma vez à entrega.");
                }
                if (muda.getQuantidade() != 1) {
                    throw new RegraDeNegocioException("A quantidade de muda por id deve ser igual a 1.");
                }

                Muda mudaCadastrada = mudaRepository.buscarPorId(muda.getId());

                if (mudaCadastrada == null || mudaCadastrada.getAtivo() != Ativo.A) {
                    throw new InformacaoNaoEncontrada("Muda com id " + muda.getId() + " não está disponível ou não está ativa.");
                }

                if (mudaCadastrada.getQuantidade() < 1) {
                    throw new RegraDeNegocioException("Quantidade insuficiente para a muda com ID " + muda.getId());
                }
            }

            entregaRequestDTO.setStatus(StatusEntrega.RECEBIDO);

            Entrega entrega = objectMapper.convertValue(entregaRequestDTO, Entrega.class);

            Entrega entregaProcessada = entregaRepository.adicionar(entrega, idEndereco);

            return objectMapper.convertValue(entregaProcessada, EntregaResponseDTO.class);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (InformacaoNaoEncontrada e) {
            throw new RegraDeNegocioException("Erro ao adicionar entrega: " + e.getMessage());
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao adicionar entrega.");
        }
    }


    public EntregaResponseDTO editar(int id, EntregaRequestDTO entregaRequestDTO) throws ErroNoBancoDeDados {
        Entrega entrega = procurar(id);
        if (entrega == null) throw new InformacaoNaoEncontrada("Não existe nenhuma entregada com o ID: " + id);

        try {
            Entrega entregaEntity = objectMapper.convertValue(entregaRequestDTO, Entrega.class);

            entrega.setStatus(entregaEntity.getStatus());
            entrega.setMudas(entregaEntity.getMudas());

            Entrega entregaProcessada = entregaRepository.editar(id, entrega);

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
        if (entrega == null) throw new InformacaoNaoEncontrada("Não existe nenhuma entregada com o ID: " + id);

        EntregaResponseDTO entregaResponseDTO = objectMapper.convertValue(entrega, EntregaResponseDTO.class);
        return entregaResponseDTO;
    }

    public void deletar(Integer id) throws ErroNoBancoDeDados {
        Entrega entrega = procurar(id);
        if (entrega == null) throw new InformacaoNaoEncontrada("Não existe nenhuma entregada com o ID: " + id);

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