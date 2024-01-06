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
        Optional<Entrega> entregaCadastrada = procurarPorID(entrega.getId());
        if(entregaCadastrada.isPresent()){
            throw new ObjetoExistente("Está entrega já existe.");
        }
        entrega.setId(BancoDeDados.getNewID());
        BancoDeDados.entregas.add(entrega);
    }

    @Override
    public void deletar(int id) {
        Optional<Entrega> entregaDeletar = procurarPorID(id);
        if(entregaDeletar.isEmpty()){
            throw new InformacaoNaoEncontrada("Está entraga não existe.");
        }
        BancoDeDados.entregas.remove(entregaDeletar.get());
    }

    @Override
    public boolean editar(int id, Entrega entregaAtualizada) {
        Optional<Entrega> entregaCadastrada = procurarPorID(id);
        if(entregaCadastrada.isEmpty()){
            throw new InformacaoNaoEncontrada("Está entrega não existe.");
        }
        entregaAtualizada.setId(id);
        int indiceEntrega = BancoDeDados.entregas.indexOf(entregaCadastrada.get());
        BancoDeDados.entregas.set(indiceEntrega, entregaAtualizada);
        return true;
    }

    @Override
    public Optional procurarPorID(int id) {
        return BancoDeDados.entregas.stream()
        .filter(entrega -> entrega.getId() == id)
        .findFirst();
    }

    @Override
    public List listarTodos() {
        return BancoDeDados.entregas;
    }
}