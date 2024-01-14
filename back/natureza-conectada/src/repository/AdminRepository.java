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
        try (Connection conexao = ConexaoBancoDeDados.getConnection()) {

            Integer proximoId = this.getProximoId(conexao);
            admin.setId(proximoId.intValue());

            String sql = "INSERT INTO VS_13_EQUIPE_5.ADMIN\n" +
                    "(ID_ADMIN, NOME, EMAIL)\n" +
                    "VALUES(?, ?, ?)\n";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, admin.getId());
                stmt.setString(2, admin.getNome());
                stmt.setString(3, admin.getEmail());

                int resultado = stmt.executeUpdate();
                System.out.println("O administrador foi adicionado! Resultado: " + resultado);

            } catch (SQLException erro) {
                System.out.println("ERRO: Algo deu errado para adicionar o administrador ao banco de dados.");
                throw new BancoDeDadosException(erro.getCause());
            }

            return admin;

        } catch (SQLException erro) {
            System.out.println("ERRO: Não foi possível obter a conexão com o banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        }
    }

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        try (Connection conexao = ConexaoBancoDeDados.getConnection()) {
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
        }
    }


    @Override
    public List<Admin> listar() throws BancoDeDadosException {
        List<Admin> admins = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

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
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return admins;
    }
}