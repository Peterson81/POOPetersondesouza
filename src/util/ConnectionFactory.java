package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Fábrica de conexões com o PostgreSQL via JDBC.
 * Ajuste URL, USUARIO e SENHA conforme o seu ambiente.
 */
public class ConnectionFactory {

    private static final String DRIVER  = "org.postgresql.Driver";
    private static final String URL     = "jdbc:postgresql://localhost:5432/oficina";
    private static final String USUARIO = "postgres";
    private static final String SENHA   = "postgres";

    private ConnectionFactory() { }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do PostgreSQL não encontrado: " + DRIVER, e);
        }
    }
}
