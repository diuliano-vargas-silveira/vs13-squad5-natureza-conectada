package interfaces;

import java.util.List;
import java.util.Optional;

public interface IService<E> {
    boolean adicionar(E e);
    E  deletar(int id);
    boolean editar(int id, E e);
    Optional<E> procurarPorID(int id);
    List<E> listarTodos();
}