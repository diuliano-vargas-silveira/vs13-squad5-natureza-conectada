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
        Optional<Endereco> enderecoExistente = procurar(endereco.getId());

        if (enderecoExistente.isPresent())
            throw new EnderecoExistente();

        endereco.setId(BancoDeDados.gerarNovoIdEndereco());
        BancoDeDados.enderecos.add(endereco);
    }

    @Override
    public void deletar(int id) {
        Endereco endereco = procurarPorID(id);

        BancoDeDados.enderecos.remove(endereco);
    }


    @Override
    public boolean editar(int id, Endereco enderecoEditado) {
        Endereco endereco = procurarPorID(id);

        int indexContato = BancoDeDados.enderecos.indexOf(endereco);
        endereco.setCidade(endereco.getCidade());
        endereco.setCep(endereco.getCep());
        endereco.setComplemento(endereco.getComplemento());
        endereco.setEstado(endereco.getEstado());
        endereco.setLogradouro(endereco.getLogradouro());
        endereco.setTipo(endereco.getTipo().ordinal());

        BancoDeDados.enderecos.set(indexContato, enderecoEditado);

        return true;
    }

    @Override
    public Endereco procurarPorID(int id) {
        Optional<Endereco> endereco = procurar(id);

        if (endereco.isEmpty()) {
            throw new InformacaoNaoEncontrada("Não existe nenhum endereço com este ID!");
        }

        return endereco.get();
    }

    @Override
    public List<Endereco> listarTodos() {
        return BancoDeDados.enderecos;
    }

    @Override
    public Optional<Endereco> procurar(int id) {
        return BancoDeDados.enderecos.stream().filter(endereco -> endereco.getId() == id).findFirst();
    }
}