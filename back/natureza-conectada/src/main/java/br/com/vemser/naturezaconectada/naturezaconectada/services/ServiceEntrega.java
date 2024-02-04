package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EntregaRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.MudaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EntregaResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoEmail;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.*;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.IEntregaRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.MudaRepository;
import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceEntrega {

    private final IEntregaRepository entregaRepository;
//    private final ServiceCliente serviceCliente;
//    private final ServiceEndereco serviceEndereco;
    private final MudaRepository mudaRepository;
    private final ObjectMapper objectMapper;
//    private final EmailService emailService;

    public EntregaResponseDTO adicionar(EntregaRequestDTO entregaRequestDTO, Integer idEndereco) throws Exception {
        try {

//            if (serviceEndereco.procurarPorIdEndereco(idEndereco) == null) {
//                throw new InformacaoNaoEncontrada("Não existe nenhum endereço com o ID: " + idEndereco);
//            }
//            serviceCliente.procurarPorIdCliente(entregaRequestDTO.getIdCliente());

            Set<Integer> idsMudas = new HashSet<>();
            for (Muda muda : entregaRequestDTO.getMudas()) {
                if (!idsMudas.add(muda.getId())) {
                    throw new RegraDeNegocioException("A mesma muda não pode ser adicionada mais de uma vez à entrega.");
                }
                if (muda.getQuantidade() != 1) {
                    throw new RegraDeNegocioException("A quantidade de muda por id deve ser igual a 1.");
                }

//                Muda mudaCadastrada = mudaRepository.buscarPorId(muda.getId());

//                if (mudaCadastrada == null || mudaCadastrada.getAtivo() != Ativo.A) {
//                    throw new InformacaoNaoEncontrada("Muda com id " + muda.getId() + " não está disponível ou não está ativa.");
//                }
//
//                if (mudaCadastrada.getQuantidade() < 1) {
//                    throw new RegraDeNegocioException("Quantidade insuficiente para a muda com ID " + muda.getId());
//                }
            }

            entregaRequestDTO.setStatus(StatusEntrega.RECEBIDO);
            entregaRequestDTO.setDataPedido(LocalDate.now());
//            entregaRequestDTO.setEndereco(enderecoRecuperado);
            Cliente cliente = clienteService.findById(entregaRequestDTO.getIdCliente());

            Entrega entrega = objectMapper.convertValue(entregaRequestDTO, Entrega.class);
            entrega.setCliente(cliente);

            Entrega entregaProcessada = entregaRepository.save(entrega);

            EntregaResponseDTO novaEntrega = objectMapper.convertValue(entregaProcessada, EntregaResponseDTO.class);

//            emailService.sendEmail(novaEntrega,TipoEmail.CRIACAO);

            return novaEntrega ;
        } catch (RegraDeNegocioException e) {
            throw e;
        }
//        } catch (InformacaoNaoEncontrada e) {
//            throw new RegraDeNegocioException("Erro ao adicionar entrega: " + e.getMessage());
//        }
    }

    public EntregaResponseDTO editarMudasEntrega(Integer id, EntregaRequestDTO entregaRequestDTO) throws ErroNoBancoDeDados, RegraDeNegocioException {
        Entrega entrega = procurar(id);
        if (entrega == null) throw new InformacaoNaoEncontrada("Não existe nenhuma entregada com o ID: " + id);

        try {
            Entrega entregaEntity = objectMapper.convertValue(entregaRequestDTO, Entrega.class);

            entrega.setMudas(entregaEntity.getMudas());

//            entregaRepository.atualizarMudas(id, entrega.getMudas());

            Entrega entregaProcessada = this.procurar(id);

            EntregaResponseDTO novaEntrega = objectMapper.convertValue(entregaProcessada, EntregaResponseDTO.class);

//            this.emailService.sendEmail(novaEntrega,TipoEmail.ALTERACAO);

            return novaEntrega;
        } catch (Exception e) {
            throw new ErroNoBancoDeDados("Erro ao editar entrega com Id: " + id);
        }
    }
    public EntregaResponseDTO mudarStatusEntrega(Integer idEntrega, String status) throws RegraDeNegocioException {
        Entrega entrega = procurar(idEntrega);
        entrega =  entregaRepository.editarStatus(StatusEntrega.valueOf(status.toUpperCase()), idEntrega);
        return this.objectMapper.convertValue(entrega,EntregaResponseDTO.class);
    }

    public List<EntregaResponseDTO> listarTodos() throws RegraDeNegocioException {
        List<Entrega> entregas = entregaRepository.findAll();
        if (!entregas.isEmpty()) {
            return entregas.stream()
                    .map(entregaEntity -> objectMapper.convertValue(entregaEntity, EntregaResponseDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw new RegraDeNegocioException("Nenhuma entrega encontrada");
        }
    }

    public EntregaResponseDTO procurarPorId(Integer id) throws RegraDeNegocioException {
        Entrega entrega = procurar(id);
        return objectMapper.convertValue(entrega, EntregaResponseDTO.class);
    }

    private Entrega procurar(Integer id) throws RegraDeNegocioException {
        Optional<Entrega> entregaOptional = entregaRepository.findById(id);
        if (entregaOptional.isPresent()) {
            return entregaOptional.get();
        } else {
            throw new RegraDeNegocioException("Nenhuma entrega encontrada para o id: " + id);
        }
    }
}