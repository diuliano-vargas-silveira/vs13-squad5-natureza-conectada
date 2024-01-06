package services;

import database.BancoDeDados;
import exceptions.EspecialistaExistente;
import exceptions.InformacaoNaoEncontrada;
import interfaces.IService;
import models.Especialista;
import java.util.List;
import java.util.Optional;

public class ServiceEspecialista  implements IService<Especialista> {

    @Override
    public void adicionar(Especialista especialista) {
        Optional<Especialista> especialistaExistente = procurarPorID(especialista.getId());

        if (especialistaExistente.isPresent())
            throw new EspecialistaExistente();

        especialista.setID(BancoDeDados.gerarNovoIdEspecialista());
        BancoDeDados.especialistas.add(especialista);
        BancoDeDados.usuarios.add(especialista);
    }

    @Override
    public void deletar(int id) {
        Optional<Especialista> especialistaExistente = procurarPorID(id);

        if (especialistaExistente.isEmpty())
            throw new InformacaoNaoEncontrada("Este Especialista não existe.");

        BancoDeDados.especialistas.remove(especialistaExistente.get());
    }

    @Override
    public boolean editar(int id, Especialista especialistaEditado) {
        Optional<Especialista> especialistaExistente = procurarPorID(id);

        if (especialistaExistente.isEmpty())
            throw new InformacaoNaoEncontrada("Este Especialista não existe.");

        especialistaEditado.setID(id);
        int indexEspecialista = BancoDeDados.especialistas.indexOf(especialistaExistente.get());
        BancoDeDados.especialistas.set(indexEspecialista, especialistaEditado);
        return true;
    }

    @Override
    public Optional<Especialista> procurarPorID(int id) {
        return BancoDeDados.especialistas.stream().filter(especialista -> especialista.getId() == id).findFirst();
    }

    @Override
    public List<Especialista> listarTodos() {
        return BancoDeDados.especialistas;
    }
}
