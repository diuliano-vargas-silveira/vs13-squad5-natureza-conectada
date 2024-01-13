package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import enums.Tipo;
import models.Contato;

public class ContatoRepository {

    private Connection connection;

    public ContatoRepository(Connection connection) {
        this.connection = connection;
    }
    public void inserirContato(Contato contato) {
        String sql = "INSERT INTO contatos (descricao, numero, tipo) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, contato.getDescricao());
            preparedStatement.setString(2, contato.getNumero());
            preparedStatement.setInt(3, contato.getTipo().getCodigo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contato> obterTodosContatos() {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contatos";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contato contato = new Contato();
                contato.setId(resultSet.getInt("id"));
                contato.setDescricao(resultSet.getString("descricao"));
                contato.setNumero(resultSet.getString("numero"));
                int tipoCodigo = resultSet.getInt("tipo");
                contato.setTipo(Tipo.getByCodigo(tipoCodigo));
                contatos.add(contato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contatos;
    }

    // Outros métodos conforme necessário, como atualizar e excluir contatos
}
