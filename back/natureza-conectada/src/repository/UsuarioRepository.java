package repository;

import enums.StatusEntrega;
import enums.TamanhoMuda;
import enums.TipoMuda;
import enums.TipoUsuario;
import exceptions.BancoDeDadosException;
import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements Repository<Integer, Usuario> {

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_USUARIO.nextval mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);

        if (resultado.next()) {
            return resultado.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Usuario adicionar(Usuario usuario) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            usuario.setId(proximoId.intValue());

            String sql = "INSERT INTO USUARIO\n" +
                    "(ID_USUARIO, NOME, EMAIL, SENHA, TIPO_USUARIO)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getTipoUsuario().toString());

            int resultado = stmt.executeUpdate();
            System.out.println("Usuário criado! Resultado: ".concat(String.valueOf(resultado)));
            return usuario;
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para adicionar o usuário ao banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM USUARIO WHERE ID_USUARIO = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id.intValue());

            int resultado = stmt.executeUpdate();
            System.out.println("Usuário foi removido! Resultado: ".concat(String.valueOf(resultado)));

            return resultado > 0;
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para remover seu usuário do banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    @Override
    public boolean editar(Integer id, Usuario usuario) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE USUARIO SET");
            sql.append(" EMAIL = ?,");
            sql.append(" NOME = ?,");
            sql.append(" SENHA = ?");
            sql.append(" WHERE ID_USUARIO = ?");

            PreparedStatement stmt = conexao.prepareStatement(sql.toString());
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, id);

            int resultado = stmt.executeUpdate();

            System.out.println("Seu usuário foi criado! Resultado: ".concat(String.valueOf(resultado)));
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado em editar seu usuário no banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public List<Usuario> listar() throws BancoDeDadosException {
        Connection conexao = null;
        List<Usuario> listaUsuario = new ArrayList<>();

        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Statement statment = conexao.createStatement();

            String sqlUsuario = "SELECT * FROM USUARIO";

            ResultSet usuarioTabela = statment.executeQuery(sqlUsuario);

            while (usuarioTabela.next()) {
                TipoUsuario tipoUsuario = TipoUsuario.valueOf(usuarioTabela.getString("TIPO_USUARIO"));

                Usuario usuario = null;
                switch (tipoUsuario) {
                    case CLIENTE -> {
                        usuario = getCliente(usuarioTabela);
                    }
                    case ESPECIALISTA -> {
                        usuario = getEspecialista(usuarioTabela);
                    }
                    case ADMIN -> {
                        usuario = getAdmin(usuarioTabela);
                    }
                }

                usuario.setTipoUsuario(tipoUsuario);

                listaUsuario.add(usuario);
            }

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao listar os usuários do banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return listaUsuario;
    }

    private Usuario getAdmin(ResultSet usuario) throws SQLException {
        Admin admin = new Admin();

        admin.setId(usuario.getInt("ID_USUARIO"));
        admin.setNome(usuario.getString("NOME"));
        admin.setEmail(usuario.getString("EMAIL"));

        return admin;
    }

    private Usuario getEspecialista(ResultSet usuario) throws SQLException {
        Especialista especialista = new Especialista();

        especialista.setId(usuario.getInt("ID_USUARIO"));
        especialista.setNome(usuario.getString("NOME"));
        especialista.setEmail(usuario.getString("EMAIL"));

        return especialista;
    }

    private Cliente getCliente(ResultSet usuario) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setId(usuario.getInt("ID_USUARIO"));
        cliente.setNome(usuario.getString("NOME"));
        cliente.setEmail(usuario.getString("EMAIL"));

        return cliente;
    }

    private void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }
}
