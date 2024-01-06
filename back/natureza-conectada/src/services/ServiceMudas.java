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
       Optional<Muda> mudaNoBd = BancoDeDados.mudas.stream().filter(md -> md.getId() == muda.getId()).findFirst() ;
        if (mudaNoBd.isPresent()){
           throw  new MudaExistente("Muda existente no Banco de dados ");
        }else{
            muda.setId(BancoDeDados.gerarNovoIDMudas());
            BancoDeDados.mudas.add(muda);
        }


    }

    @Override
    public void deletar(int id) {
        Optional<Muda> mudaASerDeletada = procurarPorID(id);

        BancoDeDados.mudas.remove(mudaASerDeletada.get());




    }

    @Override
    public boolean editar(int id, Muda muda) {
        Optional<Muda> mudaAtualizada = procurarPorID(id);

        int indexMuda = BancoDeDados.mudas.indexOf(mudaAtualizada.get());

        mudaAtualizada.get().setNome(muda.getNome());
        mudaAtualizada.get().setAmbienteIdeal(muda.getAmbienteIdeal());
        mudaAtualizada.get().setPorte(muda.getPorte());
        mudaAtualizada.get().setDescricao(muda.getDescricao());
        mudaAtualizada.get().setTipo(muda.getTipo());
        mudaAtualizada.get().setNomeCientifico(muda.getNomeCientifico());
        BancoDeDados.mudas.set(indexMuda, mudaAtualizada.get());





        return true;
    }

    @Override
    public Optional<Muda> procurarPorID(int id) {
        Optional<Muda> mudaPorID = BancoDeDados.mudas.stream().filter(md -> md.getId() == id).findFirst();

        if(mudaPorID.isEmpty()){
           throw  new InformacaoNaoEncontrada("Não existe nenhuma muda com este ID");
        }

        return mudaPorID;
    }

    @Override
    public List<Muda> listarTodos() {

        return BancoDeDados.mudas ;
    }
}
