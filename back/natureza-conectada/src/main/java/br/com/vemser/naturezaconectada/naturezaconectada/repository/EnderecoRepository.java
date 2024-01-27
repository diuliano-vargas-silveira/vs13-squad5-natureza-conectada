package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Tipo;
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

    public Endereco adicionar(Endereco endereco, Integer idUsuario) throws Exception {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            endereco.setIdEndereco(proximoId.intValue());

            String sql = "INSERT INTO ENDERECO\n" +
                    "(ID_ENDERECO, ID_USUARIO, ID_ESTADO, CEP, LOGRADOURO, NUMERO, COMPLEMENTO, CIDADE, TIPO)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, proximoId);
            stmt.setInt(2, endereco.getIdCliente());
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
            throw new Exception(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    public boolean remover(Integer id) throws Exception {
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
            throw new Exception(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    public boolean editar(Integer id, Endereco endereco) throws Exception {
        Connection conexao = null;
        try {
            procurarPorIdEndereco(id);
            conexao = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE VS_13_EQUIPE_5.ENDERECO SET "
                    + "ID_ESTADO = ?, CEP = ?, LOGRADOURO = ?, NUMERO = ?, COMPLEMENTO = ?, CIDADE = ?, TIPO = ? WHERE ID_ENDERECO = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, endereco.getEstado().ordinal() + 1);
            stmt.setString(2, endereco.getCep());
            stmt.setString(3, endereco.getLogradouro());
            stmt.setString(4, endereco.getNumero());
            stmt.setString(5, endereco.getComplemento());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, String.valueOf(endereco.getTipo()));
            stmt.setInt(8, id);


            int resultado = stmt.executeUpdate();

            System.out.println("O endereço foi atualizado! Resultado: ".concat(String.valueOf(resultado)));
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado em editar o endereço no banco de dados.");
            throw new Exception(erro.getMessage());
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

    public List<Endereco> listar() throws Exception {
        List<Endereco> listaEndereco = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();
            Statement statment = conexao.createStatement();

            String sqlEndereco = "SELECT * FROM VS_13_EQUIPE_5.ENDERECO";


            ResultSet enderecoTabela = statment.executeQuery(sqlEndereco);

            while (enderecoTabela.next()) {

                Endereco enderecoAtual = new Endereco();
                enderecoAtual.setIdEndereco(enderecoTabela.getInt("ID_ENDERECO"));
                enderecoAtual.setIdCliente(enderecoTabela.getInt("ID_USUARIO"));
                enderecoAtual.setEstado(Estados.values()[enderecoTabela.getInt("ID_ESTADO") - 1]);
                enderecoAtual.setCep(enderecoTabela.getString("CEP"));
                enderecoAtual.setLogradouro(enderecoTabela.getString("LOGRADOURO"));
                enderecoAtual.setNumero(enderecoTabela.getString("NUMERO"));
                enderecoAtual.setComplemento(enderecoTabela.getString("COMPLEMENTO"));
                enderecoAtual.setCidade(enderecoTabela.getString("CIDADE"));
                enderecoAtual.setTipo(Tipo.valueOf(enderecoTabela.getString("TIPO")));
                listaEndereco.add(enderecoAtual);

            }
        } catch (SQLException e) {
            System.out.println("Não foi possível listar os endereços.");
            throw new Exception(e.getMessage());
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

    public Endereco procurarPorIdEndereco(Integer idEndereco) throws Exception {
        Endereco enderecoEncontrado = null;
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM VS_13_EQUIPE_5.ENDERECO WHERE ID_ENDERECO = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idEndereco);

            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                enderecoEncontrado = new Endereco();
                enderecoEncontrado.setIdEndereco(resultado.getInt("ID_ENDERECO"));
                enderecoEncontrado.setIdCliente(resultado.getInt("ID_USUARIO"));
                enderecoEncontrado.setEstado(Estados.values()[resultado.getInt("ID_ESTADO") - 1]);
                enderecoEncontrado.setCep(resultado.getString("CEP"));
                enderecoEncontrado.setLogradouro(resultado.getString("LOGRADOURO"));
                enderecoEncontrado.setNumero(resultado.getString("NUMERO"));
                enderecoEncontrado.setComplemento(resultado.getString("COMPLEMENTO"));
                enderecoEncontrado.setCidade(resultado.getString("CIDADE"));
                enderecoEncontrado.setTipo(Tipo.valueOf(resultado.getString("TIPO")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao procurar endereço por ID: " + e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return enderecoEncontrado;
    }

    public List<Endereco> procurarEnderecoPorCliente(Integer idCliente) throws Exception {
        List<Endereco> enderecosCliente = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM VS_13_EQUIPE_5.ENDERECO WHERE ID_USUARIO = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idCliente);

            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Endereco endereco = new Endereco();
                endereco.setIdEndereco(resultado.getInt("ID_ENDERECO"));
                endereco.setIdCliente(resultado.getInt("ID_USUARIO"));
                endereco.setEstado(Estados.values()[resultado.getInt("ID_ESTADO") - 1]);
                endereco.setCep(resultado.getString("CEP"));
                endereco.setLogradouro(resultado.getString("LOGRADOURO"));
                endereco.setNumero(resultado.getString("NUMERO"));
                endereco.setComplemento(resultado.getString("COMPLEMENTO"));
                endereco.setCidade(resultado.getString("CIDADE"));
                endereco.setTipo(Tipo.valueOf(resultado.getString("TIPO")));

                enderecosCliente.add(endereco);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar endereço por cliente: " + e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return enderecosCliente;
    }

    private void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }
}