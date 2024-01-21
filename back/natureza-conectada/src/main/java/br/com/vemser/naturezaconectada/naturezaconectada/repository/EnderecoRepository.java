package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.BancoDeDadosException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnderecoRepository {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public EnderecoRepository(ConexaoBancoDeDados conexaoBancoDeDados) {
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }

    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ENDERECO.NEXTVAL mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if (resultado.next()) {
            return resultado.getInt("mysequence");
        }
        return null;
    }

    public Endereco adicionar(Endereco endereco, Integer idUsuario) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            endereco.setId(proximoId.intValue());

            String sql = "INSERT INTO ENDERECO\n" +
                    "(ID_ENDERECO, ID_USUARIO, ID_ESTADO, CEP, LOGRADOURO, NUMERO, COMPLEMENTO, CIDADE, TIPO)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, endereco.getId());
            stmt.setInt(2, idUsuario);
            stmt.setInt(3, endereco.getEstado().ordinal() + 1);
            stmt.setString(4, endereco.getCep());
            stmt.setString(5, endereco.getLogradouro());
            stmt.setString(6, endereco.getNumero());
            stmt.setString(7, endereco.getComplemento());
            stmt.setString(8, endereco.getCidade());
            stmt.setString(9, String.valueOf(endereco.getTipo()));


            int resultado = stmt.executeUpdate();
            System.out.println("O endereço foi adicionado! Resultado: " + resultado);
            return endereco;

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para adicionar o endereço ao banco de dados.");
            throw new BancoDeDadosException(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM VS_13_EQUIPE_5.ENDERECO WHERE ID_ENDERECO = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id.intValue());

            int resultado = stmt.executeUpdate();
            System.out.println("O endereço foi removido! Resultado: " + resultado);

            return resultado > 0;
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao remover o endreço do banco de dados.");
            throw new BancoDeDadosException(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    public boolean editar(Integer id, Endereco endereco) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE VS_13_EQUIPE_5.ENDERECO SET "
                    + "ESTADO = ?, CEP = ?, LOGRADOURO = ?, NUMERO = ?, COMPLEMENTO = ?, CIDADE = ? WHERE ID_ENDERECO = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, endereco.getEstado().ordinal() + 1);
            stmt.setString(2, endereco.getCep());
            stmt.setString(3, endereco.getLogradouro());
            stmt.setString(4, endereco.getNumero());
            stmt.setString(5, endereco.getComplemento());
            stmt.setString(6, endereco.getCidade());

            int resultado = stmt.executeUpdate();

            System.out.println("O endereço foi atualizado! Resultado: ".concat(String.valueOf(resultado)));
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado em editar o endereço no banco de dados.");
            throw new BancoDeDadosException(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return true;
    }

    public List<Endereco> listar() throws BancoDeDadosException {
        List<Endereco> listaEndereco = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();
            Statement statment = conexao.createStatement();

            String sqlEndereco = "SELECT * FROM VS_13_EQUIPE_5.ENDERECO";


            ResultSet enderecoTabela = statment.executeQuery(sqlEndereco);

            while (enderecoTabela.next()) {

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
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException e) {
                System.out.println("Não foi possível encerrar a conexão com o banco de dados.");
                e.printStackTrace();
            }
        }
        return listaEndereco;
    }


    private void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }
}