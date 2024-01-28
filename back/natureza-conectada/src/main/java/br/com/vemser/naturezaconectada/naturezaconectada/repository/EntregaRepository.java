package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.*;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceCliente;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EntregaRepository {
    private final ServiceCliente serviceCliente;
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public EntregaRepository(ServiceCliente serviceCliente, ConexaoBancoDeDados conexaoBancoDeDados) {
        this.serviceCliente = serviceCliente;
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }

    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ENTREGA.NEXTVAL mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if (resultado.next()) {
            return resultado.getInt("mysequence");
        }
        return null;
    }


    public Entrega adicionar(Entrega entrega, Integer idEndereco) throws ErroNoBancoDeDados {
        try (Connection conexao = conexaoBancoDeDados.getConnection()) {
            entrega.setId(getProximoId(conexao));

            String sqlEntrega = "INSERT INTO VS_13_EQUIPE_5.ENTREGA (ID_ENTREGA, ID_CLIENTE, ID_ENDERECO, STATUS) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statementEntrega = conexao.prepareStatement(sqlEntrega)) {
                statementEntrega.setInt(1, entrega.getId());
                statementEntrega.setInt(2, entrega.getCliente().getId());
                statementEntrega.setInt(3, idEndereco);
                statementEntrega.setString(4, String.valueOf(entrega.getStatus()));
                int resultadoEntrega = statementEntrega.executeUpdate();

                adicionarMudas(conexao, entrega.getId(), entrega.getMudas());

                entrega.setMudas(obterMudasDaEntrega(conexao, entrega.getId()));
                entrega.setCliente(obterClienteDaEntrega(conexao, entrega.getId()));

                System.out.println("A entrega foi adicionada! Resultado: " + resultadoEntrega);

                return entrega;
            }
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao adicionar a entrega ao banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        }
    }

    private void adicionarMudas(Connection conexao, int idEntrega, List<Muda> mudas) throws SQLException {
        String sqlMuda = "INSERT INTO VS_13_EQUIPE_5.ENTREGA_MUDA (ID_ENTREGA_MUDA, ID_MUDA, ID_ENTREGA, QUANTIDADE) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statementMuda = conexao.prepareStatement(sqlMuda)) {
            for (Muda muda : mudas) {
                int proximoIdEntregaMuda = obterProximoIdEntregaMuda(conexao);
                statementMuda.setInt(1, proximoIdEntregaMuda);
                statementMuda.setInt(2, muda.getId());
                statementMuda.setInt(3, idEntrega);
                statementMuda.setInt(4, muda.getQuantidade());
                statementMuda.executeUpdate();

            }
        }
    }

    private int obterProximoIdEntregaMuda(Connection conexao) throws SQLException {
        String sqlNextValSeqEntregaMuda = "SELECT SEQ_ENTREGA_MUDA.NEXTVAL mysequence FROM DUAL";
        try (Statement statement = conexao.createStatement()) {
            ResultSet resultadoQueryEntregaMuda = statement.executeQuery(sqlNextValSeqEntregaMuda);
            return resultadoQueryEntregaMuda.next() ? resultadoQueryEntregaMuda.getInt("mysequence") : 0;
        }
    }


    public boolean remover(Integer id) throws SQLException {
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();
            conexao.setAutoCommit(false);

            String sqlEntregaMuda = "DELETE FROM VS_13_EQUIPE_5.ENTREGA_MUDA WHERE ID_ENTREGA = ?";
            try (PreparedStatement statementDois = conexao.prepareStatement(sqlEntregaMuda)) {
                statementDois.setInt(1, id);
                int resultadoDois = statementDois.executeUpdate();

                if (resultadoDois == 0) {
                    conexao.rollback();
                    throw new InformacaoNaoEncontrada("Não existe nenhuma entrega com o ID: " + id);
                }
            }

            String sql = "DELETE FROM VS_13_EQUIPE_5.ENTREGA WHERE ID_ENTREGA = ?";
            try (PreparedStatement statementUm = conexao.prepareStatement(sql)) {
                statementUm.setInt(1, id);
                int resultadoUm = statementUm.executeUpdate();

                if (resultadoUm == 0) {
                    conexao.rollback();
                    throw new InformacaoNaoEncontrada("Não existe nenhuma entrega com o ID: " + id);
                }

                conexao.commit();
                return true;
            }
        } catch (SQLException erro) {
            conexao.rollback();
            throw new ErroNoBancoDeDados("Erro ao remover a entrega do banco de dados.");
        } finally {
            try {
                conexao.setAutoCommit(true);
                fecharConexao(conexao);
            } catch (SQLException erro) {
                throw new ErroNoBancoDeDados("Não foi possível encerrar corretamente a conexão com o banco de dados.");
            }
        }
    }

    public Entrega editar(int idEntrega, Entrega entrega) throws ErroNoBancoDeDados {
        try (Connection conexao = conexaoBancoDeDados.getConnection()) {
            String sqlEntrega = "UPDATE VS_13_EQUIPE_5.ENTREGA SET STATUS = ? WHERE ID_ENTREGA = ?";

            try (PreparedStatement statementEntrega = conexao.prepareStatement(sqlEntrega)) {
                statementEntrega.setString(1, String.valueOf(entrega.getStatus()));
                statementEntrega.setInt(2, idEntrega);
                int resultadoEntrega = statementEntrega.executeUpdate();

                atualizarMudas(conexao, idEntrega, entrega.getMudas());

                System.out.println("A entrega foi atualizada! Resultado: " + resultadoEntrega);

                return entrega;
            }
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao atualizar a entrega no banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        }
    }


    private void atualizarMudas(Connection conexao, int idEntrega, List<Muda> mudas) throws SQLException {
        String sqlDeleteMudas = "DELETE FROM VS_13_EQUIPE_5.ENTREGA_MUDA WHERE ID_ENTREGA = ?";
        try (PreparedStatement statementDeleteMudas = conexao.prepareStatement(sqlDeleteMudas)) {
            statementDeleteMudas.setInt(1, idEntrega);
            statementDeleteMudas.executeUpdate();
        }

        String sqlMuda = "INSERT INTO VS_13_EQUIPE_5.ENTREGA_MUDA (ID_ENTREGA_MUDA, ID_MUDA, ID_ENTREGA, QUANTIDADE) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statementMuda = conexao.prepareStatement(sqlMuda)) {
            for (Muda muda : mudas) {
                int proximoIdEntregaMuda = obterProximoIdEntregaMuda(conexao);
                statementMuda.setInt(1, proximoIdEntregaMuda);
                statementMuda.setInt(2, muda.getId());
                statementMuda.setInt(3, idEntrega);
                statementMuda.setInt(4, muda.getQuantidade());
                statementMuda.executeUpdate();
            }
        }
    }


    public List<Entrega> listar() throws ErroNoBancoDeDados {
        Connection conexao = null;
        List<Entrega> listaEntrega = new ArrayList<>();

        try {
            conexao = conexaoBancoDeDados.getConnection();
            Statement statement = conexao.createStatement();
            String sqlEntrega = "SELECT * FROM VS_13_EQUIPE_5.ENTREGA";

            ResultSet entregaTabela = statement.executeQuery(sqlEntrega);

            while (entregaTabela.next()) {
                Entrega entregaAtual = new Entrega();
                entregaAtual.setId(entregaTabela.getInt("ID_ENTREGA"));
                entregaAtual.setStatus(StatusEntrega.valueOf(entregaTabela.getString("STATUS")));

                List<Muda> mudas = obterMudasDaEntrega(conexao, entregaAtual.getId());
                entregaAtual.setMudas(mudas);

                Cliente clienteAtual = obterClienteDaEntrega(conexao, entregaAtual.getId());
                entregaAtual.setCliente(clienteAtual);

                listaEntrega.add(entregaAtual);
            }

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao listar as entregas do banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
            }
        }
        return listaEntrega;
    }

    private List<Muda> obterMudasDaEntrega(Connection conexao, int idEntrega) throws SQLException {
        List<Muda> mudas = new ArrayList<>();
        String sqlMudas = "SELECT em.QUANTIDADE AS QTD_ENTREGA, m.* " +
                "FROM VS_13_EQUIPE_5.ENTREGA_MUDA em " +
                "JOIN VS_13_EQUIPE_5.MUDA m ON em.ID_MUDA = m.ID_MUDA " +
                "WHERE em.ID_ENTREGA = ?";

        try (PreparedStatement statementMudas = conexao.prepareStatement(sqlMudas)) {
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
        }
        return mudas;
    }



    private Cliente obterClienteDaEntrega(Connection conexao, int idEntrega) throws SQLException {
        Cliente clienteAtual = new Cliente();
        String sqlCliente = "SELECT c.*, u.NOME FROM VS_13_EQUIPE_5.CLIENTE c JOIN VS_13_EQUIPE_5.USUARIO u ON c.ID_USUARIO = u.ID_USUARIO WHERE c.ID_CLIENTE = (SELECT ID_CLIENTE FROM VS_13_EQUIPE_5.ENTREGA WHERE ID_ENTREGA = ?)";

        try (PreparedStatement statementCliente = conexao.prepareStatement(sqlCliente)) {
            statementCliente.setInt(1, idEntrega);
            ResultSet resultadoCliente = statementCliente.executeQuery();

            if (resultadoCliente.next()) {
                clienteAtual.setId(resultadoCliente.getInt("ID_CLIENTE"));
                clienteAtual.setCpf(resultadoCliente.getString("CPF"));
                clienteAtual.setNome(resultadoCliente.getString("NOME"));
            }
        }
        return clienteAtual;
    }


    public Entrega procurarPorId(int idEntrega) throws ErroNoBancoDeDados {
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sqlEntrega = "SELECT * FROM VS_13_EQUIPE_5.ENTREGA WHERE ID_ENTREGA = ?";
            PreparedStatement statementEntrega = conexao.prepareStatement(sqlEntrega);
            statementEntrega.setInt(1, idEntrega);

            ResultSet entregaTabela = statementEntrega.executeQuery();

            if (entregaTabela.next()) {
                Entrega entregaAtual = new Entrega();
                entregaAtual.setId(entregaTabela.getInt("ID_ENTREGA"));
                entregaAtual.setStatus(StatusEntrega.valueOf(entregaTabela.getString("STATUS")));

                int idCliente = entregaTabela.getInt("ID_CLIENTE");
                String sqlMudas = "SELECT * FROM VS_13_EQUIPE_5.ENTREGA_MUDA em "
                        + "LEFT JOIN VS_13_EQUIPE_5.MUDA m ON em.ID_MUDA = m.ID_MUDA "
                        + "WHERE em.ID_ENTREGA = ?";
                PreparedStatement statementMudas = conexao.prepareStatement(sqlMudas);
                statementMudas.setInt(1, idEntrega);

                ResultSet resultadoMudas = statementMudas.executeQuery();
                while (resultadoMudas.next()) {
                    Muda mudaAtual = new Muda();
                    mudaAtual.setId(resultadoMudas.getInt("ID_MUDA"));
                    mudaAtual.setPorte(TamanhoMuda.valueOf(resultadoMudas.getString("PORTE")));
                    mudaAtual.setTipo(TipoMuda.valueOf(resultadoMudas.getString("TIPO_MUDA")));
                    mudaAtual.setNome(resultadoMudas.getString("NOME"));
                    mudaAtual.setNomeCientifico(resultadoMudas.getString("NOME_CIENTIFICO"));
                    mudaAtual.setEcossistema(Ecossistema.valueOf(resultadoMudas.getString("ECOSSISTEMA")));
                    mudaAtual.setQuantidade(resultadoMudas.getInt("QUANTIDADE"));
                    mudaAtual.setDescricao(resultadoMudas.getString("DESCRICAO"));
                    mudaAtual.setAtivo(Ativo.valueOf(resultadoMudas.getString("ATIVO")));

                    entregaAtual.getMudas().add(mudaAtual);
                }

                String sqlCliente = "SELECT * FROM VS_13_EQUIPE_5.CLIENTE c WHERE c.ID_CLIENTE = ?";
                PreparedStatement statementCliente = conexao.prepareStatement(sqlCliente);
                statementCliente.setInt(1, idCliente);

                ResultSet clienteTabela = statementCliente.executeQuery();

                Cliente clienteAtual = new Cliente();
                while (clienteTabela.next()) {
                    int idUsuario = clienteTabela.getInt("ID_USUARIO");
                    clienteAtual.setCpf(clienteTabela.getString("CPF"));
                    clienteAtual.setId(clienteTabela.getInt("ID_CLIENTE"));

                    String sqlUsuario = "SELECT * FROM VS_13_EQUIPE_5.USUARIO WHERE ID_USUARIO = ?";
                    PreparedStatement statementUsuario = conexao.prepareStatement(sqlUsuario);
                    statementUsuario.setInt(1, idUsuario);

                    ResultSet queryUsuario = statementUsuario.executeQuery();
                    while (queryUsuario.next()) {
                        clienteAtual.setNome(queryUsuario.getString("NOME"));
                    }

                    String sqlEndereco = "SELECT * FROM VS_13_EQUIPE_5.ENDERECO e WHERE e.ID_USUARIO = ?";
                    PreparedStatement statementEndereco = conexao.prepareStatement(sqlEndereco);
                    statementEndereco.setInt(1, idUsuario);

                    ResultSet queryEndereco = statementEndereco.executeQuery();
                    while (queryEndereco.next()) {
//                        entregaAtual.getEnderecoDeEntrega().setTipo(Tipo.valueOf(queryEndereco.getString("TIPO")));
                    }

                    entregaAtual.setCliente(clienteAtual);
                }

                return entregaAtual;
            } else {
                return null;
            }

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao listar a entrega do banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
            }
        }
    }

    private void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }
}