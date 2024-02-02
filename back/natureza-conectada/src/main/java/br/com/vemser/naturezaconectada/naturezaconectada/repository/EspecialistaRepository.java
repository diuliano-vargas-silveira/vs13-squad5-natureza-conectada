package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.config.ConexaoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class EspecialistaRepository  {

    public Especialista adicionar(Especialista especialista) throws Exception {
        return null;
    }

    public void remover(Integer id) throws Exception {
    }

    public Especialista editar(Integer id, Especialista especialista) throws Exception {
        return null;
    }


    public List<Especialista> listar() throws Exception {
        return null;
    }

    public Especialista procurarPorId(int id) throws Exception {
        return null;
    }
}
