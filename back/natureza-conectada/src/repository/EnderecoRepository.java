package repository;

import enums.Estados;
import exceptions.BancoDeDadosException;
import models.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoRepository implements Repository<Integer, Endereco> {
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ENDERECO.NEXTVAL mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if(resultado.next()){
            return resultado.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Endereco adicionar(Endereco endereco) throws BancoDeDadosException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            endereco.setId(proximoId.intValue());

            String sql = "INSERT INTO VS_13_EQUIPE_5.ENDERECO\n" +
                    "(ID_ENDERECO, ID_USUARIO, ID_ESTADO, CEP, LOGRADOURO, NUMERO, COMPLEMENTO, CIDADE)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, endereco.getId());
            stmt.setInt(2, endereco.getUsuario().getId());
            stmt.setInt(3, endereco.getEstado().ordinal() + 1);
            stmt.setString(4, endereco.getCep());
            stmt.setString(5, endereco.getLogradouro());
            stmt.setString(6, endereco.getNumero());
            stmt.setString(7, endereco.getComplemento());
            stmt.setString(8, endereco.getCidade());


            int resultado = stmt.executeUpdate();
            System.out.println("O endereço foi adicionado! Resultado: " + resultado);
            return endereco;

        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado para adicionar o endereço ao banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try{
                fecharConexao(conexao);
            }catch(SQLException erro){
                System.out.println("ERRO: Não foi possivel encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM VS_13_EQUIPE_5.ENDERECO WHERE ID_ENDERECO = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id.intValue());

            int resultado = stmt.executeUpdate();
            System.out.println("O endereço foi removido! Resultado: " + resultado);

            return resultado > 0;
        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado ao remover o endreço do banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try{
                fecharConexao(conexao);
            }catch(SQLException erro){
                System.out.println("ERRO: Não foi possivel encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    @Override
    public boolean editar(Integer id, Endereco endereco) throws BancoDeDadosException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE VS_13_EQUIPE_5.ENTREGA SET");
            sql.append(" ESTADO = ?");
            sql.append(" CEP = ?");
            sql.append(" LOGRADOURO = ?");
            sql.append(" NUMERO = ?");
            sql.append(" COMPLEMENTO = ?");
            sql.append(" CIDADE = ?");

            PreparedStatement stmt = conexao.prepareStatement(sql.toString());
            stmt.setInt(1, endereco.getEstado().ordinal() + 1);
            stmt.setString(2, endereco.getCep());
            stmt.setString(3, endereco.getLogradouro());
            stmt.setString(4, endereco.getNumero());
            stmt.setString(5, endereco.getComplemento());
            stmt.setString(6, endereco.getCidade());

            int resultado = stmt.executeUpdate();

            System.out.println("O endereço foi atualizado! Resultado: ".concat(String.valueOf(resultado)));
        }catch(SQLException erro){
            System.out.println("ERRO: Algo deu errado em editar o edenreço no banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try{
                fecharConexao(conexao);
            }catch(SQLException erro){
                System.out.println("ERRO: Não foi possivel encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public List<Endereco> listar() throws BancoDeDadosException {
        List<Endereco> listaEndereco = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Statement statment = conexao.createStatement();

            String sqlEndereco = "SELECT * FROM VS_13_EQUIPE_5.ENDERECO";


            ResultSet enderecoTabela = statment.executeQuery(sqlEndereco);

            while(enderecoTabela.next()) {

                Endereco enderecoAtual = new Endereco();
                enderecoAtual.setId(enderecoTabela.getInt("ID_ENDERECO"));
                // criar método para procurar usuário
                enderecoAtual.setEstado(Estados.values()[enderecoTabela.getInt("ID_ESTADO") - 1]);
                enderecoAtual.setCep(enderecoTabela.getString("CEP"));
                enderecoAtual.setLogradouro(enderecoTabela.getString("LOGRADOURO"));
                enderecoAtual.setNumero(enderecoTabela.getString("NUMERO"));
                enderecoAtual.setComplemento(enderecoTabela.getString("COMPLEMENTO"));
                enderecoAtual.setCidade(enderecoTabela.getString("CIDADE"));
                listaEndereco.add(enderecoAtual);

            }
        } catch (SQLException e) {
            System.out.println("Não foi possível listar os endereços.");
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try{
                fecharConexao(conexao);
            }catch(SQLException e){
                System.out.println("Não foi possível encerrar a conexão com o banco de dados.");
                e.printStackTrace();
            }
        }
        return listaEndereco;
    }

    private void fecharConexao(Connection conexao) throws SQLException{
        if(conexao != null){
            conexao.close();
        }
    }
}