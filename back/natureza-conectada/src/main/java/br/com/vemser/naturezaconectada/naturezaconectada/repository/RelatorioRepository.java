package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.config.ConexaoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Relatorio;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceCliente;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceEspecialista;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceMudas;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RelatorioRepository {

    public Relatorio adicionar(Relatorio relatorio, Integer idCliente, Integer idEspecialista, Integer idMuda) throws ErroNoBancoDeDados {
        return null;
    }

    public void avaliarRelatorio (Relatorio relatorio) throws ErroNoBancoDeDados {
    }

    public void remover(Integer id) throws ErroNoBancoDeDados {

    }

    public Relatorio editar(Integer id, Relatorio relatorio) throws ErroNoBancoDeDados {
        return null;
    }

    public List<Relatorio> listar() throws ErroNoBancoDeDados {
        return null;
    }

    public Relatorio procurarPorId(Integer id) throws ErroNoBancoDeDados {
        return null;
    }

    public List<Relatorio> buscarRelatorioAbertos() throws ErroNoBancoDeDados {
        return null;
    }
}
