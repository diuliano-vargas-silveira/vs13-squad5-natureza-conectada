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
        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            cliente.setId(proximoId.intValue());

            String sql = "INSERT INTO VS_13_EQUIPE_5.CLIENTE\n" +
                    "(ID_CLIENTE, ID_USUARIO, CPF)\n" +
                    "VALUES(?, ?, ?)\n";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, cliente.getIdCliente());
                stmt.setInt(2, cliente.getId());
                stmt.setString(3, cliente.getCpf());

                int resultado = stmt.executeUpdate();
                System.out.println("O usuário foi adicionado! Resultado: " + resultado);

            } catch (SQLException erro) {
                System.out.println("ERRO: Algo deu errado para adicionar o usuário ao banco de dados.");
                throw new BancoDeDadosException(erro.getCause());
            }

            return cliente;

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
    }

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM VS_13_EQUIPE_5.CLIENTE WHERE id_cliente = ?";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int resultado = stmt.executeUpdate();
                System.out.println("O cliente foi removido! Resultado: " + resultado);

                return resultado > 0;
            } catch (SQLException e) {
                throw new BancoDeDadosException(e.getCause());
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
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
            sql_cliente.append(" WHERE id_cliente = ? ");

            try (PreparedStatement stmt = conexao.prepareStatement(sql_cliente.toString())) {
                stmt.setString(1, clienteEditado.getCpf());
                stmt.setInt(2, id);

                int res = stmt.executeUpdate();
                System.out.println("editarPessoa.res=" + res);

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
    public List<Cliente> listar() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();

            Statement stmt = conexao.createStatement();
            ResultSet res = stmt.executeQuery("SELECT c.ID_CLIENTE, c.CPF, u.NOME, u.EMAIL\n" +
                    "FROM VS_13_EQUIPE_5.CLIENTE c\n" +
                    "INNER JOIN VS_13_EQUIPE_5.USUARIO u ON (c.ID_USUARIO = u.ID_USUARIO )");

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
                } catch (SQLException erro) {
                    System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                    erro.printStackTrace();
                }
            }
            return clientes;

    }

    public boolean getId(Cliente cliente) throws BancoDeDadosException {

        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();
            String sql = "SELECT ID_CLIENTE FROM VS_13_EQUIPE_5.CLIENTE WHERE ID_CLIENTE = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, cliente.getIdCliente());

            ResultSet resposta = stmt.executeQuery();

            if(resposta.next()){
                return true;
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
        finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return false;
    }

    private Cliente getCliente(ResultSet usuario) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setIdCliente(usuario.getInt("ID_CLIENTE"));
        cliente.setNome(usuario.getString("NOME"));
        cliente.setEmail(usuario.getString("EMAIL"));
        cliente.setCpf(usuario.getString("CPF"));

        return cliente;
    }

    private void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }

    public Cliente listarPorEmail(String email) throws SQLException {
        Cliente cliente = null;
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();

            String sqlUsuario = "SELECT c.ID_CLIENTE, c.CPF, u.NOME, u.EMAIL\n" +
                    "FROM\n" +
                    "\tUSUARIO u \n" +
                    "INNER JOIN \n" +
                    "\tCLIENTE c ON (u.ID_USUARIO = c.ID_CLIENTE)\n" +
                    "WHERE \n" +
                    "\tu.EMAIL = ? ";

            PreparedStatement preparedStatement = conexao.prepareStatement(sqlUsuario);

            preparedStatement.setString(1, email);

            ResultSet clienteRes = preparedStatement.executeQuery();

            if (clienteRes.next()) {
                cliente = getCliente(clienteRes);

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
        return cliente;
    }

}