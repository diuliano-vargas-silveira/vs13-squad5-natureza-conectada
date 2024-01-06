package services;

import database.BancoDeDados;
import exceptions.EnderecoExistente;
import exceptions.InformacaoNaoEncontrada;
import interfaces.IService;
import models.Endereco;

import java.util.List;
import java.util.Optional;

public class ServiceEndereco implements IService<Endereco> {

    @Override
    public void adicionar(Endereco endereco) {
        Optional<Endereco> enderecoExistente = procurarPorID(endereco.getId());

        if (enderecoExistente.isPresent())
            throw new EnderecoExistente();

        endereco.setId(BancoDeDados.getNewID());
        BancoDeDados.enderecos.add(endereco);
    }

    @Override
    public void deletar(int id) {
        Optional<Endereco> enderecoExistente = procurarPorID(id);

        if (enderecoExistente.isEmpty())
            throw new InformacaoNaoEncontrada("Este endereço não existe.");

        BancoDeDados.enderecos.remove(enderecoExistente.get());
    }


    @Override
    public boolean editar(int id, Endereco enderecoEditado) {
        Optional<Endereco> enderecoExistente = procurarPorID(id);

        if (enderecoExistente.isEmpty())
            throw new InformacaoNaoEncontrada("Este contato não existe.");

        enderecoEditado.setId(id);
        int indexContato = BancoDeDados.enderecos.indexOf(enderecoExistente.get());
        BancoDeDados.enderecos.set(indexContato, enderecoEditado);

        return true;
    }

    @Override
    public Optional<Endereco> procurarPorID(int id) {
        return BancoDeDados.enderecos.stream().filter(endereco -> endereco.getId() == id).findFirst();
    }

    @Override
    public List<Endereco> listarTodos() {
        return BancoDeDados.enderecos;
    }
}