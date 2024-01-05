package interfaces;

import models.Cliente;
import models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IService {
    boolean adicionar(Object object);
    Object deletar(int id);
    boolean editar(int id, Object object);
    Optional<Usuario> procurarPorID(int id);
    List<Object> listarTodos();
}
