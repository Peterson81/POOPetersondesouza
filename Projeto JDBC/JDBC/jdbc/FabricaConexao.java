package br.com.ecommerce.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * NÍVEL 1 - Ponto centralizado de criação de conexões com o PostgreSQL.
 *
 * O pacote java.sql é um framework de INTERFACES: Connection é uma interface
 * que o driver postgresql implementa concretamente, permitindo polimorfismo.
 *
 * Ajuste USUARIO/SENHA conforme a configuração local do seu PostgreSQL.
 */
public class FabricaConexao {

    // Padrão conforme o roteiro; pode ser sobrescrito via argumentos JVM
    // (-Djdbc.driver=... -Djdbc.url=... -Djdbc.usuario=... -Djdbc.senha=...)
    private static final String DRIVER =
            System.getProperty("jdbc.driver", "org.postgresql.Driver");
    private static final String URL =
            System.getProperty("jdbc.url", "jdbc:postgresql://localhost:5432/bdecommerce");
    private static final String USUARIO =
            System.getProperty("jdbc.usuario", "postgres");
    private static final String SENHA =
            System.getProperty("jdbc.senha", "postgres");

    public static Connection obterConexao() throws ClassNotFoundException, SQLException {
        // ClassLoader da JVM carrega o bytecode do driver postgresql
        Class.forName(DRIVER);

        // DriverManager seleciona o driver adequado a partir da URL JDBC
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}