package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.BancoDeDadosException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository implements Repository<Integer, Cliente> {

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
            cliente.setIdCliente(proximoId);

            String sql = "INSERT INTO CLIENTE\n" +
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
                throw new BancoDeDadosException(erro.getMessage());
            }

            return cliente;

        } catch (SQLException erro) {
            System.out.println("ERRO: Não foi possível obter a conexão com o banco de dados.");
            throw new BancoDeDadosException(erro.getMessage());
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
            String sql = "DELETE FROM CLIENTE WHERE id_cliente = ?";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int resultado = stmt.executeUpdate();
                System.out.println("O cliente foi removido! Resultado: " + resultado);

                return resultado > 0;
            } catch (SQLException e) {
                throw new BancoDeDadosException(e.getMessage());
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
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
    public boolean editar(Integer id, Cliente clienteEditado) throws BancoDeDadosException {
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();

            String sql_cliente = "UPDATE CLIENTE SET\n" +
                    " CPF = ? \n" +
                    " WHERE id_cliente = ? ";

            PreparedStatement stmt = conexao.prepareStatement(sql_cliente);
            stmt.setString(1, clienteEditado.getCpf());
            stmt.setInt(2, id);

            int res = stmt.executeUpdate();
            System.out.println("editarPessoa.res=" + res);

            return res > 0;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
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
    public List<Cliente> listar() throws BancoDeDadosException {
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
            throw new BancoDeDadosException(e.getMessage());
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

    public Cliente listarPorID(int id) throws BancoDeDadosException {
        Cliente cliente = new Cliente();
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM CLIENTE WHERE ID_CLIENTE = " + id;

            Statement stmt = conexao.createStatement();
            ResultSet resposta = stmt.executeQuery(sql);

            Statement stmt2 = conexao.createStatement();


            while (resposta.next()) {
                cliente.setCpf(resposta.getString("CPF"));
                String sql2 = "SELECT * FROM USUARIO WHERE ID_USUARIO = " + resposta.getInt("ID_USUARIO");
                ResultSet resposta2 = stmt2.executeQuery(sql2);
                while (resposta2.next()) {
                    cliente.setNome(resposta2.getString("NOME"));
                    cliente.setEmail(resposta2.getString("EMAIL"));
                    cliente.setId(resposta.getInt("ID_CLIENTE"));
                }
            }
            return cliente;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
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

    public Cliente listarPorEmail(String email) throws BancoDeDadosException {
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
            throw new BancoDeDadosException(e.getMessage());
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

    public void InserirMudaEmCliente(Integer idCliente, Integer idMuda) throws BancoDeDadosException {
        Connection connection = null;
        try {
            connection = ConexaoBancoDeDados.getConnection();
            int proximoId = this.getProximoIDMudaCliente(connection);
            String sql = "Insert Into CLIENTE_MUDA (ID_CLIENTE_MUDA,ID_MUDA,ID_CLIENTE)" +
                    "values(?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, proximoId);
            stm.setInt(2, idMuda);
            stm.setInt(3, idCliente);
            int resultado = stm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao Inserir Muda ao Cliente, ERRO: " + ex.getMessage());
            throw new BancoDeDadosException(ex.getMessage());

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException erro) {
                System.out.println("Erro ao fechar conexao do banco de Dados, Erro: " + erro.getMessage());
                erro.printStackTrace();
            }
        }

    }

    public Integer getProximoIDMudaCliente(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_CLIENTE_MUDA.NEXTVAL mysequence2 FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if (resultado.next()) {
            return resultado.getInt("mysequence2");
        }
        return null;
    }
}