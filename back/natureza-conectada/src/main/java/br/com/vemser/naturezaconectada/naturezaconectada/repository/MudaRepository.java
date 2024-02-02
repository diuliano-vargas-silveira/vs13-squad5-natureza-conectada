package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.config.ConexaoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TamanhoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class MudaRepository  {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_MUDA.NEXTVAL mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if(resultado.next()){
            return resultado.getInt("mysequence");
        }
        return null;
    }


    public Muda adicionar(Muda muda) throws Exception {
        Connection conexao = null;
        try{
            conexao = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            muda.setId(proximoId.intValue());

            String sql = "INSERT INTO MUDA\n" +
             "(ID_MUDA,QUANTIDADE, NOME, NOME_CIENTIFICO, PORTE, ECOSSISTEMA, DESCRICAO, TIPO_MUDA)\n" +
              "VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, proximoId);
            stmt.setInt(2,muda.getQuantidade());
            stmt.setString(3, muda.getNome());
            stmt.setString(4, muda.getNomeCientifico());
            stmt.setString(5, String.valueOf(muda.getPorte()));
            stmt.setString(6, String.valueOf(muda.getEcossistema()));
            stmt.setString(7, muda.getDescricao());
            stmt.setString(8, String.valueOf(muda.getTipo()));


            int resultado = stmt.executeUpdate();
            System.out.println("A muda foi adicionada! Resultado: ".concat(String.valueOf(resultado)));
            return muda;

        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado para adicionar á muda ao banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        } finally {
            try{
                fecharConexao(conexao);
            }catch(SQLException erro){
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }


    public boolean mudarAtivoMuda(Integer id,Ativo ativo) throws Exception {
        Connection conexao = null;
        try{
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "UPDATE MUDA SET " +
                    "ATIVO = ? " +
                    "WHERE ID_MUDA = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1,String.valueOf(ativo));
            stmt.setInt(2, id);

            int resultado = stmt.executeUpdate();
            System.out.println("A muda foi removida! Resultado: ".concat(String.valueOf(resultado)));

            return resultado > 0;
        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado para remover á muda do banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        } finally {
            try{
                fecharConexao(conexao);
            }catch(SQLException erro){
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }




    public boolean editar(Integer id, Muda muda) throws Exception {
        Connection conexao = null;
        try{
            this.buscarPorId(id);
            conexao = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE MUDA SET");
            sql.append(" NOME = ?,");
            sql.append(" NOME_CIENTIFICO = ?,");
            sql.append(" PORTE = ?,");
            sql.append(" ECOSSISTEMA = ?,");
            sql.append(" DESCRICAO = ?,");
            sql.append(" TIPO_MUDA = ?,");
            sql.append("QUANTIDADE = ?");
            sql.append(" WHERE ID_MUDA = ?");

            PreparedStatement stmt = conexao.prepareStatement(sql.toString());


            stmt.setString(1, muda.getNome());
            stmt.setString(2, muda.getNomeCientifico());
            stmt.setString(3, String.valueOf(muda.getPorte()));
            stmt.setString(4, String.valueOf(muda.getEcossistema()));
            stmt.setString(5, muda.getDescricao());
            stmt.setString(6, String.valueOf(muda.getTipo()));
            stmt.setInt(7,muda.getQuantidade());
            stmt.setInt(8, id);


            int resultado = stmt.executeUpdate();


            log.info("A muda foi atualizada! Resultado: ".concat(String.valueOf(resultado)));
        }catch(SQLException erro){
            log.error("ERRO: Algo deu errado em editar á muda no banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        } finally {
            try{
                fecharConexao(conexao);
            }catch(SQLException erro){
                log.error("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return true;
    }

    public List<Muda> listarMudasAtivas() throws Exception {
        Connection conexao = null;
        List<Muda> listaMuda = new ArrayList<>();

        try{
            conexao = conexaoBancoDeDados.getConnection();
            Statement statment = conexao.createStatement();

            String sqlEntrega = "SELECT * FROM MUDA WHERE QUANTIDADE > 0 AND  ATIVO = 'A'";

            ResultSet mudaTabela = statment.executeQuery(sqlEntrega);

            while (mudaTabela.next()) {
                Muda mudaAtual = new Muda();
                mudaAtual.setId(mudaTabela.getInt("ID_MUDA"));
                mudaAtual.setEcossistema(Ecossistema.valueOf(mudaTabela.getString("ECOSSISTEMA")));
                mudaAtual.setDescricao(mudaTabela.getString("DESCRICAO"));
                mudaAtual.setNomeCientifico(mudaTabela.getString("NOME_CIENTIFICO"));
                mudaAtual.setNome(mudaTabela.getString("NOME"));
                mudaAtual.setTipo(TipoMuda.valueOf(mudaTabela.getString("TIPO_MUDA")));
                mudaAtual.setQuantidade(mudaTabela.getInt("QUANTIDADE"));
                mudaAtual.setPorte(TamanhoMuda.valueOf(mudaTabela.getString("PORTE")));
                mudaAtual.setAtivo(Ativo.valueOf(mudaTabela.getString("ATIVO")));
                listaMuda.add(mudaAtual);
            }
            if(listaMuda.isEmpty()){
                throw new ErroNoBancoDeDados("Não existe nenhuma muda no banco de dados");
            }

        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado ao listar as mudas do banco de dados.");
            throw new InformacaoNaoEncontrada(erro.getMessage());
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

    public List<Muda> listar() throws Exception {
        Connection conexao = null;
        List<Muda> listaMuda = new ArrayList<>();

        try{
            conexao = conexaoBancoDeDados.getConnection();
            Statement statment = conexao.createStatement();

            String sqlEntrega = "SELECT * FROM MUDA";

            ResultSet mudaTabela = statment.executeQuery(sqlEntrega);

               while (mudaTabela.next()) {
                   Muda mudaAtual = new Muda();
                   mudaAtual.setId(mudaTabela.getInt("ID_MUDA"));
                   mudaAtual.setEcossistema(Ecossistema.valueOf(mudaTabela.getString("ECOSSISTEMA")));
                   mudaAtual.setDescricao(mudaTabela.getString("DESCRICAO"));
                   mudaAtual.setNomeCientifico(mudaTabela.getString("NOME_CIENTIFICO"));
                   mudaAtual.setNome(mudaTabela.getString("NOME"));
                   mudaAtual.setTipo(TipoMuda.valueOf(mudaTabela.getString("TIPO_MUDA")));
                   mudaAtual.setQuantidade(mudaTabela.getInt("QUANTIDADE"));
                   mudaAtual.setPorte(TamanhoMuda.valueOf(mudaTabela.getString("PORTE")));
                   mudaAtual.setAtivo(Ativo.valueOf(mudaTabela.getString("ATIVO")));
                   listaMuda.add(mudaAtual);
               }
            if(listaMuda.isEmpty()){
                throw new ErroNoBancoDeDados("Não existe nenhuma muda no banco de dados");
            }

        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado ao listar as mudas do banco de dados.");
            throw new InformacaoNaoEncontrada(erro.getMessage());
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
    private void fecharConexao(Connection conexao) throws Exception {
        if (conexao != null) {
            conexao.close();
        }
    }

    public Muda buscarPorId(Integer idMuda) throws Exception {
        Connection conn = null;
        Muda muda = null;
        try {
            conn = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM MUDA WHERE ID_MUDA = ? ";
            PreparedStatement stm = conn.prepareStatement(sql);

            stm.setInt(1,idMuda);
            ResultSet resultado = stm.executeQuery();



            while (resultado.next()){
                muda = new Muda();
                muda.setId(resultado.getInt("ID_MUDA"));
                muda.setNome(resultado.getString("NOME"));
                muda.setPorte(TamanhoMuda.valueOf(resultado.getString("PORTE")));
                muda.setTipo(TipoMuda.valueOf(resultado.getString("TIPO_MUDA")));
                muda.setEcossistema(Ecossistema.valueOf(resultado.getString("ECOSSISTEMA")));
                muda.setNomeCientifico(resultado.getString("NOME_CIENTIFICO"));
                muda.setDescricao(resultado.getString("DESCRICAO"));
                muda.setQuantidade(resultado.getInt("QUANTIDADE"));
                muda.setAtivo(Ativo.valueOf(resultado.getString("ATIVO")));
            }
            if(muda == null){
                throw new ErroNoBancoDeDados("Não existe muda com este ID");
            }
        return muda;


        }catch (SQLException ex) {
            log.error("Erro ao fazer a consulta no anco "+ ex.getMessage());
            throw new InformacaoNaoEncontrada(ex.getMessage());

        }finally {
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException e){

                log.error("Erro ao fechar conexao do banco de dados, ERRO: " + e.getMessage());
                throw new ErroNoBancoDeDados(e.getMessage());
            }
        }

    }
    public Muda buscarPorEco(Ecossistema ecossistema) throws Exception {
        Connection conn = null;
        Muda muda = null;
        try {
            conn = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM MUDA WHERE ECOSSISTEMA = ? ";
            PreparedStatement stm = conn.prepareStatement(sql);

            stm.setString(1,String.valueOf(ecossistema));
            ResultSet resultado = stm.executeQuery();



            while (resultado.next()){
                muda = new Muda();
                muda.setId(resultado.getInt("ID_MUDA"));
                muda.setNome(resultado.getString("NOME"));
                muda.setPorte(TamanhoMuda.valueOf(resultado.getString("PORTE")));
                muda.setTipo(TipoMuda.valueOf(resultado.getString("TIPO_MUDA")));
                muda.setEcossistema(Ecossistema.valueOf(resultado.getString("ECOSSISTEMA")));
                muda.setNomeCientifico(resultado.getString("NOME_CIENTIFICO"));
                muda.setDescricao(resultado.getString("DESCRICAO"));
                muda.setQuantidade(resultado.getInt("QUANTIDADE"));
                muda.setAtivo(Ativo.valueOf(resultado.getString("ATIVO")));

            }
            if(muda == null){
                throw new ErroNoBancoDeDados("Não existe muda com este Ecossistema");
            }
            return muda;


        }catch (SQLException ex) {
            log.error("Erro ao fazer a consulta no banco "+ ex.getMessage());
            throw new InformacaoNaoEncontrada(ex.getMessage());

        }finally {
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException e){

                log.error("Erro ao fechar conexao do banco de dados, ERRO: " + e.getMessage());
                throw new ErroNoBancoDeDados(e.getMessage());
            }
        }

    }



    public List<Muda> obterMudasDaEntrega( int idEntrega) throws Exception {
        List<Muda> mudas = new ArrayList<>();
        Connection conexao = null;
        String sqlMudas = "SELECT em.QUANTIDADE AS QTD_ENTREGA, m.* " +
                "FROM VS_13_EQUIPE_5.ENTREGA_MUDA em " +
                "JOIN VS_13_EQUIPE_5.MUDA m ON em.ID_MUDA = m.ID_MUDA " +
                "WHERE em.ID_ENTREGA = ?";

        try  {
            conexao = conexaoBancoDeDados.getConnection();
            PreparedStatement statementMudas = conexao.prepareStatement(sqlMudas);
            statementMudas.setInt(1, idEntrega);
            ResultSet resultadoMudas = statementMudas.executeQuery();

            while (resultadoMudas.next()) {
                Muda mudaAtual = new Muda();
                mudaAtual.setId(resultadoMudas.getInt("ID_MUDA"));
                mudaAtual.setQuantidade(resultadoMudas.getInt("QTD_ENTREGA"));
                mudaAtual.setPorte(TamanhoMuda.valueOf(resultadoMudas.getString("PORTE")));
                mudaAtual.setTipo(TipoMuda.valueOf(resultadoMudas.getString("TIPO_MUDA")));
                mudaAtual.setNome(resultadoMudas.getString("NOME"));
                mudaAtual.setNomeCientifico(resultadoMudas.getString("NOME_CIENTIFICO"));
                mudaAtual.setEcossistema(Ecossistema.valueOf(resultadoMudas.getString("ECOSSISTEMA")));
                mudaAtual.setDescricao(resultadoMudas.getString("DESCRICAO"));
                mudaAtual.setAtivo(Ativo.valueOf(resultadoMudas.getString("ATIVO")));
                mudas.add(mudaAtual);
            }
        }catch (Exception ex){

            throw new RegraDeNegocioException("Erro ao buscar as mudas da entrega");
        }finally {
            if(conexao != null){
                try {
                    conexao.close();
                }catch (Exception e){
                    throw new ErroNoBancoDeDados("Erro ao fechar a conexão");

                }
            }
        }
        return mudas;
    }
}
