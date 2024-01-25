package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import java.lang.Exception;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Admin;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository implements IRepository<Integer, Usuario> {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public UsuarioRepository(ConexaoBancoDeDados conexaoBancoDeDados) {
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }

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
    public Usuario adicionar(Usuario usuario) throws Exception {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            usuario.setId(proximoId);

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
            throw new Exception(erro.getMessage());
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
    public boolean remover(Integer id) throws Exception {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM USUARIO WHERE ID_USUARIO = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            int resultado = stmt.executeUpdate();
            System.out.println("Usuário foi removido! Resultado: ".concat(String.valueOf(resultado)));

            return resultado > 0;
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para remover seu usuário do banco de dados.");
            throw new Exception(erro.getMessage());
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
    public boolean editar(Integer id, Usuario usuario) throws Exception {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();

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

            System.out.println("Seu usuário foi editado! Resultado: ".concat(String.valueOf(resultado)));
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado em editar seu usuário no banco de dados.");
            throw new Exception(erro.getMessage());
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
    public List<Usuario> listar() throws Exception {
        Connection conexao = null;
        List<Usuario> listaUsuario = new ArrayList<>();

        try {
            conexao = conexaoBancoDeDados.getConnection();
            Statement statment = conexao.createStatement();

            String sqlUsuario = "SELECT * FROM USUARIO";

            ResultSet usuarioTabela = statment.executeQuery(sqlUsuario);

            while (usuarioTabela.next()) {
                TipoUsuario tipoUsuario = TipoUsuario.valueOf(usuarioTabela.getString("TIPO_USUARIO"));

                Usuario usuario = getUsuario(usuarioTabela, tipoUsuario);

                listaUsuario.add(usuario);
            }

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao listar os usuários do banco de dados.");
            throw new Exception(erro.getMessage());
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

    public Usuario procurarPorEmail(String email) throws Exception {
        Usuario usuario = null;
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM USUARIO WHERE EMAIL = ?";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, email);

                try (ResultSet res = pstmt.executeQuery()) {
                    if (res.next()) {
                        usuario = new Usuario();
                        usuario.setId(res.getInt("ID_USUARIO"));
                        usuario.setNome(res.getString("NOME"));
                        usuario.setEmail(res.getString("EMAIL"));
                        usuario.setSenha(res.getString("SENHA"));
                        usuario.setTipoUsuario(TipoUsuario.valueOf(res.getString("TIPO_USUARIO")));
                    }
                }
            }
            return usuario;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("ERRO: Algo deu errado ao buscar o usuário do banco de dados.");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public Usuario procurarPorId(int id) throws Exception {
        Usuario usuario = null;
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM USUARIO WHERE ID_USUARIO = ?";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setLong(1, id);

                try (ResultSet res = pstmt.executeQuery()) {
                    if (res.next()) {
                        usuario = new Usuario();
                        usuario.setId(res.getInt("ID_USUARIO"));
                        usuario.setNome(res.getString("NOME"));
                        usuario.setEmail(res.getString("EMAIL"));
                        usuario.setTipoUsuario(TipoUsuario.valueOf(res.getString("TIPO_USUARIO")));
                    } else {
                        throw new RegraDeNegocioException("Nenhum usuario encontrado para o Id: " + id);
                    }
                } catch (RegraDeNegocioException e) {
                    throw new RuntimeException(e);
                }
            }
            return usuario;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("ERRO: Algo deu errado ao buscar o usuário do banco de dados.");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private Usuario getUsuario(ResultSet usuarioTabela, TipoUsuario tipoUsuario) throws SQLException {
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

        return usuario;
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