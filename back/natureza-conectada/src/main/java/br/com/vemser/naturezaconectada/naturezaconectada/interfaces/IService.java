package br.com.vemser.naturezaconectada.naturezaconectada.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;

import java.sql.SQLException;
import java.util.List;

public interface IService<E> {

    void adicionar(E e) throws java.lang.Exception;
    void  deletar(int id) throws java.lang.Exception;
    boolean editar(int id, E e) throws ErroNoBancoDeDados;
    E procurarPorID(int id) throws java.lang.Exception;
    List<E> listarTodos() throws SQLException, ErroNoBancoDeDados;
    E procurar(int id) throws SQLException, ErroNoBancoDeDados;
}