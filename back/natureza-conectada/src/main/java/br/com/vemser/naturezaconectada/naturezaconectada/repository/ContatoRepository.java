package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.BancoDeDadosException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatoRepository  {

    private Connection connection;

    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_CONTATO.nextval mysequence from DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    public Contato novoContato(Contato contato, Integer idUsuario) throws BancoDeDadosException {
        String sql = "INSERT INTO contato (ID_CONTATO,descricao, numero, tipo_contato,ID_USUARIO) VALUES (?,?, ?, ?,?)";
        try {

            connection = ConexaoBancoDeDados.getConnection();
            int proximoId = getProximoId(connection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,proximoId);
            preparedStatement.setString(2, contato.getDescricao());
            preparedStatement.setString(3, contato.getNumero());
            preparedStatement.setString(4, contato.getTipo().toString());
            preparedStatement.setInt(5,idUsuario);
            int res = preparedStatement.executeUpdate();
            System.out.println("Adicionado "+ res + " contato");
        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado para adicionar o Contato ao banco de dados.");
            throw new BancoDeDadosException(erro.getMessage());
        } finally {
            try{
                connection.close();
            }catch(SQLException erro){
                System.out.println("ERRO: Não foi possivel encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return contato;
    }

    public boolean editar(Integer id, Contato contato) throws BancoDeDadosException {
        try{
            String sql = "UPDATE contato SET " +
                    "DESCRICAO = ?," +
                    "NUMERO = ?," +
                    " TIPO_CONTATO = ?"+
                    "where id_contato =  ?"
                    ;
            connection = ConexaoBancoDeDados.getConnection();

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,contato.getDescricao());
            stm.setString(2,contato.getNumero());
            stm.setString(3,contato.getTipo().toString());
            stm.setInt(4,id);
            int res = stm.executeUpdate();
            System.out.println("contatos editados :" + res);

            return  res > 0;


        }catch(SQLException  e){
            System.out.println("ERRO: Algo deu errado ao editar o Contato no banco de dados.");
             throw new BancoDeDadosException(e.getMessage());
        }finally {
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


    public List<Contato> listar() throws SQLException {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contatos";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contato contato = new Contato();
                contato.setId(resultSet.getInt("id"));
                contato.setDescricao(resultSet.getString("descricao"));
                contato.setNumero(resultSet.getString("numero"));
                int tipoCodigo = resultSet.getInt("tipo");
                contato.setTipo(resultSet.getString("TIPO_CONTATO"));
                contatos.add(contato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contatos;
    }

    public List<Contato> contatosPorCliente(Integer idCliente) throws BancoDeDadosException{
        List<Contato> contatosDosClientes = new ArrayList<>();
        try{
            String sql = "SELECT * FROM CONTATO C RIGHT JOIN USUARIO U ON C.ID_USUARIO = U.ID_USUARIO where C.ID_USUARIO = ? ";
            connection = ConexaoBancoDeDados.getConnection();
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,idCliente);
            ResultSet  res = stm.executeQuery();

            while (res.next()){
                Contato contato = new Contato();
                contato.setTipo(res.getString("TIPO_CONTATO"));
                contato.setNumero(res.getString("NUMERO"));
                contato.setDescricao(res.getString("DESCRICAO"));
                contato.setId(res.getInt("ID_CONTATO"));
                contatosDosClientes.add(contato);

            }
            if(contatosDosClientes.isEmpty()){
                throw new BancoDeDadosException("Este usuario não tem contatos");
            }
        }catch (SQLException ex){
            System.out.println("ERRO: Este cliente não tem contatos no banco de dados.");
            throw new BancoDeDadosException(ex.getMessage());
        }finally {
            try {
                if(connection!=null){
                    connection.close();

                }
            }catch (SQLException erro){
                System.out.println("ERRO: HOUVE UM PROBLEMA AO FECHAR A CONEXÃO");
            }

        }
        return contatosDosClientes;
    }

    public void excluirContato(Integer idContato) throws BancoDeDadosException{
        try{
            String sql = "delete from contato where id_contato = ?";
            connection = ConexaoBancoDeDados.getConnection();

            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setInt(1,idContato);

            int res =stm.executeUpdate();


        }catch (SQLException   ex){
            System.out.println("ERRO AO EXCLUIR O CONTATO : " + ex.getMessage());
            throw new BancoDeDadosException(ex.getMessage());

        }finally {
            try{if(connection != null){
                connection.close();
            }
            }catch (SQLException e){
                System.out.println("Erro ao fechar a conexão com o banco de dados");
             e.printStackTrace();
            }

        }

    }
}
