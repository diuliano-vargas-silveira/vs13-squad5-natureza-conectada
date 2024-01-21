package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConexaoBancoDeDados {

    private static final String BASE_URL = "jdbc:oracle:thin:@";

    private static final String ALTER_SECTION = "alter session set current_schema=";

    @Value("${database-url}")
    private String SERVER;

    @Value("${database-port}")
    private String PORT;

    @Value("${database-name}")
    private String DATABASE;

    @Value("${database-user}")
    private String USER;

    @Value("${database-pass}")
    private String PASS;

    @Value("${database-schema}")
    private String SCHEMA;

    public Connection getConnection() throws SQLException {
        String url = BASE_URL + SERVER + ":" + PORT + ":" + DATABASE;

        Connection con = DriverManager.getConnection(url, USER, PASS);

        con.createStatement().execute(ALTER_SECTION + SCHEMA);

        return con;
    }
}
