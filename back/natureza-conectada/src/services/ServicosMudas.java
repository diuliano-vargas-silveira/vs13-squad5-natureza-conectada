package services;

import interfaces.IService;
import models.Muda;

import java.util.List;
import java.util.Optional;

public class ServicosMudas implements IService<Muda> {

    @Override
    public boolean adicionar(Muda o) {
        return false;
    }

    @Override
    public Muda deletar(int id) {
        return null;
    }

    @Override
    public boolean editar(int id, Muda o) {
        return false;
    }

    @Override
    public Optional procurarPorID(int id) {
        return Optional.empty();
    }

    @Override
    public List listarTodos() {
        return null;
    }
}
