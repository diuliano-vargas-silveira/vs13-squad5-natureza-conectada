package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EntregaRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.*;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.*;
import br.com.vemser.naturezaconectada.naturezaconectada.pk.EntregaMudaPK;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.ClienteRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EnderecoRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EntregaMudaRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EntregaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceEntrega {

    private final EntregaRepository entregaRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ServiceCliente serviceCliente;
    private final ServiceEndereco serviceEndereco;
    private final ServiceMudas serviceMudas;
    private final ObjectMapper objectMapper;
    private final EntregaMudaRepository entregaMudaRepository;

//  private final EmailService emailService;

    public EntregaResponseDTO adicionar(EntregaRequestDTO entregaRequestDTO, Integer idEndereco) throws Exception {
        try {

            validarEndereco(idEndereco);

            validarCliente(entregaRequestDTO.getIdCliente());

            List<Muda> mudaEntity = validarMudas(entregaRequestDTO.getMudas());

            entregaRequestDTO.setStatus(StatusEntrega.RECEBIDO);

            entregaRequestDTO.setDataPedido(LocalDate.now());

            Cliente cliente = obterClientePorId(entregaRequestDTO.getIdCliente());
            Endereco endereco = obterEnderecoPorId(idEndereco);

            Entrega entrega = objectMapper.convertValue(entregaRequestDTO, Entrega.class);

            entrega.setCliente(cliente);
            entrega.setEndereco(endereco);

            Entrega entregaProcessada = entregaRepository.save(entrega);

            processarMudas(mudaEntity, entregaProcessada);

            EntregaResponseDTO novaEntrega = objectMapper.convertValue(entregaProcessada, EntregaResponseDTO.class);

            alteraMudasEnderecosClienteNoEntregaResponseDTO(novaEntrega, entregaProcessada.getId());

//          emailService.sendEmail(novaEntrega,TipoEmail.CRIACAO);

            return novaEntrega ;
        } catch (RegraDeNegocioException e) {
            throw e;
        }
    }

    public EntregaResponseDTO mudarStatusEntrega(Integer idEntrega, String status) throws Exception {
        Entrega entrega = procurar(idEntrega);
        Optional<Cliente> cliente = clienteRepository.findById(entrega.getCliente().getId());
        entrega.setStatus(StatusEntrega.valueOf(status.toUpperCase()));
        entregaRepository.save(entrega);

        if (StatusEntrega.valueOf(status.toUpperCase()) == StatusEntrega.ENTREGUE) {
            entrega.setDataEntrega(LocalDate.now());
            entregaRepository.save(entrega);
            List<Muda> mudaList = buscarMudasEntityEntrega(idEntrega);
            cliente.get().setMudas(mudaList);
        }

        clienteRepository.save(cliente.get());

        EntregaResponseDTO entregaResponseDTO = objectMapper.convertValue(entrega, EntregaResponseDTO.class);
        entregaResponseDTO.setDataEntregue(LocalDate.now());
        alteraMudasEnderecosClienteNoEntregaResponseDTO(entregaResponseDTO, idEntrega);
        return entregaResponseDTO;
    }

    public List<EntregaResponseDTO> listarTodos() throws RegraDeNegocioException {
        List<Entrega> entregas = entregaRepository.findAll();

        if (!entregas.isEmpty()) {
            List<EntregaResponseDTO> listEntregaDTO = entregas.stream()
                    .map(entregaEntity -> objectMapper.convertValue(entregaEntity, EntregaResponseDTO.class))
                    .collect(Collectors.toList());

            listEntregaDTO.forEach(entrega -> {
                try {
                    alteraMudasEnderecosClienteNoEntregaResponseDTO(entrega, entrega.getId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return listEntregaDTO;
        } else {
            throw new RegraDeNegocioException("Nenhuma entrega encontrada");
        }
    }

    public EntregaResponseDTO procurarPorId(Integer id) throws Exception {
        Entrega entrega = procurar(id);
        EntregaResponseDTO entregaResponseDTO = objectMapper.convertValue(entrega, EntregaResponseDTO.class);
        alteraMudasEnderecosClienteNoEntregaResponseDTO(entregaResponseDTO, id);
        return entregaResponseDTO;
    }

    private Entrega procurar(Integer id) throws RegraDeNegocioException {
        Optional<Entrega> entregaOptional = entregaRepository.findById(id);
        if (entregaOptional.isPresent()) {
            return entregaOptional.get();
        } else {
            throw new RegraDeNegocioException("Nenhuma entrega encontrada para o id: " + id);
        }
    }

    private List<MudaDTO> buscarMudasEntrega(Integer idEntrega) throws Exception {
        List<EntregaMuda> entregaList = entregaMudaRepository.buscarMudasEntrega(idEntrega);
        List<MudaDTO> mudaList = new ArrayList<>();
        for (EntregaMuda entregaMuda : entregaList) {
            MudaDTO mudaDTO = new MudaDTO();
            mudaDTO = serviceMudas.procurarPorIdDto(entregaMuda.getEntregaPK().getIdMuda());
            mudaList.add(mudaDTO);
        }
        return mudaList;
    }

    private List<Muda> buscarMudasEntityEntrega(Integer idEntrega) throws Exception {
        List<EntregaMuda> entregaList = entregaMudaRepository.buscarMudasEntrega(idEntrega);
        List<Muda> mudaList = new ArrayList<>();
        for (EntregaMuda entregaMuda : entregaList) {
            Muda muda = new Muda();
            muda = serviceMudas.procurarPorIDEntidade(entregaMuda.getEntregaPK().getIdMuda());
            mudaList.add(muda);
        }
        return mudaList;
    }

    private void alteraMudasEnderecosClienteNoEntregaResponseDTO(EntregaResponseDTO novaEntrega, Integer entregaProcessada) throws Exception {
        novaEntrega.setMudas(buscarMudasEntrega(entregaProcessada));
        novaEntrega.setEndereco(objectMapper.convertValue(enderecoRepository.buscarEnderecoEntrega(entregaProcessada), EnderecoEntregaDTO.class));
        novaEntrega.setCliente(objectMapper.convertValue(clienteRepository.buscarClienteEntrega(entregaProcessada), ClienteEntregaDTO.class));
    }

    private void validarEndereco(Integer idEndereco) throws Exception {
        if (serviceEndereco.procurarPorIdEndereco(idEndereco) == null) {
            throw new InformacaoNaoEncontrada("Não existe nenhum endereço com o ID: " + idEndereco);
        }
    }

    private void validarCliente(Integer idCliente) throws Exception {
        serviceCliente.procurarPorId(idCliente);
    }

    private List<Muda> validarMudas(List<Muda> mudas) throws Exception {
        Set<Integer> idsMudas = new HashSet<>();
        List<Muda> mudaEntity = new ArrayList<>();

        for (Muda muda : mudas) {
            if (!idsMudas.add(muda.getId())) {
                throw new RegraDeNegocioException("A mesma muda não pode ser adicionada mais de uma vez à entrega.");
            }

            Muda mudaEntidade = serviceMudas.procurarPorIDEntidade(muda.getId());
            if (mudaEntidade == null || mudaEntidade.getAtivo() != Ativo.A) {
                throw new InformacaoNaoEncontrada("Muda com id " + muda.getId() + " não está disponível ou não está ativa.");
            }
            if (mudaEntidade.getEstoque() < 1) {
                throw new RegraDeNegocioException("Quantidade insuficiente para a muda com ID " + muda.getId());
            }
            mudaEntity.add(mudaEntidade);
        }
        return mudaEntity;
    }

    private void processarMudas(List<Muda> mudaEntity, Entrega entregaProcessada) {
        for (Muda muda : mudaEntity) {
            muda.setEstoque(muda.getEstoque() - 1);
            EntregaMuda entregaMuda = new EntregaMuda();
            EntregaMudaPK pk = new EntregaMudaPK();
            pk.setIdEntrega(entregaProcessada.getId());
            pk.setIdMuda(muda.getId());
            entregaMuda.setEntregaPK(pk);
            entregaMuda.setQuantidade(1);
            entregaMudaRepository.save(entregaMuda);
        }
    }

    private Cliente obterClientePorId(Integer idCliente) throws Exception {
        ClienteResponseDTO clienteDTO = serviceCliente.procurarPorId(idCliente);
        return objectMapper.convertValue(clienteDTO, Cliente.class);
    }

    private Endereco obterEnderecoPorId(Integer idEndereco) throws Exception {
        EnderecoResponseDTO enderecoDTO = serviceEndereco.procurarPorIdEndereco(idEndereco);
        return objectMapper.convertValue(enderecoDTO, Endereco.class);
    }
}