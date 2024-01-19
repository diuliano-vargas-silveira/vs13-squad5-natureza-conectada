
package br.com.vemser.naturezaconectada.naturezaconectada.services;

import exceptions.BancoDeDadosException;
import exceptions.InformacaoNaoEncontrada;
import interfaces.IService;
import models.Especialista;
import models.Relatorio;
import models.Usuario;
import repository.EspecialistaRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ServiceEspecialista implements IService<Especialista> {

    EspecialistaRepository especialistaRepository = new EspecialistaRepository();
    ServiceUsuario serviceUsuario = new ServiceUsuario();


    public void adicionar(Especialista especialista) throws BancoDeDadosException {
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
    public boolean editar(int id, Especialista especialistaEditado) throws BancoDeDadosException {
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
    public List<Especialista> listarTodos() throws BancoDeDadosException {
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
