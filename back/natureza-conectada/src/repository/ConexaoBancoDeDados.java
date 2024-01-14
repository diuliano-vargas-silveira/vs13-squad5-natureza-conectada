package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {
    private static final String SERVER = "vemser-dbc.dbccompany.com.br";
    private static final String PORT = "25000";
    private static final String DATABASE = "xe";


    private static final String USER = "VS_13_EQUIPE_5";
    private static final String PASS = "oracle";
    private static final String SCHEMA = "VS_13_EQUIPE_5";

//    private static final String SESSAO_LOCAL = "jdbc:oracle:thin:@localhost:1521:XE";
//    private static final String USER_LOCAL = "system";
//    private static final String SENHA_LOCAL = "oracle";
//    private static final String SCHEMA_LOCAL = "VEM_SER";

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;


        Connection con = DriverManager.getConnection(url, USER, PASS);


        con.createStatement().execute("alter session set current_schema=" + SCHEMA);

        return con;
    }
}
