package br.com.ecommerce.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * NÍVEL 1 - Teste da conexão com o PostgreSQL.
 *
 * Trata separadamente ClassNotFoundException (driver ausente no classpath) e
 * SQLException (falha de rede / credenciais / banco inexistente). Ao final,
 * fecha a conexão explicitamente dentro do finally.
 */
public class TesteConexaoPostgreSQL {

    public static void main(String[] args) {
        Connection conexao = null;
        try {
            conexao = FabricaConexao.obterConexao();

            System.out.println(">>> Conexão estabelecida com o banco bdecommerce!");
            System.out.println(">>> Classe concreta devolvida pelo driver (polimorfismo): "
                    + conexao.getClass().getName());
        } catch (ClassNotFoundException e) {
            System.out.println("ERRO (ClassNotFoundException): o driver JDBC do PostgreSQL "
                    + "não está no classpath.");
            System.out.println("Detalhe: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERRO (SQLException): falha de rede, banco inexistente ou "
                    + "credenciais inválidas.");
            System.out.println("Código do erro: " + e.getErrorCode());
            System.out.println("Mensagem: " + e.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                    System.out.println(">>> Recurso Connection liberado via close().");
                } catch (SQLException e) {
                    System.err.println("Falha ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }
}