package services;

import java.util.List;
import java.util.Optional;

import database.BancoDeDados;
import exceptions.InformacaoNaoEncontrada;
import exceptions.ObjetoExistente;
import interfaces.IService;
import models.Entrega;

public class ServiceEntrega implements IService<Entrega>{

    @Override
    public void adicionar(Entrega entrega) {
        Entrega entregaCadastrada = procurarPorID(entrega.getId());
        if(entregaCadastrada != null){
            throw new ObjetoExistente("Está entrega já existe.");
        }
        entrega.setId(BancoDeDados.gerarNovoIdEntrega());
        BancoDeDados.entregas.add(entrega);
    }

    @Override
    public void deletar(int id) {
        Entrega entregaDeletar = procurarPorID(id);
        if(entregaDeletar == null){
            throw new InformacaoNaoEncontrada("Está entrega não existe.");
        }
        BancoDeDados.entregas.remove(entregaDeletar);
    }

    @Override
    public boolean editar(int id, Entrega entregaAtualizada) {
        Entrega entregaCadastrada = procurarPorID(id);
        if(entregaCadastrada == null){
            throw new InformacaoNaoEncontrada("Está entrega não existe.");
        }
        entregaAtualizada.setId(id);
        int indiceEntrega = BancoDeDados.entregas.indexOf(entregaCadastrada);
        BancoDeDados.entregas.set(indiceEntrega, entregaAtualizada);
        return true;
    }

    @Override
    public Entrega procurarPorID(int id) {
        Optional<Entrega> entregaCadastrada = procurar(id);
        if(entregaCadastrada.isEmpty()){
            throw new InformacaoNaoEncontrada("Não existe nenhuma entrega com este ID!");
        }
        return entregaCadastrada.get();
    }

    @Override
    public List listarTodos() {
        return BancoDeDados.entregas;
    }

    @Override
    public Optional<Entrega> procurar(int id) {
        return BancoDeDados.entregas.stream()
        .filter(entrega -> entrega.getId() == id)
        .findFirst();
    }
}