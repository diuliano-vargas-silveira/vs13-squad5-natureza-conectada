package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.config.ConexaoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import java.lang.Exception;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Admin;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class UsuarioRepository {


    public Usuario adicionar(Usuario usuario) throws Exception {
        return null;
    }

    public void remover(Integer id) throws Exception {
    }

    public Usuario editar(Integer id, Usuario usuario) throws Exception {
        return null;
    }

    public List<Usuario> listar() throws Exception {
        return null;
    }

    public Usuario procurarPorEmail(String email) throws Exception {
        return null;
    }

    public Usuario procurarPorId(int id) throws Exception {
        return null;
    }

    public Usuario procurarAtivoPorId(int id) throws Exception {
        return null;
    }

    public List<Usuario> procurarUsuariosAtivos() throws Exception {
        return null;
    }

    private Usuario getUsuario(ResultSet usuarioTabela, TipoUsuario tipoUsuario) throws SQLException {
        return null;
    }

    private Usuario getAdmin(ResultSet usuario) throws SQLException {
        return null;
    }

    private Usuario getEspecialista(ResultSet usuario) throws SQLException {
        return null;
    }

}