package repository;

import exceptions.BancoDeDadosException;
import models.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository implements Repository<Integer, Cliente>{

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_cliente.nextval mysequence from DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Cliente adicionar(Cliente cliente) throws BancoDeDadosException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(conexao);
            cliente.setId(proximoId.intValue());

            String sql = "INSERT INTO VS_13_EQUIPE_5.CLIENTE\n" +
                    "(ID_CLIENTE, ID_USUARIO, CPF)\n" +
                    "VALUES(?, ?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setInt(1, cliente.getId());
            stmt.setInt(2, cliente.getIdUsuario());
            stmt.setString(3, cliente.getCpf());

            int resultado = stmt.executeUpdate();
            System.out.println("O usuário foi adicionado! Resultado: ".concat(String.valueOf(resultado)));
            return cliente;

        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado para adicionar o usuário ao banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try{
                fecharConexao(conexao);
            }catch(SQLException erro){
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

            String sql = "DELETE FROM VS_13_EQUIPE_5.CLIENTE WHERE id_pessoa = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setInt(1, id.intValue());

            int resultado = stmt.executeUpdate();
            System.out.println("O cliente foi removido! Resultado: ".concat(String.valueOf(resultado)));

            return resultado > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean editar(Integer id, Cliente clienteEditado) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql_cliente = new StringBuilder();

            sql_cliente.append("UPDATE VS_13_EQUIPE_5.CLIENTE SET ");
            sql_cliente.append(" CPF = ? ");
            sql_cliente.append(" WHERE id_pessoa = ? ");

            PreparedStatement stmt = conexao.prepareStatement(sql_cliente.toString());

            stmt.setString(1, clienteEditado.getCpf());
            stmt.setInt(2, id);

            int res = stmt.executeUpdate();
            System.out.println("editarPessoa.res=" + res);

            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Cliente> listar() throws BancoDeDadosException {
        List<Cliente> clientes = new ArrayList<>();
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Statement stmt = conexao.createStatement();

            String sql = "SELECT c.ID_CLIENTE, c.CPF, u.NOME, u.EMAIL\n" +
                    "FROM VS_13_EQUIPE_5.CLIENTE c\n" +
                    "INNER JOIN VS_13_EQUIPE_5.USUARIO u ON (c.ID_USUARIO = u.ID_USUARIO )";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(res.getInt("ID_CLIENTE"));
                cliente.setNome(res.getString("NOME"));
                cliente.setEmail(res.getString("EMAIL"));
                cliente.setCpf(res.getString("CPF"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return clientes;
    }

    public List<Cliente> listarPorID(int id) throws BancoDeDadosException {
        List<Cliente> clientes = new ArrayList<>();
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Statement stmt = conexao.createStatement();

            String sql = "SELECT c.ID_CLIENTE, c.CPF, u.NOME, u.EMAIL\n" +
                    "FROM VS_13_EQUIPE_5.CLIENTE c\n" +
                    "INNER JOIN VS_13_EQUIPE_5.USUARIO u ON (c.ID_USUARIO = u.ID_USUARIO )\n" +
                    "WHERE ID_CLIENTE = ?";

            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setInt(1, id);

                try (ResultSet res = pstmt.executeQuery()) {
                    while (res.next()) {
                        Cliente cliente = new Cliente();
                        cliente.setId(res.getInt("ID_CLIENTE"));
                        cliente.setNome(res.getString("NOME"));
                        cliente.setEmail(res.getString("EMAIL"));
                        cliente.setCpf(res.getString("CPF"));
                        clientes.add(cliente);
                    }
                }
            }

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(res.getInt("ID_CLIENTE"));
                cliente.setNome(res.getString("NOME"));
                cliente.setEmail(res.getString("EMAIL"));
                cliente.setCpf(res.getString("CPF"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return clientes;
    }

    private void fecharConexao(Connection conexao) throws SQLException{
        if(conexao != null){
            conexao.close();
        }
    }
}
