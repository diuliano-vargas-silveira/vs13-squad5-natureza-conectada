package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import enums.TipoMuda;
import exceptions.BancoDeDadosException;
import models.Muda;

public class MudaRepository implements Repository<Integer, Muda>{

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_MUDA.NEXTVAL mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if(resultado.next()){
            return resultado.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Muda adicionar(Muda muda) throws BancoDeDadosException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            muda.setId(proximoId.intValue());

            String sql = "INSERT INTO VS_13_EQUIPE_5.MUDA\n" +
             "(ID_MUDA,QUANTIDADE, NOME, NOME_CIENTIFICO, PORTE, AMBIENTE_IDEAL, DESCRICAO, TIPO_MUDA)\n" +
              "VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, proximoId);
            stmt.setInt(2,muda.getQuantidade());
            stmt.setString(3, muda.getNome());
            stmt.setString(4, muda.getNomeCientifico());
            stmt.setString(5, String.valueOf(muda.getPorte()));
            stmt.setString(6, muda.getAmbienteIdeal());
            stmt.setString(7, muda.getDescricao());
            stmt.setString(8, String.valueOf(muda.getTipo()));
            
            
            int resultado = stmt.executeUpdate();
            System.out.println("A muda foi adicionada! Resultado: ".concat(String.valueOf(resultado)));
            return muda;

        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado para adicionar á muda ao banco de dados.");
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
        try{
            conexao = ConexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM VS_13_EQUIPE_5.MUDA WHERE ID_MUDA = ?";
            
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id.intValue());

            int resultado = stmt.executeUpdate();
            System.out.println("A muda foi removida! Resultado: ".concat(String.valueOf(resultado)));
            
            return resultado > 0;
        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado para remover á muda do banco de dados.");
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
    public boolean editar(Integer id, Muda muda) throws BancoDeDadosException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE MUDA SET");
            sql.append(" NOME = ?,");
            sql.append(" NOME_CIENTIFICO = ?,");
            sql.append(" PORTE = ?,");
            sql.append(" AMBIENTE_IDEAL = ?,");
            sql.append(" DESCRICAO = ?,");
            sql.append(" TIPO_MUDA = ?");
            sql.append(" WHERE ID_MUDA = ?");
            
            PreparedStatement stmt = conexao.prepareStatement(sql.toString());


            stmt.setString(1, muda.getNome());
            stmt.setString(2, muda.getNomeCientifico());
            stmt.setString(3, String.valueOf(muda.getPorte()));
            stmt.setString(4, muda.getAmbienteIdeal());
            stmt.setString(5, muda.getDescricao());
            stmt.setString(6, String.valueOf(muda.getTipo()));
            stmt.setInt(7, id.intValue());
            
            
            int resultado = stmt.executeUpdate();

            System.out.println("A muda foi atualizada! Resultado: ".concat(String.valueOf(resultado)));
        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado em editar á muda no banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try{
                fecharConexao(conexao);
            }catch(SQLException erro){
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public List<Muda> listar() throws BancoDeDadosException {
        Connection conexao = null;
        List<Muda> listaMuda = new ArrayList<>();

        try{
            conexao = ConexaoBancoDeDados.getConnection();
            Statement statment = conexao.createStatement();

            String sqlEntrega = "SELECT * FROM VS_13_EQUIPE_5.MUDA";
            
            ResultSet mudaTabela = statment.executeQuery(sqlEntrega);

            while(mudaTabela.next()){
                Muda mudaAtual = new Muda();
                mudaAtual.setId(mudaTabela.getInt("ID_MUDA"));
                mudaAtual.setAmbienteIdeal(mudaTabela.getString("AMBIENTE_IDEAL"));
                mudaAtual.setDescricao(mudaTabela.getString("DESCRICAO"));
                mudaAtual.setNome(mudaTabela.getString("NOME"));
                mudaAtual.setTipo(TipoMuda.valueOf(mudaTabela.getString("TIPO_MUDA")));
                listaMuda.add(mudaAtual);
            }


        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado ao listar as mudas do banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        }finally {
            try{
                fecharConexao(conexao);
            }catch(SQLException erro){
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return listaMuda;
    }
    private void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }
}
