package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.config.ConexaoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.*;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceCliente;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceMudas;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EntregaRepository {
    private final ServiceCliente serviceCliente;
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    private final ServiceMudas serviceMudas;

    private final ObjectMapper objectMapper;





    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ENTREGA.NEXTVAL mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if (resultado.next()) {
            return resultado.getInt("mysequence");
        }
        return null;
    }


    public Entrega adicionar(Entrega entrega, Integer idEndereco) throws Exception {
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

                entrega.setMudas(this.serviceMudas.obterMudasDaEntrega( entrega.getId()).stream().map(mudaDTO -> this.objectMapper.convertValue(mudaDTO, Muda.class)).toList());
                entrega.setCliente(this.objectMapper.convertValue(this.serviceCliente.procurarPorIdCliente(entrega.getCliente().getId()),Cliente.class));

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

    public Entrega editarStatus(int idEntrega, StatusEntrega status) throws Exception {
        try (Connection conexao = conexaoBancoDeDados.getConnection()) {
            String sqlEntrega = "UPDATE VS_13_EQUIPE_5.ENTREGA SET STATUS = ? WHERE ID_ENTREGA = ?";

            try (PreparedStatement statementEntrega = conexao.prepareStatement(sqlEntrega)) {
                statementEntrega.setString(1, String.valueOf(status));
                statementEntrega.setInt(2, idEntrega);
                int resultadoEntrega = statementEntrega.executeUpdate();
                Entrega entrega = this.procurarPorId(idEntrega);

                System.out.println("A entrega foi atualizada! Resultado: " + resultadoEntrega);

                if(status == StatusEntrega.ENTREGUE){

                    entrega.getMudas().forEach(muda -> {
                        try {
                            this.serviceCliente.inserirMudasEntregues(entrega.getCliente().getIdCliente(),muda.getId());
                        } catch (Exception e) {
                            log.error("erro ao anexar muda ao clienete");
                            throw new RuntimeException("Erro ao anexar muda");
                        }
                    });

                }

                return entrega;
            }
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao atualizar a entrega no banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        }catch (Exception e){
            throw new ErroNoBancoDeDados("erro ao buscar");
        }
    }


    public void atualizarMudas( int idEntrega, List<Muda> mudas) throws SQLException {
        String sqlDeleteMudas = "DELETE FROM VS_13_EQUIPE_5.ENTREGA_MUDA WHERE ID_ENTREGA = ?";
        try (Connection conexao = conexaoBancoDeDados.getConnection()) {
            PreparedStatement statementDeleteMudas = conexao.prepareStatement(sqlDeleteMudas);
            statementDeleteMudas.setInt(1, idEntrega);
            statementDeleteMudas.executeUpdate();
        }

        String sqlMuda = "INSERT INTO VS_13_EQUIPE_5.ENTREGA_MUDA (ID_ENTREGA_MUDA, ID_MUDA, ID_ENTREGA, QUANTIDADE) VALUES (?, ?, ?, ?)";
        try  (Connection conexao = conexaoBancoDeDados.getConnection()) {
            PreparedStatement statementMuda = conexao.prepareStatement(sqlMuda);
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


    public List<Entrega> listar() throws Exception {
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

                List<Muda> mudas = this.serviceMudas.obterMudasDaEntrega( entregaAtual.getId()).stream().map(muda ->this.objectMapper.convertValue(muda,Muda.class) ).toList();
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


    public Entrega procurarPorId(int idEntrega) throws Exception {
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
                log.debug("service");
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


                Cliente clienteAtual = this.objectMapper.convertValue(this.serviceCliente.procurarPorIdCliente(idCliente),Cliente.class);
                entregaAtual.setCliente(clienteAtual);

                return entregaAtual;
            } else {
                throw new InformacaoNaoEncontrada("Não foi encontrado nenhuma entrega com este id");
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