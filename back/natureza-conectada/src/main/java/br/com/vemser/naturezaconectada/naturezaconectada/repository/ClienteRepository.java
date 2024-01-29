package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.*;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public ClienteRepository(ConexaoBancoDeDados conexaoBancoDeDados) {
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }

    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_cliente.nextval mysequence from DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    public Cliente adicionar(Cliente cliente) throws Exception {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(conexao);
            cliente.setIdCliente(proximoId);

            String sql = "INSERT INTO CLIENTE\n" +
                    "(ID_CLIENTE, ID_USUARIO, CPF)\n" +
                    "VALUES(?, ?, ?)\n";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, cliente.getIdCliente());
                stmt.setInt(2, cliente.getId());
                stmt.setString(3, cliente.getCpf());

                int resultado = stmt.executeUpdate();
                System.out.println("O usuário foi adicionado! Resultado: " + resultado);

            } catch (SQLException erro) {
                System.out.println("ERRO: Algo deu errado para adicionar o usuário ao banco de dados.");
                throw new ErroNoBancoDeDados(erro.getMessage());
            }

            return cliente;

        } catch (SQLException erro) {
            System.out.println("ERRO: Não foi possível obter a conexão com o banco de dados.");
            throw new ErroNoBancoDeDados(erro.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    public Cliente editar(Integer id, Cliente clienteEditado) throws Exception {
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();

            String sql_cliente = "UPDATE CLIENTE SET\n" +
                    " CPF = ? \n" +
                    " WHERE ID_CLIENTE = ? ";

            PreparedStatement stmt = conexao.prepareStatement(sql_cliente);
            stmt.setString(1, clienteEditado.getCpf());
            stmt.setInt(2, id);

            int res = stmt.executeUpdate();
            System.out.println("editarPessoa.res=" + res);

            return clienteEditado;

        } catch (SQLException e) {
            throw new ErroNoBancoDeDados(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    public void remover(Integer id) throws Exception {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM CLIENTE WHERE id_cliente = ?";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int resultado = stmt.executeUpdate();
                System.out.println("O cliente foi removido! Resultado: " + resultado);


            } catch (SQLException e) {
                throw new ErroNoBancoDeDados(e.getMessage());
            }

        } catch (SQLException e) {
            throw new ErroNoBancoDeDados(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    public List<Cliente> listarTodos() throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();

            Statement stmt = conexao.createStatement();
            ResultSet res = stmt.executeQuery(
                    "SELECT c.ID_CLIENTE, c.CPF, u.NOME, u.EMAIL, u.ATIVO, u.TIPO_USUARIO " +
                            "FROM VS_13_EQUIPE_5.CLIENTE c " +
                            "INNER JOIN VS_13_EQUIPE_5.USUARIO u ON (c.ID_USUARIO = u.ID_USUARIO )");

            while (res.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(res.getInt("ID_CLIENTE"));
                cliente.setNome(res.getString("NOME"));
                cliente.setEmail(res.getString("EMAIL"));
                cliente.setCpf(res.getString("CPF"));
                cliente.setAtivo(Ativo.valueOf(res.getString("ATIVO")));
                cliente.setTipoUsuario(TipoUsuario.CLIENTE);

                cliente.setEnderecos(buscarEnderecosPorIdCliente(conexao, cliente.getId()));

                cliente.setContatos(buscarContatosPorIdCliente(conexao, cliente.getId()));

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            throw new ErroNoBancoDeDados(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return clientes;
    }

    private List<Contato> buscarContatosPorIdCliente(Connection conexao, Integer idCliente) throws SQLException {
        String sqlContato = "SELECT ID_CONTATO, DESCRICAO, NUMERO, TIPO_CONTATO FROM CONTATO WHERE ID_USUARIO = ?";

        PreparedStatement statementContato = conexao.prepareStatement(sqlContato);
        statementContato.setInt(1, idCliente);

        ResultSet resultadoContato = statementContato.executeQuery();
        List<Contato> contatos = new ArrayList<>();

        while (resultadoContato.next()) {
            Contato contato = new Contato();
            contato.setId(resultadoContato.getInt("ID_CONTATO"));
            contato.setDescricao(resultadoContato.getString("DESCRICAO"));
            contato.setNumero(resultadoContato.getString("NUMERO"));
            contato.setTipo(Tipo.valueOf(resultadoContato.getString("TIPO_CONTATO")));

            contatos.add(contato);
        }

        return contatos;
    }

    private List<Endereco> buscarEnderecosPorIdCliente(Connection conexao, Integer idCliente) throws SQLException {
        String sqlEndereco = "SELECT e.ID_ENDERECO, e.CEP, e.LOGRADOURO, e.NUMERO, e.COMPLEMENTO, e.CIDADE, e.ID_ESTADO, e.TIPO, e.ECOSSISTEMA, e.ATIVO " +
                "FROM VS_13_EQUIPE_5.ENDERECO e " +
                "WHERE e.ID_USUARIO = ?";

        PreparedStatement statementEndereco = conexao.prepareStatement(sqlEndereco);
        statementEndereco.setInt(1, idCliente);

        ResultSet resultadoEndereco = statementEndereco.executeQuery();
        List<Endereco> enderecos = new ArrayList<>();

        while (resultadoEndereco.next()) {
            Endereco endereco = new Endereco();
            endereco.setIdEndereco(resultadoEndereco.getInt("ID_ENDERECO"));
            endereco.setCep(resultadoEndereco.getString("CEP"));
            endereco.setLogradouro(resultadoEndereco.getString("LOGRADOURO"));
            endereco.setNumero(resultadoEndereco.getString("NUMERO"));
            endereco.setComplemento(resultadoEndereco.getString("COMPLEMENTO"));
            endereco.setCidade(resultadoEndereco.getString("CIDADE"));
            endereco.setEstado(Estados.values()[resultadoEndereco.getInt("ID_ESTADO") - 1]);
            endereco.setTipo(Tipo.valueOf(resultadoEndereco.getString("TIPO")));
            endereco.setEcossistema(Ecossistema.valueOf(resultadoEndereco.getString("ECOSSISTEMA")));
            endereco.setAtivo(Ativo.valueOf(resultadoEndereco.getString("ATIVO")));

            enderecos.add(endereco);
        }

        return enderecos;
    }

    public Cliente procurarPorIdCliente(Integer idCliente) throws Exception {
        Cliente cliente = new Cliente();
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sqlCliente = "SELECT c.ID_CLIENTE, c.CPF, u.NOME, u.EMAIL, u.ATIVO, u.TIPO_USUARIO " +
                    "FROM VS_13_EQUIPE_5.CLIENTE c " +
                    "INNER JOIN VS_13_EQUIPE_5.USUARIO u ON (c.ID_USUARIO = u.ID_USUARIO) " +
                    "WHERE c.ID_CLIENTE = ?";

            PreparedStatement statementCliente = conexao.prepareStatement(sqlCliente);
            statementCliente.setInt(1, idCliente);

            ResultSet resultadoCliente = statementCliente.executeQuery();

            while (resultadoCliente.next()) {
                cliente.setId(resultadoCliente.getInt("ID_CLIENTE"));
                cliente.setCpf(resultadoCliente.getString("CPF"));
                cliente.setNome(resultadoCliente.getString("NOME"));
                cliente.setEmail(resultadoCliente.getString("EMAIL"));
                cliente.setAtivo(Ativo.valueOf(resultadoCliente.getString("ATIVO")));
                cliente.setTipoUsuario(TipoUsuario.CLIENTE);
            }

            if (cliente.getId() != 0) {
                String sqlEndereco = "SELECT e.ID_ENDERECO, e.ID_USUARIO, e.CEP, e.LOGRADOURO, e.NUMERO, e.COMPLEMENTO, e.CIDADE, e.ID_ESTADO, e.TIPO, e.ECOSSISTEMA, e.ATIVO " +
                        "FROM VS_13_EQUIPE_5.ENDERECO e " +
                        "WHERE e.ID_USUARIO = ?";

                PreparedStatement statementEndereco = conexao.prepareStatement(sqlEndereco);
                statementEndereco.setInt(1, cliente.getId());

                ResultSet resultadoEndereco = statementEndereco.executeQuery();
                List<Endereco> enderecos = new ArrayList<>();

                while (resultadoEndereco.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setIdEndereco(resultadoEndereco.getInt("ID_ENDERECO"));
                    endereco.setCep(resultadoEndereco.getString("CEP"));
                    endereco.setLogradouro(resultadoEndereco.getString("LOGRADOURO"));
                    endereco.setNumero(resultadoEndereco.getString("NUMERO"));
                    endereco.setComplemento(resultadoEndereco.getString("COMPLEMENTO"));
                    endereco.setCidade(resultadoEndereco.getString("CIDADE"));
                    endereco.setEstado(Estados.values()[resultadoEndereco.getInt("ID_ESTADO") - 1]);
                    endereco.setTipo(Tipo.valueOf(resultadoEndereco.getString("TIPO")));
                    endereco.setEcossistema(Ecossistema.valueOf(resultadoEndereco.getString("ECOSSISTEMA")));
                    endereco.setAtivo(Ativo.valueOf(resultadoEndereco.getString("ATIVO")));

                    cliente.getEnderecos().add(endereco);
                }

                cliente.setContatos(buscarContatosPorIdCliente(conexao, cliente.getId()));
            }

            return cliente;

        } catch (SQLException e) {
            throw new ErroNoBancoDeDados(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possível encerrar corretamente a conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
    }

    private Cliente getCliente(ResultSet usuario) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setIdCliente(usuario.getInt("ID_CLIENTE"));
        cliente.setNome(usuario.getString("NOME"));
        cliente.setEmail(usuario.getString("EMAIL"));
        cliente.setCpf(usuario.getString("CPF"));

        return cliente;
    }

    private void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }

    public Cliente listarPorEmail(String email) throws Exception {
        Cliente cliente = null;
        Connection conexao = null;

        try {
            conexao = conexaoBancoDeDados.getConnection();

            String sqlUsuario = "SELECT c.ID_CLIENTE, c.CPF, u.NOME, u.EMAIL\n" +
                    "FROM\n" +
                    "\tUSUARIO u \n" +
                    "INNER JOIN \n" +
                    "\tCLIENTE c ON (u.ID_USUARIO = c.ID_CLIENTE)\n" +
                    "WHERE \n" +
                    "\tu.EMAIL = ? ";

            PreparedStatement preparedStatement = conexao.prepareStatement(sqlUsuario);

            preparedStatement.setString(1, email);

            ResultSet clienteRes = preparedStatement.executeQuery();

            if (clienteRes.next()) {
                cliente = getCliente(clienteRes);

            }

        } catch (SQLException e) {
            throw new ErroNoBancoDeDados(e.getMessage());
        } finally {
            try {
                fecharConexao(conexao);
            } catch (SQLException erro) {
                System.out.println("ERRO: Não foi possivel encerrar corretamente á conexão com o banco de dados.");
                erro.printStackTrace();
            }
        }
        return cliente;
    }

    public void InserirMudaEmCliente(Integer idCliente, Integer idMuda) throws Exception {
        Connection connection = null;
        try {
            connection = conexaoBancoDeDados.getConnection();
            int proximoId = this.getProximoIDMudaCliente(connection);
            String sql = "Insert Into CLIENTE_MUDA (ID_CLIENTE_MUDA,ID_MUDA,ID_CLIENTE)" +
                    "values(?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, proximoId);
            stm.setInt(2, idMuda);
            stm.setInt(3, idCliente);
            int resultado = stm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao Inserir Muda ao Cliente, ERRO: " + ex.getMessage());
            throw new ErroNoBancoDeDados(ex.getMessage());

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException erro) {
                System.out.println("Erro ao fechar conexao do banco de Dados, Erro: " + erro.getMessage());
                erro.printStackTrace();
            }
        }

    }

    public Integer getProximoIDMudaCliente(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_CLIENTE_MUDA.NEXTVAL mysequence2 FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);
        if (resultado.next()) {
            return resultado.getInt("mysequence2");
        }
        return null;
    }
}