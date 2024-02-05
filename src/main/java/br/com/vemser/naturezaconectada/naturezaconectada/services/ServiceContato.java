 package br.com.vemser.naturezaconectada.naturezaconectada.services;

 import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ContatoCreateDTO;
 import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ContatoDTO;
 import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
 import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
 import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
 import br.com.vemser.naturezaconectada.naturezaconectada.repository.ContatoRepository;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import lombok.RequiredArgsConstructor;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.stereotype.Service;

 import java.sql.SQLException;
 import java.util.List;
 import java.util.stream.Collectors;

 @RequiredArgsConstructor
 @Service
 @Slf4j
 public class ServiceContato  {

    private final ContatoRepository contatoRepository;
    private final ObjectMapper objectMapper;
    private final ServiceCliente serviceCliente;


     public ContatoDTO adicionar(ContatoCreateDTO contatoCreateDTO, Integer idCliente) throws Exception {
         var contato = objectMapper.convertValue(contatoCreateDTO, Contato.class);
         var clienteDTO = serviceCliente.procurarClienteAtivo(idCliente);
         var cliente = objectMapper.convertValue(clienteDTO, Cliente.class);

         contato.setCliente(cliente);
         contatoRepository.save(contato);
         cliente.getContatos().add(contato);

        return objectMapper.convertValue(contato, ContatoDTO.class);
    }

     public ContatoDTO editar(Integer idContato, ContatoCreateDTO contatoEditado) throws Exception {
         var contatoEncontrado = contatoRepository.getById(idContato);

         var clienteDTO = serviceCliente.procurarPorId(contatoEncontrado.getCliente().getId());

         var cliente = objectMapper.convertValue(clienteDTO, Cliente.class);

         contatoEncontrado.setDescricao(contatoEditado.getDescricao());
         contatoEncontrado.setTipo(contatoEditado.getTipo());
         contatoEncontrado.setNumero(contatoEditado.getNumero());

         contatoEncontrado.setCliente(cliente);

         contatoRepository.save(contatoEncontrado);

         var contato = objectMapper.convertValue(contatoEncontrado, ContatoDTO.class);
//         contato.setIdCliente(cliente.getId());

         return contato;
     }

     public void remover(Integer idContato) throws Exception {
         var contatoEncontrado = procurarPorIdContato(idContato);
         var contato = objectMapper.convertValue(contatoEncontrado, Contato.class);
         contatoRepository.delete(contato);
     }

     public List<ContatoDTO> listarTodos() throws SQLException {
         var contatos = contatoRepository.findAll();

         return contatos.stream()
                 .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                 .collect(Collectors.toList());
     }

     public List<ContatoDTO> procurarPorIdCliente(Integer idCliente) throws Exception {
         var contatos = contatoRepository.findAll().stream()
                 .filter(contato -> contato.getCliente() != null && contato.getCliente().getId().equals(idCliente))
                 .collect(Collectors.toList());

         var contatosDTO = contatos.stream()
                 .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                 .collect(Collectors.toList());

         return contatosDTO;
     }

     private ContatoDTO procurarPorIdContato(Integer idContato) throws SQLException, RegraDeNegocioException {
         return listarTodos().stream()
                 .filter(contato -> contato.getIdContato().equals(idContato))
                 .findFirst().orElseThrow(() -> new RegraDeNegocioException("Contato não encontrado."));
     }
}

