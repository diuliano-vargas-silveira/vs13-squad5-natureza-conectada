/* package services;

import database.BancoDeDados;
import exceptions.InformacaoNaoEncontrada;
import exceptions.ObjetoExistente;
import interfaces.IService;
import models.Contato;

import java.util.List;
import java.util.Optional;

public class ServiceContato implements IService<Contato> {
    @Override
    public void adicionar(Contato contato) {
        Optional<Contato> contatoExistente = procurar(contato.getId());

        if (contatoExistente.isPresent())
            throw new ObjetoExistente("Este contato já existe");

        contato.setId(BancoDeDados.gerarNovoIdContato());
        BancoDeDados.contatos.add(contato);
    }

    @Override
    public void deletar(int id) {
        Contato contatoExistente = procurarPorID(id);

        BancoDeDados.contatos.remove(contatoExistente);
    }

    @Override
    public boolean editar(int id, Contato contatoEditado) {
        Contato contatoExistente = procurarPorID(id);

        int indexContato = BancoDeDados.contatos.indexOf(contatoExistente);

        contatoExistente.setDescricao(contatoEditado.getDescricao());
        contatoExistente.setNumero(contatoEditado.getNumero());
        contatoExistente.setTipo(contatoEditado.getTipo().ordinal());

        BancoDeDados.contatos.set(indexContato, contatoEditado);

        return true;
    }

    @Override
    public Contato procurarPorID(int id) {
        Optional<Contato> contato = procurar(id);

        if (contato.isEmpty()) {
            throw new InformacaoNaoEncontrada("Não existe nenhum contato com este ID");
        }

        return contato.get();
    }

    @Override
    public List<Contato> listarTodos() {
        return BancoDeDados.contatos;
    }

    @Override
    public Optional<Contato> procurar(int id) {
        return BancoDeDados.contatos.stream().filter(contato -> contato.getId() == id).findFirst();
    }
}

 */