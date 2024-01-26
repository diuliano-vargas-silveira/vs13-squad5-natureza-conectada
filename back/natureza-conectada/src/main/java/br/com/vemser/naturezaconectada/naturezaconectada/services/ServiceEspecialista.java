
package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EspecialistaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EspecialistaDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.interfaces.IService;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EspecialistaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceEspecialista  {

    private final EspecialistaRepository especialistaRepository;
    private final ServiceUsuario serviceUsuario;

    private final ObjectMapper objectMapper;



    public EspecialistaCreateDTO adicionar(EspecialistaCreateDTO especialista) throws Exception {
        UsuarioResponseDTO usuarioCriado = serviceUsuario.adicionarUsuario(especialista);

        especialista.setId(usuarioCriado.getId());
       Especialista especialistaAdicionado =  especialistaRepository.adicionar(this.objectMapper.convertValue(especialista,Especialista.class));
        especialista.setIdEspecialista(especialistaAdicionado.getIdEspecialista());
        return especialista;
    }


    public void deletar(int id) throws Exception {
        Especialista especialista = procurarPorID(id);

        especialistaRepository.remover(id);

        serviceUsuario.remover(especialista.getId());

    }


//    public boolean editar(int id, Especialista especialistaEditado) throws Exception {
//        serviceUsuario.editar(especialistaEditado.getId(), especialistaEditado);
//        return especialistaRepository.editar(id, especialistaEditado);
//    }


    private Especialista procurarPorID(int id) throws Exception {
        Especialista especialista = this.especialistaRepository.procurarPorId(id);

        if (especialista == null) {
            throw new InformacaoNaoEncontrada("Não existe nenhum especialista com este ID!");
        }

        return especialista;
    }


    public List<EspecialistaDTO> listarTodos() throws Exception {
        return especialistaRepository.listar().stream().map(especialista -> this.objectMapper.convertValue(especialista,EspecialistaDTO.class)).toList();
    }


    public EspecialistaCreateDTO procurar(int id) throws Exception {
        EspecialistaCreateDTO especialista = this.objectMapper.convertValue(especialistaRepository.procurarPorId(id),EspecialistaCreateDTO.class);
        if (especialista == null) {
            throw new InformacaoNaoEncontrada("Não existe nenhum especialista com este ID!");
        }else {
            return especialista;
        }
    }

    public EspecialistaCreateDTO editar(Integer idEspecialista, EspecialistaCreateDTO dto) throws Exception {
           EspecialistaCreateDTO especEncontrado = this.procurar(idEspecialista);

           this.serviceUsuario.editar(especEncontrado.getId(),dto);

           Especialista especEditado = this.especialistaRepository.editar(especEncontrado.getIdEspecialista(),this.objectMapper.convertValue(dto,Especialista.class));

           return this.objectMapper.convertValue(especEditado,EspecialistaCreateDTO.class);

    }

//    public List<Relatorio> procurarRelatorioPorEmail(String email) {
//        return serviceRelatorio.listarTodos().stream().filter(relatorio -> {
//            if (Objects.isNull(relatorio.getAvaliador())) return false;
//            return relatorio.getAvaliador().getEmail().equals(email);
//        }).toList();
//    }
}
