package repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import enums.StatusEntrega;
import enums.TamanhoMuda;
import enums.TipoMuda;
import exceptions.BancoDeDadosException;
import models.Cliente;
import models.Entrega;
import models.Muda;

public class EntregaRepository implements Repository<Integer, Entrega>{

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ENTREGA.NEXTVAL mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if(resultado.next()){
            return resultado.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Entrega adicionar(Entrega entrega) throws BancoDeDadosException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            entrega.setId(proximoId.intValue());

            String sql = "INSERT INTO VS_13_EQUIPE_5.ENTREGA\n" +
             "(ID_ENTREGA, STATUS, ID_CLIENTE, ID_ENDERECO)\n" +
              "VALUES(?, ?, ?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, entrega.getId());
            stmt.setString(2, String.valueOf(entrega.getStatus()));
            stmt.setInt(3, entrega.getCliente().getId());
            stmt.setInt(4, entrega.getEnderecoDeEntrega().getId());
            
            int resultado = stmt.executeUpdate();
            System.out.println("A entrega foi adicionada! Resultado: ".concat(String.valueOf(resultado)));
            return entrega;

        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado para adicionar á entrega ao banco de dados.");
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
            String sql = "DELETE FROM VS_13_EQUIPE_5.ENTREGA WHERE ID_ENTREGA = ?";
            
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id.intValue());

            int resultado = stmt.executeUpdate();
            System.out.println("A entrega foi removida! Resultado: ".concat(String.valueOf(resultado)));
            
            return resultado > 0;
        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado para remover á entrega do banco de dados.");
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
    public boolean editar(Integer id, Entrega entrega) throws BancoDeDadosException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE VS_13_EQUIPE_5.ENTREGA SET");
            sql.append(" STATUS = ?");
            
            PreparedStatement stmt = conexao.prepareStatement(sql.toString());
            stmt.setString(1, String.valueOf(entrega.getStatus()));

            int resultado = stmt.executeUpdate();

            System.out.println("A entrega foi atualizada! Resultado: ".concat(String.valueOf(resultado)));
        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado em editar á entrega no banco de dados.");
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
    public List listar() throws BancoDeDadosException {
        Connection conexao = null;
        List<Entrega> listaEntrega = new ArrayList<>();

        try{
            conexao = ConexaoBancoDeDados.getConnection();
            Statement statment = conexao.createStatement();

            String sqlEntrega = "SELECT * FROM VS_13_EQUIPE_5.ENTREGA";
            
            ResultSet entregaTabela = statment.executeQuery(sqlEntrega);

            
            while(entregaTabela.next()){
                Entrega entregaAtual = new Entrega();
                entregaAtual.setId(entregaTabela.getInt("ID_ENTREGA"));
                entregaAtual.setStatus(StatusEntrega.valueOf(entregaTabela.getString("STATUS")));
                
                
                // buscar e filtrar mudas no banco de dados:
                // tabela com todos os id_muda que tem relacao com a entrega atual:
                String sqlRelacionamentoEntregaMuda = "(SELECT * FROM VS_13_EQUIPE_5.ENTREGA_MUDA EM WHERE (EM.ID_ENTREGA = " + entregaTabela.getInt("ID_ENTREGA") + "))";
                
                // tabela com os dados de todas as mudas que a entrega atual possui:
                String sqlSubQuery = "SELECT * FROM VS_13_EQUIPE_5.MUDA M\n"+
                 "FULL JOIN " + sqlRelacionamentoEntregaMuda + " REM WHERE (M.ID_MUDA = REM.ID_MUDA)";

                ResultSet filtroMuda = statment.executeQuery(sqlSubQuery);
                while (filtroMuda.next()) {

                    Muda mudaAtual = new Muda();
                    mudaAtual.setId(filtroMuda.getInt("ID_MUDA"));
                    mudaAtual.setPorte(TamanhoMuda.valueOf(filtroMuda.getString("Porte")));
                    mudaAtual.setTipo(TipoMuda.valueOf(filtroMuda.getString("Tipo_Muda")));
                    mudaAtual.setNome(filtroMuda.getString("Nome"));
                    mudaAtual.setNomeCientifico(filtroMuda.getString("Nome_Cientifico"));
                    mudaAtual.setAmbienteIdeal(filtroMuda.getString("Ambiente_Ideal"));
                    mudaAtual.setDescricao(filtroMuda.getString("Descricao"));

                    entregaAtual.getMudas().add(mudaAtual);
                }
                
                // buscar cliente no banco de dados:
                String sqlCliente = "SELECT * FROM VS_13_EQUIPE_5.CLIENTE C WHERE (C.ID_CLIENTE = " + entregaTabela.getInt("ID_CLIENTE")+ ")";
                ResultSet clienteTabela = statment.executeQuery(sqlCliente);
                
                Cliente clienteAtual = new Cliente();
                clienteAtual.setId(clienteTabela.getInt("ID_CLIENTE"));
                clienteAtual.setNome(clienteTabela.getString("Nome"));
                clienteAtual.setCpf(clienteTabela.getString("CPF"));
                entregaAtual.setCliente(clienteAtual);
                listaEntrega.add(entregaAtual);
            }

        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado ao listar as entregas do banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        }finally {
            try{
                fecharConexao(conexao);
            }catch(SQLException erro){
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return listaEntrega;
    }
    // Método(s) da classe:
    private void fecharConexao(Connection conexao) throws SQLException{
        if(conexao != null){
            conexao.close();
        }
    }
}
