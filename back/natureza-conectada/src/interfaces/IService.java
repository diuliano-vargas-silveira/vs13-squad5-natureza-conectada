package interfaces;

import java.util.List;
import java.util.Optional;

public interface IService<E> {

    void adicionar(E e);
    void  deletar(int id);
    boolean editar(int id, E e);
    Optional<E> procurarPorID(int id);
    List<E> listarTodos();
}