package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EnderecoCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EnderecoDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.IClienteRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.IEnderecoRepository;
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

    private final IEnderecoRepository enderecoRepository;
    private final IClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    public EnderecoDTO adicionar(EnderecoCreateDTO enderecoCreateDTO, Integer idCliente) throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        var endereco = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
        var cliente = clienteRepository.getById(idCliente);
        clientes.add(cliente);
        endereco.setClientes(clientes);
        endereco.setAtivo(Ativo.A);

        enderecoRepository.save(endereco);

        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }

    public void remover(Integer idEndereco) throws Exception {
        var enderecoEncontrado = procurarPorIdEndereco(idEndereco);

        var endereco = objectMapper.convertValue(enderecoEncontrado, Endereco.class);

        endereco.setAtivo(Ativo.D);

        enderecoRepository.save(endereco);
    }

    public EnderecoDTO editar(Integer idEndereco, EnderecoCreateDTO enderecoEditado) throws Exception {
        var enderecoEncontrado = procurarPorIdEndereco(idEndereco);

        enderecoEncontrado.setCep(enderecoEditado.getCep());
        enderecoEncontrado.setCidade(enderecoEditado.getCidade());
        enderecoEncontrado.setEstado(enderecoEditado.getEstado());
        enderecoEncontrado.setLogradouro(enderecoEditado.getLogradouro());
        enderecoEncontrado.setNumero(enderecoEditado.getNumero());
        enderecoEncontrado.setComplemento(enderecoEditado.getComplemento());

        var endereco = objectMapper.convertValue(enderecoEncontrado, Endereco.class);

        enderecoRepository.save(endereco);

        return objectMapper.convertValue(enderecoEncontrado, EnderecoDTO.class);
    }

    public EnderecoDTO procurarPorIdEndereco(Integer idEndereco) throws Exception {
        var enderecoEncontrado =  enderecoRepository.findAll().stream()
                .filter(endereco -> endereco.getIdEndereco().equals(idEndereco)).findFirst().orElseThrow(() -> new RegraDeNegocioException("Endereço não existe."));

        return objectMapper.convertValue(enderecoEncontrado, EnderecoDTO.class);
    }

    public List<EnderecoDTO> listarTodos() throws Exception {
        var enderecos = enderecoRepository.findAll();


        return enderecos.stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }
//
//
//    public List<EnderecoDTO> procurarEnderecoPorIdCliente(Integer idCliente) throws Exception {
//        var enderecos = enderecoRepository.procurarEnderecoPorIdCliente(idCliente);
//
//        if (enderecos == null)
//            return null;
//
//        return enderecos.stream()
//                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
//                .collect(Collectors.toList());
//    }
//
    public EnderecoDTO ativarEndereco(Integer idEndereco, String eco) throws Exception {
        var enderecoEncontrado = procurarPorIdEndereco(idEndereco);

        enderecoEncontrado.setAtivo(Ativo.A);

        return objectMapper.convertValue(enderecoEncontrado, EnderecoDTO.class);

    }

    public List<EnderecoDTO> listarEnderecosPorAtivo() throws Exception {
        var listaEnderecosAtivos =  enderecoRepository.findAll().stream()
                .filter(endereco -> endereco.getAtivo().equals(Ativo.A)).toList();

        return listaEnderecosAtivos.stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());



    }
}