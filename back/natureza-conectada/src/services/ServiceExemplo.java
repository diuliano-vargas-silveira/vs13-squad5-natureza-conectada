package services;

import database.BancoDeDados;
import exceptions.InformacaoNaoEncontrada;
import interfaces.IService;
import models.Cliente;
import models.Usuario;

import java.util.List;
import java.util.Optional;

public class ServiceExemplo implements IService {
    private BancoDeDados bancoDeDados;

    public ServiceExemplo(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    @Override
    public boolean adicionar(Object object) {
        Optional<Usuario> usuarioJaFoiCriado = procurarPorID(1);

        if (usuarioJaFoiCriado.isEmpty()) {
            // adiciona seu objeto
        }

        return false;
    }

    @Override
    public Object deletar(int id) {
        return null;
    }

    @Override
    public boolean editar(int id, Object object) {
        return false;
    }

    @Override
    public Optional<Usuario> procurarPorID(int id) {
        return BancoDeDados.usuarios.stream().filter(usuario -> usuario.getId() == id).findFirst();
    }

    @Override
    public List<Object> listarTodos() {
        return null;
    }
}
