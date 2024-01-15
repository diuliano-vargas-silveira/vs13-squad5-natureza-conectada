package repository;

import enums.Estados;
import exceptions.BancoDeDadosException;
import models.Especialista;
import models.Relatorio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioRepository implements Repository<Integer, Relatorio> {

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_RELATORIO.NEXTVAL mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if (resultado.next()) {
            return resultado.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Relatorio adicionar(Relatorio relatorio) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            relatorio.setId(proximoId.intValue());

            String sql = "INSERT INTO RELATORIO\n" +
                    "(ID_RELATORIO, ID_CLIENTE, ID_AVALIADOR, ID_MUDA, ESTADO_MUDA, SUGESTOES, AVALIACAO_ESPECIALISTA)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, relatorio.getId());
            stmt.setInt(2, relatorio.getDono().getId());
            stmt.setInt(3, relatorio.getAvaliador().getId());
            stmt.setInt(4, relatorio.getMuda().getId());
            stmt.setString(5, relatorio.getEstadoMuda());
            stmt.setString(6, relatorio.getSugestoes());
            stmt.setDouble(7, relatorio.getAvaliacaoEspecialista());

            int resultado = stmt.executeUpdate();
            System.out.println("O relatório foi adicionado! Resultado: " + resultado);

            return relatorio;

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para adicionar o relatório ao banco de dados.");
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
            String sql = "DELETE FROM RELATORIO WHERE ID_RELATORIO = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id.intValue());

            int resultado = stmt.executeUpdate();
            System.out.println("O relatório foi removido! Resultado: " + resultado);

            return resultado > 0;
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para remover o relatório do banco de dados.");
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
    public boolean editar(Integer id, Relatorio relatorio) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE RELATORIO SET");
            sql.append(" ID_CLIENTE = ?, ID_AVALIADOR = ?, ID_MUDA = ?, ESTADO_MUDA = ?, SUGESTOES = ?, AVALIACAO_ESPECIALISTA = ?");
            sql.append(" WHERE ID_RELATORIO = ?");

            PreparedStatement stmt = conexao.prepareStatement(sql.toString());
            stmt.setInt(1, relatorio.getDono().getId());
            stmt.setInt(2, relatorio.getAvaliador().getId());
            stmt.setInt(3, relatorio.getMuda().getId());
            stmt.setString(4, relatorio.getEstadoMuda());
            stmt.setString(5, relatorio.getSugestoes());
            stmt.setDouble(6, relatorio.getAvaliacaoEspecialista());
            stmt.setInt(7, id.intValue());

            int resultado = stmt.executeUpdate();

            System.out.println("O relatório foi atualizado! Resultado: " + resultado);
            return resultado > 0;
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao editar o relatório no banco de dados.");
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
    public List<Relatorio> listar() throws BancoDeDadosException {
        Connection conexao = null;
        List<Relatorio> listaRelatorios = new ArrayList<>();

        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Statement statement = conexao.createStatement();

            String sqlRelatorio = "SELECT * FROM RELATORIO";

            ResultSet relatorioTabela = statement.executeQuery(sqlRelatorio);

            while (relatorioTabela.next()) {
                Relatorio relatorioAtual = new Relatorio();
                relatorioAtual.setId(relatorioTabela.getInt("ID_RELATORIO"));
                relatorioAtual.setEstadoMuda(relatorioTabela.getString("ESTADO_MUDA"));
                relatorioAtual.setSugestoes(relatorioTabela.getString("SUGESTOES"));
                relatorioAtual.setAvaliacaoEspecialista(relatorioTabela.getDouble("AVALIACAO_ESPECIALISTA"));

                listaRelatorios.add(relatorioAtual);
            }

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao listar os relatórios do banco de dados.");
            throw new BancoDeDadosException(erro.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return listaRelatorios;
    }

    private void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }

    public Relatorio procurarPorId(Integer id) throws BancoDeDadosException {
        Connection conexao = null;
        Relatorio relatorioEncontrado = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM RELATORIO WHERE ID_RELATORIO = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id.intValue());

            ResultSet resposta = stmt.executeQuery();

            if (resposta.next()) {
                relatorioEncontrado.setId(resposta.getInt("ID_RELATORIO"));
                relatorioEncontrado.getDono().setId(resposta.getInt("ID_CLIENTE"));
                relatorioEncontrado.getAvaliador().setId(resposta.getInt("ID_AVALIADOR"));
                relatorioEncontrado.getMuda().setId(resposta.getInt("ID_MUDA"));
                relatorioEncontrado.setEstadoMuda(resposta.getString("ESTADO_MUDA"));
                relatorioEncontrado.setSugestoes(resposta.getString("SUGESTOES"));
                relatorioEncontrado.setAvaliacaoEspecialista(resposta.getDouble("AVALIACAO_ESPECIALISTA"));
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: Algo deu errado ao buscar o relatório no banco de dados.");
            throw new BancoDeDadosException(ex.getCause());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }

        return relatorioEncontrado;
    }
}
