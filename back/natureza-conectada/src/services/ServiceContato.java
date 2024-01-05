package services;

import database.BancoDeDados;
import exceptions.ContatoExistente;
import exceptions.InformacaoNaoEncontrada;
import interfaces.IService;
import models.Contato;

import java.util.List;
import java.util.Optional;

public class ServiceContato implements IService<Contato> {


    @Override
    public boolean adicionar(Contato contato) {
        Optional<Contato> contatoExistente = procurarPorID(contato.getId());

        if (contatoExistente.isPresent())
            throw new ContatoExistente();

        contato.setId(BancoDeDados.getNewID());
        BancoDeDados.contatos.add(contato);
        return true;
    }

    @Override
    public Contato deletar(int id) {
        Optional<Contato> contatoExistente = procurarPorID(id);

        if (contatoExistente.isEmpty())
            throw new InformacaoNaoEncontrada("Este contato não existe.");

        BancoDeDados.contatos.remove(contatoExistente.get());
        return contatoExistente.get();
    }

    @Override
    public boolean editar(int id, Contato contatoEditado) {
        Optional<Contato> contatoExistente = procurarPorID(id);

        if (contatoExistente.isEmpty())
            throw new InformacaoNaoEncontrada("Este contato não existe.");

        contatoEditado.setId(id);
        int indexContato = BancoDeDados.contatos.indexOf(contatoExistente.get());
        BancoDeDados.contatos.set(indexContato, contatoEditado);

        return true;
    }

    @Override
    public Optional<Contato> procurarPorID(int id) {
        return BancoDeDados.contatos.stream().filter(contato -> contato.getId() == id).findFirst();
    }

    @Override
    public List<Contato> listarTodos() {
        return BancoDeDados.contatos;
    }
}