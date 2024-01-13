package repository;

import enums.Estados;
import exceptions.BancoDeDadosException;
import models.Contato;
import models.Especialista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialistaRepository implements Repository<Integer, Especialista> {

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ESPECIALISTA.NEXTVAL mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if (resultado.next()) {
            return resultado.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Especialista adicionar(Especialista especialista) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            especialista.setId(proximoId.intValue());

            String sql = "INSERT INTO VS_13_EQUIPE_5.ESPECIALISTA\n" +
                    "(ID_ESPECIALISTA, NOME, EMAIL, SENHA, CONTATO, DOCUMENTO, ESPECIALIZACAO, REGIAO_RESPONSAVEL)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, especialista.getId());
            stmt.setString(2, especialista.getNome());
            stmt.setString(3, especialista.getEmail());
            stmt.setString(4, especialista.getSenha());
            stmt.setObject(5, especialista.getContato());
            stmt.setString(6, especialista.getDocumento());
            stmt.setString(7, especialista.getEspecializacao());
            stmt.setString(8, especialista.getRegiaoResponsavel().toString());

            int resultado = stmt.executeUpdate();
            System.out.println("O especialista foi adicionado! Resultado: " + resultado);

            return especialista;

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para adicionar o especialista ao banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM VS_13_EQUIPE_5.ESPECIALISTA WHERE ID_ESPECIALISTA = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id.intValue());

            int resultado = stmt.executeUpdate();
            System.out.println("O especialista foi removido! Resultado: " + resultado);

            return resultado > 0;
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para remover o especialista do banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    @Override
    public boolean editar(Integer id, Especialista especialista) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE VS_13_EQUIPE_5.ESPECIALISTA SET");
            sql.append(" NOME = ?, EMAIL = ?, SENHA = ?, CONTATO = ?, DOCUMENTO = ?, ESPECIALIZACAO = ?, REGIAO_RESPONSAVEL = ?");
            sql.append(" WHERE ID_ESPECIALISTA = ?");

            PreparedStatement stmt = conexao.prepareStatement(sql.toString());
            stmt.setString(1, especialista.getNome());
            stmt.setString(2, especialista.getEmail());
            stmt.setString(3, especialista.getSenha());
            stmt.setObject(4, especialista.getContato());
            stmt.setString(5, especialista.getDocumento());
            stmt.setString(6, especialista.getEspecializacao());
            stmt.setString(7, especialista.getRegiaoResponsavel().toString());
            stmt.setInt(8, id.intValue());

            int resultado = stmt.executeUpdate();

            System.out.println("O especialista foi atualizado! Resultado: " + resultado);
            return resultado > 0;
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao editar o especialista no banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    @Override
    public List<Especialista> listar() throws BancoDeDadosException {
        Connection conexao = null;
        List<Especialista> listaEspecialistas = new ArrayList<>();

        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Statement statment = conexao.createStatement();

            String sqlEspecialista = "SELECT * FROM VS_13_EQUIPE_5.ESPECIALISTA";

            ResultSet especialistaTabela = statment.executeQuery(sqlEspecialista);

            while (especialistaTabela.next()) {
                Especialista especialistaAtual = new Especialista();
                especialistaAtual.setId(especialistaTabela.getInt("ID_ESPECIALISTA"));
                especialistaAtual.setNome(especialistaTabela.getString("NOME"));
                especialistaAtual.setEmail(especialistaTabela.getString("EMAIL"));
                especialistaAtual.setSenha(especialistaTabela.getString("SENHA"));
                especialistaAtual.setContato((Contato) especialistaTabela.getObject("CONTATO"));
                especialistaAtual.setDocumento(especialistaTabela.getString("DOCUMENTO"));
                especialistaAtual.setEspecializacao(especialistaTabela.getString("ESPECIALIZACAO"));
                especialistaAtual.setRegiaoResponsavel(Estados.valueOf(especialistaTabela.getString("REGIAO_RESPONSAVEL")));

                listaEspecialistas.add(especialistaAtual);
            }

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao listar os especialistas do banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return listaEspecialistas;
    }

    private void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }
}
