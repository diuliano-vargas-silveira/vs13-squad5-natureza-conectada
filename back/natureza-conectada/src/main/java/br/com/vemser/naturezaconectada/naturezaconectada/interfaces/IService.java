package br.com.vemser.naturezaconectada.naturezaconectada.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.Exception;

import java.sql.SQLException;
import java.util.List;

public interface IService<E> {

    void adicionar(E e) throws java.lang.Exception;
    void  deletar(int id) throws SQLException, Exception;
    boolean editar(int id, E e) throws Exception;
    E procurarPorID(int id) throws SQLException, Exception;
    List<E> listarTodos() throws SQLException, Exception;
    E procurar(int id) throws SQLException, Exception;
}