package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.config.ConexaoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Relatorio;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceCliente;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceEspecialista;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceMudas;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RelatorioRepository {

    private final ServiceCliente serviceCliente;
    private final ServiceEspecialista serviceEspecialista;
    private final ServiceMudas serviceMudas;
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public RelatorioRepository(ServiceCliente serviceCliente, ServiceEspecialista serviceEspecialista, ServiceMudas serviceMudas, ConexaoBancoDeDados conexaoBancoDeDados) {
        this.serviceCliente = serviceCliente;
        this.serviceEspecialista = serviceEspecialista;
        this.serviceMudas = serviceMudas;
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }

    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_RELATORIO.NEXTVAL mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if (resultado.next()) {
            return resultado.getInt("mysequence");
        }
        return null;
    }

    public Relatorio adicionar(Relatorio relatorio, Integer idCliente, Integer idEspecialista, Integer idMuda) throws ErroNoBancoDeDados {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            relatorio.setId(proximoId.intValue());

            String sql = "INSERT INTO RELATORIO\n" +
                    "(ID_RELATORIO, ID_CLIENTE, ID_MUDA, ESTADO_MUDA)\n" +
                    "VALUES(?, ?, ?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, relatorio.getId());
            stmt.setInt(2, idCliente);
            stmt.setInt(3, idMuda);
            stmt.setString(4, relatorio.getEstadoMuda());

            int resultado = stmt.executeUpdate();
            System.out.println("O relatório foi adicionado! Resultado: " + resultado);

            return relatorio;

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para adicionar o relatório ao banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    public void avaliarRelatorio (Relatorio relatorio) throws ErroNoBancoDeDados {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();


            String sql = "UPDATE RELATORIO SET" +
                    "ID_ESPECIALISTA = ?," +
                    "AVALIACAO = ?," +
                    "SUGESTOES = ?" +
                    "WHERE ID_RELATORIO = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,relatorio.getAvaliador().getId());
            stmt.setDouble(2, relatorio.getAvaliacaoEspecialista());
            stmt.setString(3, relatorio.getSugestoes());
            stmt.setInt(4, relatorio.getId());



            int resultado = stmt.executeUpdate();
            System.out.println("O relatório foi avaliado com sucesso! Resultado: " + resultado);



        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para avaliado o relatório ao banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    public boolean remover(Integer id) throws ErroNoBancoDeDados {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM RELATORIO WHERE ID_RELATORIO = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id.intValue());

            int resultado = stmt.executeUpdate();
            System.out.println("O relatório foi removido! Resultado: " + resultado);

            return resultado > 0;
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para remover o relatório do banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    public boolean editar(Integer id, Relatorio relatorio) throws ErroNoBancoDeDados {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE RELATORIO SET");
            sql.append(" ID_CLIENTE = ?, ID_MUDA = ?, ESTADO_MUDA = ?");
            sql.append(" WHERE ID_RELATORIO = ?");

            PreparedStatement stmt = conexao.prepareStatement(sql.toString());
            stmt.setInt(1, relatorio.getDono().getId());
            stmt.setInt(2, relatorio.getMuda().getId());
            stmt.setString(3, relatorio.getEstadoMuda());
            stmt.setInt(4, id.intValue());

            int resultado = stmt.executeUpdate();

            System.out.println("O relatório foi atualizado! Resultado: " + resultado);
            return resultado > 0;
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao editar o relatório no banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

//    public List<Relatorio> listarRelatorioPorCliente(Integer idClinte) throws Exception {
//    Connection conn = null;
//    List<Relatorio> listaRelatorios = new ArrayList<>();
//
//    try {
//        Cliente cliente = serviceCliente.procurarPorID(idClinte);
//        Especialista especialista;
//        Muda muda;
//        conn = conexaoBancoDeDados.getConnection();
//       String sql = "SELECT * FROM RELATORIO R RIGHT JOIN CLIENTE C ON R.ID_CLIENTE =  C.ID_CLIENTE" +
//               "WHERE C.ID_CLIENTE = ?";
//       PreparedStatement stm = conn.prepareStatement(sql);
//
//       stm.setInt(1,idClinte);
//
//       ResultSet resultado = stm.executeQuery();
//       while (resultado.next()){
//           especialista = this.serviceEspecialista.procurarPorID(resultado.getInt("ID_ESPECIALISTA"));
//           muda = this.serviceMudas.buscarPorId(resultado.getInt("ID_MUDA"));
//           Relatorio relatorio = new Relatorio();
//           relatorio.setDono(cliente);
//           relatorio.setId(resultado.getInt("ID-RELATORIO"));
//           relatorio.setAvaliacaoEspecialista(resultado.getDouble("AVALIACAO"));
//           relatorio.setAvaliador(especialista);
//           relatorio.setSugestoes(resultado.getString("SUGESTOES"));
//           relatorio.setEstadoMuda(resultado.getString("ESTADO_MUDA"));
//           relatorio.setMuda(muda);
//       }
//
//    }catch (SQLException ex){
//        System.out.println("Erro ao buscar Relatorios Erro:" + ex.getMessage());
//        throw new Exception(ex.getMessage());
//    }finally {
//        try {
//            fecharConexao(conn);
//        } catch (SQLException erro) {
//            System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
//            erro.printStackTrace();
//        }
//    }
//return listaRelatorios;
//    }

    public List<Relatorio> listar() throws ErroNoBancoDeDados {
        Connection conexao = null;
        List<Relatorio> listaRelatorios = new ArrayList<>();

        try {
            conexao = conexaoBancoDeDados.getConnection();
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
            throw new ErroNoBancoDeDados(erro.getMessage());
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

    public Relatorio procurarPorId(Integer id) throws ErroNoBancoDeDados {
        Connection conexao = null;
        Relatorio relatorioEncontrado = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();

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
            throw new ErroNoBancoDeDados(ex.getMessage());
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

    public List<Relatorio> buscarRelatorioAbertos () throws ErroNoBancoDeDados {
        Connection conn = null;
        List<Relatorio> relatoriosIncompletos = new ArrayList<>();
        try {
            conn = conexaoBancoDeDados.getConnection();
            String sql = "Select * from RELATORIO WHERE AVALIACAO IS NULL";
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                Relatorio relatorioEncontrado = new Relatorio();
                relatorioEncontrado.setId(resultSet.getInt("ID_RELATORIO"));
                relatorioEncontrado.getDono().setId(resultSet.getInt("ID_CLIENTE"));
                relatorioEncontrado.getMuda().setId(resultSet.getInt("ID_MUDA"));
                relatorioEncontrado.setEstadoMuda(resultSet.getString("ESTADO_MUDA"));
                relatoriosIncompletos.add(relatorioEncontrado);
            }

        }catch (SQLException e){
            System.out.println("Erro ao buscar relatorios em aberto, ERRO: "+ e.getMessage());
            throw new ErroNoBancoDeDados(e.getMessage());

        }finally {
            try {
                fecharConexao(conn);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return relatoriosIncompletos;
    }
}
