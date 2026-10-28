package br.com.empresa.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Criação de conexões com o PostgreSQL.
 * Pode ser sobrescrito via argumentos JVM (-Djdbc.senha=... etc.).
 */
public class FabricaConexao {

    private static final String DRIVER =
            System.getProperty("jdbc.driver", "org.postgresql.Driver");
    private static final String URL =
            System.getProperty("jdbc.url", "jdbc:postgresql://localhost:5432/bddepartamentos");
    private static final String USUARIO =
            System.getProperty("jdbc.usuario", "postgres");
    private static final String SENHA =
            System.getProperty("jdbc.senha", "postgres");

    public static Connection obterConexao() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}