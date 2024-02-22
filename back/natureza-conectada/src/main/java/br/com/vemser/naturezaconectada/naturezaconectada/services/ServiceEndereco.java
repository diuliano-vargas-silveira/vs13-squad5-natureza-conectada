package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EnderecoRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EnderecoResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.ClienteRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ServiceEndereco {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    public EnderecoResponseDTO adicionar(EnderecoRequestDTO enderecoCreateDTO, Integer idCliente) throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        var endereco = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
        var cliente = clienteRepository.getById(idCliente);
        clientes.add(cliente);
        endereco.setClientes(clientes);
        endereco.setAtivo(Ativo.A);

        enderecoRepository.save(endereco);

        return objectMapper.convertValue(endereco, EnderecoResponseDTO.class);
    }

    public void remover(Integer idEndereco) throws Exception {
        var enderecoEncontrado = enderecoRepository.getById(idEndereco);

        if (enderecoEncontrado.getAtivo() == Ativo.A)
            enderecoEncontrado.setAtivo(Ativo.D);

        enderecoRepository.save(enderecoEncontrado);
    }

    public EnderecoResponseDTO editar(Integer idEndereco, EnderecoRequestDTO enderecoEditado) throws Exception {
        var enderecoEncontrado = enderecoRepository.getById(idEndereco);

        enderecoEncontrado.setCep(enderecoEditado.getCep());
        enderecoEncontrado.setCidade(enderecoEditado.getCidade());
        enderecoEncontrado.setEstado(enderecoEditado.getEstado());
        enderecoEncontrado.setLogradouro(enderecoEditado.getLogradouro());
        enderecoEncontrado.setNumero(enderecoEditado.getNumero());
        enderecoEncontrado.setComplemento(enderecoEditado.getComplemento());

        var endereco = objectMapper.convertValue(enderecoEncontrado, Endereco.class);

        enderecoRepository.save(endereco);

        return objectMapper.convertValue(enderecoEncontrado, EnderecoResponseDTO.class);
    }

    public EnderecoResponseDTO procurarPorIdEndereco(Integer idEndereco) throws Exception {
        var enderecoEncontrado =  enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));

        return objectMapper.convertValue(enderecoEncontrado, EnderecoResponseDTO.class);
    }

    public List<EnderecoResponseDTO> listarTodos() throws Exception {
        var enderecos = enderecoRepository.findAll();


        return enderecos.stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public EnderecoResponseDTO ativarEndereco(Integer idEndereco, String eco) throws Exception {
        var enderecoEncontrado = enderecoRepository.getById(idEndereco);

        if (enderecoEncontrado.getAtivo() == Ativo.D)
            enderecoEncontrado.setAtivo(Ativo.A);

        enderecoRepository.save(enderecoEncontrado);

        return objectMapper.convertValue(enderecoEncontrado, EnderecoResponseDTO.class);

    }

    public List<EnderecoResponseDTO> listarEnderecosPorAtivo() throws Exception {
        var listaEnderecosAtivos =  enderecoRepository.findAll().stream()
                .filter(endereco -> endereco.getAtivo().equals(Ativo.A)).toList();

        return listaEnderecosAtivos.stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoResponseDTO.class))
                .collect(Collectors.toList());
    }
}