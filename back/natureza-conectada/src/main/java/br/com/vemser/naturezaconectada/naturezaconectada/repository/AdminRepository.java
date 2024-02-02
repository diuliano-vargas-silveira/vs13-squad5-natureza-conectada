package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.config.ConexaoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Admin;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepository {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public AdminRepository(ConexaoBancoDeDados conexaoBancoDeDados) {
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }


    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_admin.nextval mysequence from DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }


    public Admin adicionar(Admin admin) throws Exception {
        Connection conexao = null;
        try {

            conexao = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            admin.setIdAdmin(proximoId.intValue());

            String sql = "INSERT INTO ADMIN\n" +
                    "(ID_ADMIN, ID_USUARIO)\n" +
                    "VALUES(?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setInt(1, admin.getIdAdmin());
            stmt.setInt(2, admin.getId());

            int resultado = stmt.executeUpdate();
            System.out.println("O administrador foi adicionado! Resultado: " + resultado);
        } catch (SQLException erro) {
            System.out.println("ERRO: Não foi possível obter a conexão com o banco de dados.");
            throw new Exception(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }

        return admin;
    }


    public boolean remover(Integer id) throws Exception {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM VS_13_EQUIPE_5.ADMIN WHERE id_admin = ?";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int resultado = stmt.executeUpdate();
                System.out.println("O admin foi removido! Resultado: " + resultado);

                return resultado > 0;
            } catch (SQLException e) {
                throw new Exception(e.getMessage());
            }

        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }


    public boolean editar(Integer id, Admin adminEditado) throws Exception {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            StringBuilder sql_admin = new StringBuilder();

            sql_admin.append("UPDATE ADMIN SET ");
            sql_admin.append(" NOME = ?, ");
            sql_admin.append(" EMAIL = ? ");
            sql_admin.append(" WHERE id_cliente = ? ");

            try (PreparedStatement stmt = conexao.prepareStatement(sql_admin.toString())) {
                stmt.setString(1, adminEditado.getNome());
                stmt.setString(2, adminEditado.getEmail());
                stmt.setInt(3, id);

                int res = stmt.executeUpdate();
                System.out.println("editarAdmin.res=" + res);

                return res > 0;
            } catch (SQLException e) {
                throw new Exception(e.getMessage());
            }

        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }


    public List<Admin> listar() throws Exception {
        List<Admin> admins = new ArrayList<>();
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            Statement stmt = conexao.createStatement();

            String sql = "SELECT * FROM ADMIN";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Admin admin = new Admin();
                admin.setIdAdmin(res.getInt("ID_ADMIN"));
                admins.add(admin);
            }
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return admins;
    }

    private void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }

    public Admin procurarPorId(int id) throws Exception {
        Admin admin = null;
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM ADMIN WHERE ID_ADMIN = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            try (ResultSet resposta = stmt.executeQuery()) {
                if (resposta.next()) {
                    admin = new Admin();
                    admin.setIdAdmin(resposta.getInt("ID_ADMIN"));
                    admin.setId(resposta.getInt("ID_USUARIO"));
                } else {
                    throw new RegraDeNegocioException("Nenhum admin encontrado para o Id: " + id);
                }
            } catch (RegraDeNegocioException e) {
                throw new RuntimeException(e);
            }


        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return admin;
    }


}