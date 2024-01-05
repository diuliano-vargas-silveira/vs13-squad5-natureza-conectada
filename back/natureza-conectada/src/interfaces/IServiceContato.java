package interfaces;

import models.Contato;
import models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IServiceContato {
    boolean adicionar(Contato contato);
    Contato deletar(int id);
    boolean editar(int id, Contato contato);
    Optional<Contato> procurarPorID(int id);
    List<Contato> listarTodos();
}