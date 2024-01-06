package services;

import database.BancoDeDados;
import exceptions.InformacaoNaoEncontrada;
import exceptions.MudaExistente;
import interfaces.IService;
import models.Muda;

import java.util.List;
import java.util.Optional;

public class ServiceMudas implements IService<Muda> {

    @Override
    public void adicionar(Muda muda) {
       Optional<Muda> mudaNoBd = procurar(muda.getId());

        if (mudaNoBd.isPresent()){
           throw  new MudaExistente("Muda existente no Banco de dados!");
        }

        muda.setId(BancoDeDados.gerarNovoIDMudas());
        BancoDeDados.mudas.add(muda);
    }

    @Override
    public void deletar(int id) {
        Muda mudaASerDeletada = procurarPorID(id);

        BancoDeDados.mudas.remove(mudaASerDeletada);
    }

    @Override
    public boolean editar(int id, Muda muda) {
        Muda mudaAtualizada = procurarPorID(id);

        int indexMuda = BancoDeDados.mudas.indexOf(mudaAtualizada);

        mudaAtualizada.setNome(muda.getNome());
        mudaAtualizada.setAmbienteIdeal(muda.getAmbienteIdeal());
        mudaAtualizada.setPorte(muda.getPorte());
        mudaAtualizada.setDescricao(muda.getDescricao());
        mudaAtualizada.setTipo(muda.getTipo());
        mudaAtualizada.setNomeCientifico(muda.getNomeCientifico());

        BancoDeDados.mudas.set(indexMuda, mudaAtualizada);

        return true;
    }

    @Override
    public Muda procurarPorID(int id) {
        Optional<Muda> mudaPorID = procurar(id);

        if(mudaPorID.isEmpty()){
           throw  new InformacaoNaoEncontrada("Não existe nenhuma muda com este ID!");
        }

        return mudaPorID.get();
    }

    @Override
    public List<Muda> listarTodos() {
        return BancoDeDados.mudas;
    }

    @Override
    public Optional<Muda> procurar(int id) {
        return BancoDeDados.mudas.stream().filter(muda -> muda.getId() == id).findFirst();
    }
}
