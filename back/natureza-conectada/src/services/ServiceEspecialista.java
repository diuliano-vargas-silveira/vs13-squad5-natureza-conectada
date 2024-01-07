package services;

import database.BancoDeDados;
import exceptions.EspecialistaExistente;
import exceptions.InformacaoNaoEncontrada;
import exceptions.ObjetoExistente;
import interfaces.IService;
import models.Cliente;
import models.Especialista;
import java.util.List;
import java.util.Optional;

public class ServiceEspecialista  implements IService<Especialista> {

    @Override
    public void adicionar(Especialista especialista) {
        Optional<Especialista> especialistaExistente = procurarPorEmail(especialista.getEmail());

        if (especialistaExistente.isPresent())
            throw new ObjetoExistente("Especilista já existente com este email!");

        especialista.setId(BancoDeDados.gerarNovoIdEspecialista());
        BancoDeDados.usuarios.add(especialista);
        BancoDeDados.especialistas.add(especialista);
    }

    @Override
    public void deletar(int id) {
        Especialista especialistaExistente = procurarPorID(id);

        BancoDeDados.especialistas.remove(especialistaExistente);
    }

    @Override
    public boolean editar(int id, Especialista especialistaEditado) {
        Especialista especialistaExistente = procurarPorID(id);

        int indexEspecialista = BancoDeDados.especialistas.indexOf(especialistaExistente);

        especialistaExistente.setNome(especialistaEditado.getNome());
        especialistaExistente.setEmail(especialistaEditado.getEmail());
        especialistaExistente.setSenha(especialistaEditado.getSenha());
        especialistaExistente.setDocumento(especialistaEditado.getDocumento());
        especialistaExistente.setRegiaoResponsavel(especialistaEditado.getRegiaoResponsavel());
        especialistaExistente.setContato(especialistaEditado.getContato());

        BancoDeDados.especialistas.set(indexEspecialista, especialistaExistente);
        return true;
    }

    @Override
    public Especialista procurarPorID(int id) {
        Optional<Especialista> especialista = procurar(id);

        if (especialista.isEmpty()) {
            throw new InformacaoNaoEncontrada("Não existe nenhum especialista com este ID!");
        }

        return especialista.get();
    }

    @Override
    public List<Especialista> listarTodos() {
        return BancoDeDados.especialistas;
    }

    @Override
    public Optional<Especialista> procurar(int id) {
        return BancoDeDados.especialistas.stream().filter(cliente -> cliente.getId() == id).findFirst();
    }

    public Optional<Especialista> procurarPorEmail(String email) {
        return BancoDeDados.especialistas.stream().filter(especialista -> especialista.getEmail().equals(email)).findFirst();
    }
}
