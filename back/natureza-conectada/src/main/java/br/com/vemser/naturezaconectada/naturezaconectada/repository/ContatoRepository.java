package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContatoRepository {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public ContatoRepository(ConexaoBancoDeDados conexaoBancoDeDados) {
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }

    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_CONTATO.nextval mysequence from DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    public Contato adicionar(Contato contato, Integer idUsuario) throws Exception {

        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();
            Integer proximoId = getProximoId(connection);
            contato.setId(proximoId.intValue());

            String sql = "INSERT INTO CONTATO " +
                    "(ID_CONTATO, ID_USUARIO, DESCRICAO, NUMERO, TIPO_CONTATO) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, proximoId);
            statement.setInt(2, contato.getIdCliente());
            statement.setString(3, contato.getDescricao());
            statement.setString(4, contato.getNumero());
            statement.setString(5, String.valueOf(contato.getTipo()));

            int res = statement.executeUpdate();
            System.out.println("Adicionado " + res + " contato");

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para adicionar o Contato ao banco de dados.");
            throw new Exception(erro.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return contato;
    }

    public Contato editar(Integer id, Contato contato) throws Exception {
        Connection connection = null;

        try {
            String sql = "UPDATE contato SET " +
                    "DESCRICAO = ?," +
                    "NUMERO = ?," +
                    " TIPO_CONTATO = ?" +
                    "where id_contato =  ?";
            connection = conexaoBancoDeDados.getConnection();

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, contato.getDescricao());
            stm.setString(2, contato.getNumero());
            stm.setString(3, contato.getTipo().toString());
            stm.setInt(4, id);
            int res = stm.executeUpdate();
            System.out.println("contatos editados :" + res);

            return contato;


        } catch (SQLException e) {
            System.out.println("ERRO: Algo deu errado ao editar o Contato no banco de dados.");
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                e.printStackTrace();
            }
        }
    }

    public List<Contato> listarTodos() throws SQLException {
        List<Contato> contatos = new ArrayList<>();
        Connection conexao = null;


        try {
            conexao = conexaoBancoDeDados.getConnection();
            Statement statement = conexao.createStatement();

            String sql = "SELECT * FROM CONTATO";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                Contato contato = new Contato();
                contato.setId(resultSet.getInt("ID_CONTATO"));
                contato.setIdCliente(resultSet.getInt("ID_USUARIO"));
                contato.setDescricao(resultSet.getString("DESCRICAO"));
                contato.setNumero(resultSet.getString("NUMERO"));
                contato.setTipo(Tipo.valueOf(resultSet.getString("TIPO_CONTATO")));
                contatos.add(contato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contatos;
    }

    public List<Contato> procurarContatoPorIdCliente(Integer idCliente) throws Exception {
        Connection connection = null;
        List<Contato> contatosDosClientes = new ArrayList<>();

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM CONTATO WHERE ID_USUARIO = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idCliente);

            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Contato contato = new Contato();
                contato.setId(resultado.getInt("ID_CONTATO"));
                contato.setIdCliente(resultado.getInt("ID_USUARIO"));
                contato.setDescricao(resultado.getString("DESCRICAO"));
                contato.setNumero(resultado.getString("NUMERO"));
                contato.setTipo(Tipo.valueOf(resultado.getString("TIPO_CONTATO")));

                contatosDosClientes.add(contato);

            }
            if (contatosDosClientes.isEmpty()) {
                throw new Exception("Este usuario não tem contatos");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: Este cliente não tem contatos no banco de dados.");
            throw new Exception(ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException erro) {
                System.out.println("ERRO: HOUVE UM PROBLEMA AO FECHAR A CONEXÃO");
            }
        }
        return contatosDosClientes;
    }

    public void excluir(Integer idContato) throws Exception {
        Connection connection = null;

        try {
            String sql = "delete from contato where id_contato = ?";
            connection = conexaoBancoDeDados.getConnection();

            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setInt(1, idContato);

            int res = stm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERRO AO EXCLUIR O CONTATO : " + ex.getMessage());
            throw new Exception(ex.getMessage());

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão com o banco de dados");
                e.printStackTrace();
            }
        }
    }
}
