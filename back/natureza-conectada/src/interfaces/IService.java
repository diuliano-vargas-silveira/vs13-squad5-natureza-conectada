package interfaces;

import exceptions.BancoDeDadosException;

import java.util.List;
import java.util.Optional;

public interface IService<E> {

    void adicionar(E e) throws BancoDeDadosException;
    void  deletar(int id);
    boolean editar(int id, E e);
    E procurarPorID(int id);
    List<E> listarTodos();
    Optional<E> procurar(int id);
}