package interfaces;

import exceptions.BancoDeDadosException;

import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

public interface IService<E> {

    void adicionar(E e) throws Exception;
    void  deletar(int id) throws SQLException;
    boolean editar(int id, E e) throws BancoDeDadosException;
    E procurarPorID(int id) throws SQLException;
    List<E> listarTodos() throws SQLException;
    E procurar(int id) throws SQLException;
}