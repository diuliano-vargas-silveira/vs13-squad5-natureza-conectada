 package br.com.vemser.naturezaconectada.naturezaconectada.services;

 import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.ContatoCreateDTO;
 import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.ContatoDTO;
 import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
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
         this.serviceCliente.procurarClientesAtivos(idCliente);

        contato = contatoRepository.adicionar(contato, idCliente);

        return objectMapper.convertValue(contato, ContatoDTO.class);
    }

     public ContatoDTO editar(Integer idContato, ContatoCreateDTO contatoEditado) throws Exception {
         var contato = objectMapper.convertValue(contatoEditado, Contato.class);

         contatoRepository.editar(idContato, contato);

         contato.setId(idContato);
         contato.setIdCliente(contato.getIdCliente());

         return objectMapper.convertValue(contato, ContatoDTO.class);
     }

     public void remover(Integer idContato) throws Exception {
         var contatoEncontrado = procurarPorIdContato(idContato);
         var contato = objectMapper.convertValue(contatoEncontrado, Contato.class);
         contatoRepository.excluir(contato.getId());
     }

     public List<ContatoDTO> listarTodos() throws SQLException {
         var contatos = contatoRepository.listarTodos();

         if (contatos == null)
             return null;

         return contatos.stream()
                 .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                 .collect(Collectors.toList());
     }

     public List<ContatoDTO> procurarPorIdCliente(Integer idCliente) throws Exception {
         var contatos = contatoRepository.procurarContatoPorIdCliente(idCliente);

         if (contatos == null)
             return null;

         return contatos.stream()
                 .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                 .collect(Collectors.toList());
     }

     private ContatoDTO procurarPorIdContato(Integer idContato) throws SQLException, RegraDeNegocioException {
         return listarTodos().stream()
                 .filter(contato -> contato.getId().equals(idContato))
                 .findFirst().orElseThrow(() -> new RegraDeNegocioException("Contato não encontrado."));
     }
}

