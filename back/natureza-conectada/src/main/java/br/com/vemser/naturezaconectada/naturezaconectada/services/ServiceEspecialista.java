
package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.EspecialistaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EspecialistaDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EspecialistaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceEspecialista {

    private final EspecialistaRepository especialistaRepository;
    private final ServiceUsuario serviceUsuario;
    private final ObjectMapper objectMapper;


    public EspecialistaCreateDTO adicionar(EspecialistaCreateDTO especialista) throws Exception {
        especialista.setTipoUsuario(TipoUsuario.ESPECIALISTA);
        especialista.setAtivo(Ativo.A);
        Especialista especialistaAdicionado = especialistaRepository.save(retornarEntidade(especialista));

        return retornarDto(especialistaAdicionado);
    }


    public void mudarAtivoEspecialista(int id) throws Exception {
        Especialista especialista = procurarPorIDEntidade(id);

        if (especialista.getAtivo() == Ativo.A) {
            especialista.setAtivo(Ativo.D);
        } else {
            especialista.setAtivo(Ativo.A);
        }

        this.especialistaRepository.save(especialista);

    }


    public EspecialistaCreateDTO editar(int id, EspecialistaDTO especialistaEditado) throws Exception {
        Especialista especialistaEncontrado = procurarPorIDEntidade(id);
        especialistaEncontrado.setNome(especialistaEditado.getNome());
        especialistaEncontrado.setEmail(especialistaEditado.getEmail());
        especialistaEncontrado.setEspecializacao(especialistaEditado.getEspecializacao());
        especialistaEncontrado.setDocumento(especialistaEditado.getDocumento());
        return retornarDto(especialistaRepository.save(especialistaEncontrado));
    }


    private Especialista procurarPorIDEntidade(int id) throws Exception {
        Especialista especialista = this.especialistaRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Especialista não encontrado no banco de dados"));


        return especialista;
    }
    public EspecialistaCreateDTO procurarPorIdCompleto(int id) throws Exception {
        Especialista especialista = this.especialistaRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Especialista não encontrado no banco de dados"));


        return retornarDto(especialista);
    }


    public List<EspecialistaDTO> listarTodos() throws Exception {
        return especialistaRepository.findAll().stream().map(especialista -> this.objectMapper.convertValue(especialista,EspecialistaDTO.class)).toList();
    }


    public EspecialistaDTO procurarPorIdBasica(int id) throws Exception {
        Especialista especialista = this.especialistaRepository.findById(id).orElseThrow(()->new RegraDeNegocioException("Especialista não encontrado"));

         return this.objectMapper.convertValue(especialista,EspecialistaDTO.class) ;
    }



    private EspecialistaCreateDTO retornarDto(Especialista especialista){
        return this.objectMapper.convertValue(especialista,EspecialistaCreateDTO.class);
    }
    private Especialista retornarEntidade(EspecialistaCreateDTO especialista){
        return this.objectMapper.convertValue(especialista,Especialista.class);
    }
}
