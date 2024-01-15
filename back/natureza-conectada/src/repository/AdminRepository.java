package repository;

import exceptions.BancoDeDadosException;
import models.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository implements Repository<Integer, Admin> {
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_admin.nextval mysequence from DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Admin adicionar(Admin admin) throws BancoDeDadosException {
        Connection conexao = null;
        try {

            conexao = ConexaoBancoDeDados.getConnection();
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
            throw new BancoDeDadosException(erro.getCause());
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

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM VS_13_EQUIPE_5.ADMIN WHERE id_admin = ?";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int resultado = stmt.executeUpdate();
                System.out.println("O admin foi removido! Resultado: " + resultado);

                return resultado > 0;
            } catch (SQLException e) {
                throw new BancoDeDadosException(e.getCause());
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
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
    public boolean editar(Integer id, Admin adminEditado) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();
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
                throw new BancoDeDadosException(e.getCause());
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
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
    public List<Admin> listar() throws BancoDeDadosException {
        List<Admin> admins = new ArrayList<>();
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Statement stmt = conexao.createStatement();

            String sql = "SELECT * FROM ADMIN";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Admin admin = new Admin();
                admin.setId(res.getInt("ID_ADMIN"));
                admin.setNome(res.getString("NOME"));
                admin.setEmail(res.getString("EMAIL"));
                admins.add(admin);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
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

    public Admin procurarPorId(int id) throws BancoDeDadosException {
        Admin admin = null;
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();
            String sql = "SELECT ad.ID_ADMIN, u.NOME, u.EMAIL\n" +
                    "FROM\n" +
                    "\tUSUARIO u \n" +
                    "INNER JOIN \n" +
                    "\tADMIN ad ON (u.ID_USUARIO = ad.ID_ADMIN)\n" +
                    "WHERE \n" +
                    "\tad.ID_ADMIN = ? ";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet resposta = stmt.executeQuery();

            if (resposta.next()) {
                admin.setIdAdmin(resposta.getInt("ID_ADMIN"));
                admin.setNome(resposta.getString("NOME"));
                admin.setEmail(resposta.getString("EMAIL"));
            }


        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
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