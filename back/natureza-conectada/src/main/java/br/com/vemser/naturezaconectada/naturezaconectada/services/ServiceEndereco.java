//package br.com.vemser.naturezaconectada.naturezaconectada.services;
//
//import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EnderecoCreateDTO;
//import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EnderecoDTO;
//import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
//import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
//import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
//import br.com.vemser.naturezaconectada.naturezaconectada.repository.EnderecoRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Service
//@Slf4j
//public class ServiceEndereco {
//
//    private final EnderecoRepository enderecoRepository;
//    private final ObjectMapper objectMapper;
//
//    public EnderecoDTO adicionar(EnderecoCreateDTO enderecoCreateDTO, Integer idCliente) throws Exception {
//        var endereco = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
//
//        endereco = enderecoRepository.adicionar(endereco, idCliente);
//
//        return objectMapper.convertValue(endereco, EnderecoDTO.class);
//    }
//
//    public void deletar(Integer idEndereco) throws Exception {
//        var endereco = procurarPorIdEndereco(idEndereco);
//
//        enderecoRepository.remover(endereco.getIdEndereco());
//    }
//
//    public EnderecoDTO editar(Integer idEndereco, EnderecoCreateDTO enderecoEditado) throws Exception {
//        var enderecoEncontrado = objectMapper.convertValue(enderecoEditado, Endereco.class);
//
//
//        enderecoRepository.editar(idEndereco, enderecoEncontrado);
//
//        enderecoEncontrado.setIdEndereco(idEndereco);
//        enderecoEncontrado.setIdCliente(enderecoEncontrado.getIdCliente());
//
//
//        return objectMapper.convertValue(enderecoEncontrado, EnderecoDTO.class);
//    }
//
//    public EnderecoDTO procurarPorIdEndereco(int idEndereco) throws Exception {
//        var enderecoEncontrado = enderecoRepository.procurarEnderecoPorId(idEndereco);
//
//        if (enderecoEncontrado == null)
//            return null;
//
//        return objectMapper.convertValue(enderecoEncontrado, EnderecoDTO.class);
//    }
//
//    public List<EnderecoDTO> listarTodos() throws Exception {
//        var enderecos = enderecoRepository.listar();
//
//        if (enderecos == null)
//            return null;
//
//        return enderecos.stream()
//                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
//                .collect(Collectors.toList());
//    }
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
//
//    }
//}