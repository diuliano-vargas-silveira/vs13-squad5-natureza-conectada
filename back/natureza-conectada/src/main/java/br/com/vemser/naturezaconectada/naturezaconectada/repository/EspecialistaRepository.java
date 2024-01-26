package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Estados;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
@Slf4j
public class EspecialistaRepository  {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public EspecialistaRepository(ConexaoBancoDeDados conexaoBancoDeDados) {
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }


    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ESPECIALISTA.NEXTVAL mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if (resultado.next()) {
            return resultado.getInt("mysequence");
        }
        return null;
    }


    public Especialista adicionar(Especialista especialista) throws Exception {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            especialista.setIdEspecialista(proximoId.intValue());

            String sql = "INSERT INTO ESPECIALISTA\n" +
                    "(ID_ESPECIALISTA, ID_USUARIO, DOCUMENTO, ESPECIALIZACAO, ID_ESTADO)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, especialista.getIdEspecialista());
            stmt.setInt(2, especialista.getId());
            stmt.setString(3, especialista.getDocumento());
            stmt.setString(4, especialista.getEspecializacao());
            stmt.setInt(5, especialista.getRegiaoResponsavel().ordinal() + 1);

            int resultado = stmt.executeUpdate();
            System.out.println("O especialista foi adicionado! Resultado: " + resultado);

            return especialista;

        } catch (SQLException erro) {
           log.error(erro.getMessage());
            throw new ErroNoBancoDeDados("Erro ao adicionar Especialista no banco de dados");
        } finally {
            try {
                fecharConexao(conexao);
            } catch (Exception erro) {
                log.error(erro.getMessage());
                throw new ErroNoBancoDeDados("Erro ao fechar a conexão com o banco de dados");

            }
        }
    }


    public void remover(Integer id) throws Exception {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM ESPECIALISTA WHERE ID_ESPECIALISTA = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id.intValue());

            int resultado = stmt.executeUpdate();
            System.out.println("O especialista foi removido! Resultado: " + resultado);


        } catch (SQLException erro) {
            log.error(erro.getMessage());
            throw new ErroNoBancoDeDados("Erro ao remover especialista do banco de dados");
        } finally {
            try {
                fecharConexao(conexao);
            } catch (Exception erro) {
                log.error(erro.getMessage());
                erro.printStackTrace();
                throw new ErroNoBancoDeDados("Erro ao fechar a conexão");
            }
        }
    }


    public Especialista editar(Integer id, Especialista especialista) throws Exception {
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE ESPECIALISTA SET \n" +
                    "\tDOCUMENTO = ?,\n" +
                    "\tESPECIALIZACAO = ?,\n" +
                    "\tID_ESTADO = ?\n" +
                    "WHERE ID_ESPECIALISTA = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, especialista.getDocumento());
            stmt.setString(2, especialista.getEspecializacao());
            stmt.setInt(3, especialista.getRegiaoResponsavel().ordinal() + 1);
            stmt.setInt(4, id);

            int resultado = stmt.executeUpdate();

            log.info("O especialista foi atualizado! Resultado: " + resultado);
            return especialista;

        } catch (SQLException erro) {
            log.error("ERRO: Algo deu errado ao editar o especialista no banco de dados." + erro.getMessage());
            throw new ErroNoBancoDeDados("erro ao adicionar usuario no Banco de dados");
        } finally {
            try {
                fecharConexao(conexao);
            } catch (Exception erro) {
               log.error("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados."+erro.getMessage());
                erro.printStackTrace();
            }
        }
    }


    public List<Especialista> listar() throws Exception {
        Connection conexao = null;
        List<Especialista> listaEspecialistas = new ArrayList<>();

        try {
            conexao = conexaoBancoDeDados.getConnection();
            Statement statment = conexao.createStatement();

            String sqlEspecialista = """
                    SELECT * FROM ESPECIALISTA e 
                        INNER JOIN USUARIO u ON e.ID_USUARIO = u.ID_USUARIO""";

            ResultSet especialistaTabela = statment.executeQuery(sqlEspecialista);

            while (especialistaTabela.next()) {
                Especialista especialistaAtual = new Especialista();
                especialistaAtual.setId(especialistaTabela.getInt("ID_USUARIO"));
                especialistaAtual.setIdEspecialista(especialistaTabela.getInt("ID_ESPECIALISTA"));
                especialistaAtual.setNome(especialistaTabela.getString("NOME"));
                especialistaAtual.setEmail(especialistaTabela.getString("EMAIL"));
                especialistaAtual.setSenha(especialistaTabela.getString("SENHA"));
                especialistaAtual.setDocumento(especialistaTabela.getString("DOCUMENTO"));
                especialistaAtual.setEspecializacao(especialistaTabela.getString("ESPECIALIZACAO"));
                especialistaAtual.setRegiaoResponsavel(Estados.values()[especialistaTabela.getInt("ID_ESTADO") - 1]);

                listaEspecialistas.add(especialistaAtual);
            }

        } catch (SQLException erro) {
           log.error("ERRO: Algo deu errado ao listar os especialistas do banco de dados." + erro.getMessage());
            throw new ErroNoBancoDeDados(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (Exception erro) {
               log.error("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados." + erro.getMessage());
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

    public Especialista procurarPorId(int id) throws Exception {
        Especialista especialista = null;
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "SELECT e.ID_ESPECIALISTA, e.ID_USUARIO, u.NOME, u.EMAIL, e.DOCUMENTO, e.ESPECIALIZACAO, e.ID_ESTADO \n" +
                    "\tFROM ESPECIALISTA e \n" +
                    "INNER JOIN USUARIO u ON e.ID_USUARIO = u.ID_USUARIO\n" +
                    "WHERE e.ID_ESPECIALISTA  = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                especialista = new Especialista();

                especialista.setIdEspecialista(id);
                especialista.setEspecializacao((resultSet.getString("ESPECIALIZACAO")));
                especialista.setTipoUsuario(TipoUsuario.ESPECIALISTA);
                especialista.setId(resultSet.getInt("ID_USUARIO"));
                especialista.setNome(resultSet.getString("NOME"));
                especialista.setEmail(resultSet.getString("EMAIL"));
                especialista.setDocumento(resultSet.getString("DOCUMENTO"));
                especialista.setEspecializacao(resultSet.getString("ESPECIALIZACAO"));
                especialista.setRegiaoResponsavel(Estados.values()[resultSet.getInt("ID_ESTADO") - 1]);
            }

        } catch (SQLException e) {
           log.error("ERRO: Algo deu errado ao procurar o especialista no banco de dados." + e.getMessage());
            throw new ErroNoBancoDeDados("Erro ao buscar no banco de dados");
        } finally {
            try {
                fecharConexao(connection);
            } catch (Exception erro) {
                log.error("ERRO: Algo deu errado ao fechar a conexa com o banco de dados." + erro.getMessage());
                erro.printStackTrace();
                throw new ErroNoBancoDeDados(" Algo deu errado ao fechar a conexa com o banco de dados.");
            }
        }

        return especialista;
    }
}
