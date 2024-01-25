
package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.Exception;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.interfaces.IService;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EspecialistaRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ServiceEspecialista implements IService<Especialista> {

    private final EspecialistaRepository especialistaRepository;
    private final ServiceUsuario serviceUsuario;

    public ServiceEspecialista(EspecialistaRepository especialistaRepository, ServiceUsuario serviceUsuario) {
        this.especialistaRepository = especialistaRepository;
        this.serviceUsuario = serviceUsuario;
    }

    public void adicionar(Especialista especialista) throws Exception {
        Usuario usuarioCriado = serviceUsuario.adicionarUsuario(especialista);

        especialista.setId(usuarioCriado.getId());
        especialistaRepository.adicionar(especialista);
    }

    @Override
    public void deletar(int id) throws SQLException {
        Especialista especialista = procurarPorID(id);

        especialistaRepository.remover(id);
        serviceUsuario.remover(especialista.getId());
    }

    @Override
    public boolean editar(int id, Especialista especialistaEditado) throws Exception {
        serviceUsuario.editar(especialistaEditado.getId(), especialistaEditado);
        return especialistaRepository.editar(id, especialistaEditado);
    }

    @Override
    public Especialista procurarPorID(int id) throws SQLException {
        Especialista especialista = procurar(id);

        if (especialista == null) {
            throw new InformacaoNaoEncontrada("Não existe nenhum especialista com este ID!");
        }

        return especialista;
    }

    @Override
    public List<Especialista> listarTodos() throws Exception {
        return especialistaRepository.listar();
    }

    @Override
    public Especialista procurar(int id) throws SQLException {
        return especialistaRepository.procurarPorId(id);
    }

//    public List<Relatorio> procurarRelatorioPorEmail(String email) {
//        return serviceRelatorio.listarTodos().stream().filter(relatorio -> {
//            if (Objects.isNull(relatorio.getAvaliador())) return false;
//            return relatorio.getAvaliador().getEmail().equals(email);
//        }).toList();
//    }
}
