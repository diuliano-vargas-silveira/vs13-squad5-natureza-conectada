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
                    "(ID_CLIENTE, NOME, EMAIL, SENHA, TIPO_USUARIO, CPF)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setInt(1, cliente.getId());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getSenha());
            stmt.setString(5, String.valueOf(cliente.getTipoUsuario()));
            stmt.setString(6, cliente.getCpf());

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
    public boolean editar(Integer id, Cliente cliente) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE VS_13_EQUIPE_5.CLIENTE SET ");
            sql.append(" NOME = ?,");
            sql.append(" EMAIL = ?,");
            sql.append(" SENHA = ?, ");
            sql.append(" CPF = ? ");
            sql.append(" WHERE id_pessoa = ? ");

            PreparedStatement stmt = conexao.prepareStatement(sql.toString());

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getSenha());
            stmt.setString(4, cliente.getCpf());
            stmt.setInt(5, id);

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

            String sql = "SELECT ID_CLIENTE, NOME, EMAIL, CPF FROM VS_13_EQUIPE_5.CLIENTE ";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(res.getInt("ID_CLIENTE"));
                cliente.setNome(res.getString("Nome"));
                cliente.setEmail(res.getString("Email"));
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

