package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.Exception;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public interface IRepository<CHAVE, OBJETO> {
    Integer getProximoId(Connection connection) throws SQLException;
    OBJETO adicionar(OBJETO object) throws Exception;
    boolean remover(CHAVE id) throws Exception;

    boolean editar(CHAVE id, OBJETO objeto) throws Exception;
    List<OBJETO> listar() throws Exception;
}
