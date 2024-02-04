package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EnderecoCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EnderecoDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EnderecoRepository;
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
        enderecoCreateDTO.setClientes(clientes);

        enderecoRepository.save(endereco);

        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }

//    public void deletar(Integer idEndereco) throws Exception {
//        var endereco = enderecoRepository.findById(idEndereco);
//
//        var enderecoEntity = objectMapper.convertValue(endereco, Endereco.class);
//
//        enderecoRepository.delete(enderecoEntity);
//    }
//
//    public EnderecoDTO editar(Integer idEndereco, EnderecoCreateDTO enderecoEditado) throws Exception {
//        var enderecoEncontrado = objectMapper.convertValue(enderecoEditado, Endereco.class);
//
//
//        enderecoEncontrado.setIdEndereco(idEndereco);
//
//
//        return objectMapper.convertValue(enderecoEncontrado, EnderecoDTO.class);
//    }
//
    public EnderecoDTO procurarPorIdEndereco(int idEndereco) throws Exception {
        var enderecoEncontrado = enderecoRepository.getById(idEndereco);

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
//    public EnderecoDTO ativarEndereco(Integer id, String eco) throws Exception {
//        EnderecoDTO endereco = this.procurarPorIdEndereco(id);
//        this.enderecoRepository.ativarEndereco(endereco.getIdEndereco(),eco);
//        endereco.setEcossistema(Ecossistema.valueOf(eco));
//        endereco.setAtivo(Ativo.A);
//        return endereco;
//
//    }
//    public List<EnderecoDTO> listarEnderecosPorAtivo(String ativo) throws Exception {
//        List<Endereco> listaPorAtivo = this.enderecoRepository.listarEnderecosPorAtivo(ativo);
//
//        return listaPorAtivo.stream().map(endereco -> this.objectMapper.convertValue(endereco,EnderecoDTO.class)).toList();
//
//

//    }
}