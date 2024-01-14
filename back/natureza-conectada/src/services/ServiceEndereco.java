
package services;

import exceptions.BancoDeDadosException;
import exceptions.InformacaoNaoEncontrada;
import exceptions.ObjetoExistente;
import interfaces.IService;
import models.Endereco;
import repository.EnderecoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceEndereco implements IService<Endereco> {

    EnderecoRepository enderecoRepository = new EnderecoRepository();

    @Override
    public void adicionar(Endereco endereco) throws BancoDeDadosException {
        enderecoRepository.adicionar(endereco);
    }

    @Override
    public void deletar(int id) {
        Endereco endereco = procurarPorID(id);

//        BancoDeDados.enderecos.remove(endereco);
    }


    @Override
    public boolean editar(int id, Endereco enderecoEditado) {
//        Endereco endereco = procurarPorID(id);
//
//        int indexContato = BancoDeDados.enderecos.indexOf(endereco);
//        endereco.setCidade(endereco.getCidade());
//        endereco.setCep(endereco.getCep());
//        endereco.setComplemento(endereco.getComplemento());
//        endereco.setEstado(endereco.getEstado());
//        endereco.setLogradouro(endereco.getLogradouro());
//        endereco.setTipo(endereco.getTipo().ordinal());
//
//        BancoDeDados.enderecos.set(indexContato, enderecoEditado);

        return true;
    }

    @Override
    public Endereco procurarPorID(int id) {
//        Optional<Endereco> endereco = procurar(id);
//
//        if (endereco.isEmpty()) {
//            throw new InformacaoNaoEncontrada("Não existe nenhum endereço com este ID!");
//        }

        return new Endereco();
    }

    @Override
    public List<Endereco> listarTodos() {
        return new ArrayList<>();
    }

    @Override
    public Endereco procurar(int id) {
        return new Endereco();
    }
}
