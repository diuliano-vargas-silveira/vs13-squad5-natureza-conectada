package services;

import database.BancoDeDados;
import exceptions.InformacaoNaoEncontrada;
import exceptions.ObjetoExistente;
import interfaces.IService;
import models.Entrega;

import java.util.List;
import java.util.Optional;

public class ServiceEntrega implements IService<Entrega> {

    @Override
    public void adicionar(Entrega entrega) {
        Optional<Entrega> entregaExistente = procurar(entrega.getId());

        if (entregaExistente.isPresent()) {
            throw new ObjetoExistente("Está entrega já foi criada!");
        }

        entrega.setId(BancoDeDados.gerarNovoIdCliente());
        BancoDeDados.entregas.add(entrega);
        BancoDeDados.entregas.add(entrega);
    }

    @Override
    public void deletar(int id) {
        Entrega entrega = procurarPorID(id);

        BancoDeDados.entregas.remove(entrega);
    }

    @Override
    public boolean editar(int id, Entrega entregaEditada) {
        Entrega entrega = procurarPorID(id);

        int indexRelatorio = BancoDeDados.entregas.indexOf(entrega);

        entrega.setStatus(entregaEditada.getStatus());
        entrega.setMudas(entregaEditada.getMudas());
        entrega.setCliente(entregaEditada.getCliente());

        BancoDeDados.entregas.set(indexRelatorio, entrega);

        return true;
    }

    public boolean editarStatusDaEntrega(int id, Entrega entregaEditada){
        Entrega entrega = procurarPorID(id);

        if (entrega == null) {
            throw new InformacaoNaoEncontrada("Entrega não encontrada!");
        }

        entrega.atualizarEntrega(entregaEditada.getStatus());
        return true;
    }

    @Override
    public List<Entrega> listarTodos() {return BancoDeDados.entregas;
    }

    @Override
    public Entrega procurarPorID(int id) {
        Optional<Entrega> entrega = procurar(id);

        if (entrega.isEmpty()) {
            throw new InformacaoNaoEncontrada("Não existe nenhum contato com este ID");
        }

        return entrega.get();
    }

    @Override
    public Optional<Entrega> procurar(int id) {
        return BancoDeDados.entregas.stream().filter(entrega -> entrega.getId() == id).findFirst();
    }
}
