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
            String sql = "DELETE FROM ESPECIALISTA WHERE ID_ESPECIALISTA = ?";

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

            String sqlEspecialista = "SELECT * FROM ESPECIALISTA";

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

    public Especialista procurarPorId(int id) throws SQLException {
        Especialista especialista = null;
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

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
                especialista.setId(resultSet.getInt("ID_USUARIO"));
                especialista.setNome(resultSet.getString("NOME"));
                especialista.setEmail(resultSet.getString("EMAIL"));
                especialista.setDocumento(resultSet.getString("DOCUMENTO"));
                especialista.setEspecializacao(resultSet.getString("ESPECIALIZACAO"));
                especialista.setRegiaoResponsavel(Estados.values()[resultSet.getInt("ID_ESTADO") + 1]);
            }

        } catch (SQLException e) {
            System.out.println("ERRO: Algo deu errado ao procurar o especialista no banco de dados.");
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                fecharConexao(connection);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }

        return especialista;
    }
}
