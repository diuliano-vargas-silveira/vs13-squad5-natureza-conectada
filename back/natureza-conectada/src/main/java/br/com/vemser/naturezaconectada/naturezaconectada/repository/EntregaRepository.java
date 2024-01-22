package br.com.vemser.naturezaconectada.naturezaconectada.repository;


import br.com.vemser.naturezaconectada.naturezaconectada.enums.*;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.BancoDeDadosException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
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


    public Entrega adicionar(Entrega entrega, Integer idEndereco) throws BancoDeDadosException {
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            entrega.setId(proximoId.intValue());

            String sql = "INSERT INTO VS_13_EQUIPE_5.ENTREGA\n" +
                    "(ID_ENTREGA, ID_CLIENTE, ID_ENDERECO, STATUS)\n" +
                    "VALUES(?, ?, ?, ?)\n";


            PreparedStatement statementUm = conexao.prepareStatement(sql);
            statementUm.setInt(1, entrega.getId());
            statementUm.setInt(2, entrega.getCliente().getId());
            statementUm.setInt(3, idEndereco);
            statementUm.setString(4, String.valueOf(entrega.getStatus()));

            int resultadoUm = statementUm.executeUpdate();


            int quantidade = entrega.getMudas().size();
            int contador = 0;
            while (contador < quantidade) {
                String sqlQueryEntregaMuda = "INSERT INTO VS_13_EQUIPE_5.ENTREGA_MUDA (ID_ENTREGA_MUDA, ID_MUDA, ID_ENTREGA, QUANTIDADE)\n "
                        + "VALUES (?, ?, ?, ?)";
                PreparedStatement statementDois = conexao.prepareStatement(sqlQueryEntregaMuda);
                Statement statementTres = conexao.createStatement();
                String sqlNextValSeqEntregaMuda = "SELECT SEQ_ENTREGA_MUDA.NEXTVAL mysequence FROM DUAL";

                ResultSet resultadoQueryEntregaMuda = statementTres.executeQuery(sqlNextValSeqEntregaMuda);
                int proximoIdEntregaMuda = 1;
                while (resultadoQueryEntregaMuda.next()) {
                    proximoIdEntregaMuda = resultadoQueryEntregaMuda.getInt("mysequence");
                }
                statementDois.setInt(1, proximoIdEntregaMuda);
                statementDois.setInt(2, entrega.getMudas().get(contador).getId());
                statementDois.setInt(3, proximoId);
                statementDois.setInt(4, entrega.getMudas().get(contador).getQuantidade());
                int resultadoDois = statementDois.executeUpdate();
                statementDois.close();
                statementTres.close();
                contador++;
            }

            System.out.println("A entrega foi adicionada! Resultado: ".concat(String.valueOf(resultadoUm)));

            return entrega;

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para adicionar á entrega ao banco de dados.");
            throw new BancoDeDadosException(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM VS_13_EQUIPE_5.ENTREGA WHERE ID_ENTREGA = ?";

            PreparedStatement statementUm = conexao.prepareStatement(sql);
            statementUm.setInt(1, id.intValue());

            int resultado = statementUm.executeUpdate();

            String sqlEntregaMuda = "DELETE FROM VS_13_EQUIPE_5.ENTREGA_MUDA WHERE ID_ENTREGA = ?";

            PreparedStatement statementDois = conexao.prepareStatement(sqlEntregaMuda);
            statementDois.setInt(1, id.intValue());

            int resultadoDois = statementDois.executeUpdate();
            System.out.println("A entrega foi removida! Resultado: ".concat(String.valueOf(resultado)));

            return resultado > 0;
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado para remover á entrega do banco de dados.");
            throw new BancoDeDadosException(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }


    public boolean editar(Integer id, Entrega entrega) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE VS_13_EQUIPE_5.ENTREGA SET STATUS = ? WHERE ID_ENTREGA = ?";

            if (String.valueOf(entrega.getStatus()).equals("ENTREGUE")) {
                List<Muda> novaLista = entrega.getMudas();
                int idCliente = entrega.getCliente().getIdCliente();

                novaLista.stream().forEach(muda -> serviceCliente.inserirMudasEntregues(idCliente, muda.getId()));

            }

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, String.valueOf(entrega.getStatus()));
            stmt.setInt(2, id);

            int resultado = stmt.executeUpdate();

            System.out.println("A entrega foi atualizada! Resultado: ".concat(String.valueOf(resultado)));
        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado em editar á entrega no banco de dados.");
            throw new BancoDeDadosException(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return true;
    }


    public List<Entrega> listar() throws BancoDeDadosException {
        Connection conexao = null;
        List<Entrega> listaEntrega = new ArrayList<>();

        try {
            conexao = conexaoBancoDeDados.getConnection();
            Statement statement = conexao.createStatement();
            Statement statementDois = conexao.createStatement();
            Statement statementTres = conexao.createStatement();
            Statement statementQuatro = conexao.createStatement();
            Statement statementCinco = conexao.createStatement();
            String sqlEntrega = "SELECT * FROM VS_13_EQUIPE_5.ENTREGA";

            ResultSet entregaTabela = statement.executeQuery(sqlEntrega);


            while (entregaTabela.next()) {
                Entrega entregaAtual = new Entrega();
                entregaAtual.setId(entregaTabela.getInt("ID_ENTREGA"));
                entregaAtual.setStatus(StatusEntrega.valueOf(entregaTabela.getString("STATUS")));


                // buscar e filtrar mudas no banco de dados:
                // tabela com todos os id_muda que tem relacao com a entrega atual:
                int idEntrega = entregaTabela.getInt("ID_ENTREGA");
                int idCliente = entregaTabela.getInt("ID_CLIENTE");
                String sqlFullJoinResultado = "SELECT * FROM (SELECT * FROM VS_13_EQUIPE_5.ENTREGA_MUDA em WHERE (em.ID_ENTREGA = " + idEntrega
                        + ")) RESULTADO_ENTREGA_MUDA FULL OUTER JOIN VS_13_EQUIPE_5.MUDA m ON (RESULTADO_ENTREGA_MUDA.ID_MUDA = m.ID_MUDA)";

                ResultSet filtroMuda = statementDois.executeQuery(sqlFullJoinResultado);
                while (filtroMuda.next()) {

                    Muda mudaAtual = new Muda();
                    mudaAtual.setId(filtroMuda.getInt("ID_MUDA"));
                    mudaAtual.setPorte(TamanhoMuda.valueOf(filtroMuda.getString("PORTE")));
                    mudaAtual.setTipo(TipoMuda.valueOf(filtroMuda.getString("TIPO_MUDA")));
                    mudaAtual.setNome(filtroMuda.getString("NOME"));
                    mudaAtual.setNomeCientifico(filtroMuda.getString("NOME_CIENTIFICO"));
                    mudaAtual.setAmbienteIdeal(filtroMuda.getString("AMBIENTE_IDEAL"));
                    mudaAtual.setQuantidade(filtroMuda.getInt("QUANTIDADE"));
                    mudaAtual.setDescricao(filtroMuda.getString("DESCRICAO"));

                    entregaAtual.getMudas().add(mudaAtual);
                }
                // buscar cliente no banco de dados:

                String sqlCliente = "SELECT * FROM VS_13_EQUIPE_5.CLIENTE c WHERE (c.ID_CLIENTE =" + idCliente + ")";
                ResultSet clienteTabela = statementTres.executeQuery(sqlCliente);


                Cliente clienteAtual = new Cliente();
                while (clienteTabela.next()) {
                    int idUsuario = clienteTabela.getInt("ID_USUARIO");
                    clienteAtual.setCpf(clienteTabela.getString("CPF"));
                    clienteAtual.setId(clienteTabela.getInt("ID_CLIENTE"));
                    String sqlUsuario = "SELECT * FROM VS_13_EQUIPE_5.USUARIO WHERE ID_USUARIO = " + idUsuario;

                    ResultSet queryUsuario = statementQuatro.executeQuery(sqlUsuario);
                    while (queryUsuario.next()) {
                        clienteAtual.setNome(queryUsuario.getString("NOME"));
                    }
                    String sqlEndereco = "SELECT * FROM VS_13_EQUIPE_5.ENDERECO e WHERE e.ID_USUARIO = " + idUsuario;
                    ResultSet queryEndereco = statementCinco.executeQuery(sqlEndereco);
                    while (queryEndereco.next()) {
//                        entregaAtual.getCliente(new Endereco());
//                        entregaAtual.getEnderecoDeEntrega().setCep(queryEndereco.getString("CEP"));
//                        entregaAtual.getEnderecoDeEntrega().setLogradouro(queryEndereco.getString("LOGRADOURO"));
//                        entregaAtual.getEnderecoDeEntrega().setId(queryEndereco.getInt("ID_ENDERECO"));
//                        entregaAtual.getEnderecoDeEntrega().setCidade(queryEndereco.getString("CIDADE"));
//                        entregaAtual.getEnderecoDeEntrega().setNumero(queryEndereco.getString("NUMERO"));
//                        entregaAtual.getEnderecoDeEntrega().setComplemento(queryEndereco.getString("COMPLEMENTO"));
//                        entregaAtual.getEnderecoDeEntrega().setEstado(Estados.ofTipo((Integer) queryEndereco.getInt("ID_ESTADO")));
//                        entregaAtual.getEnderecoDeEntrega().setTipo(Tipo.valueOf(queryEndereco.getString("TIPO")));
                    }
                    entregaAtual.setCliente(clienteAtual);
                }
                listaEntrega.add(entregaAtual);
            }

        } catch (SQLException erro) {
            System.out.println("ERRO: Algo deu errado ao listar as entregas do banco de dados.");
            throw new BancoDeDadosException(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
            }
        }
        return listaEntrega;
    }

    // Método(s) da classe:
    private void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }
}
