package br.com.vemser.naturezaconectada.naturezaconectada.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.BancoDeDadosException;

import java.sql.SQLException;
import java.util.List;

public interface IService<E> {

    void adicionar(E e) throws Exception;
    void  deletar(int id) throws SQLException, BancoDeDadosException;
    boolean editar(int id, E e) throws BancoDeDadosException;
    E procurarPorID(int id) throws SQLException, BancoDeDadosException;
    List<E> listarTodos() throws SQLException, BancoDeDadosException;
    E procurar(int id) throws SQLException, BancoDeDadosException;
}