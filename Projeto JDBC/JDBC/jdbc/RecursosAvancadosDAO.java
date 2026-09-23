package br.com.ecommerce.jdbc;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * NÍVEL 5 - Recursos avançados: cursores roláveis e stored procedures.
 */
public class RecursosAvancadosDAO {

    public void demonstrarCursorRolaVel() {
        String sql = "SELECT codigo, nome, preco, quantidade_estoque FROM produto ORDER BY preco ASC";

        // Cursor ROLÁVEL e SOMENTE LEITURA: permite navegação não sequencial
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(
                     sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = comando.executeQuery()) {

            // rs.last(): salta para o último registro (mais caro)
            if (rs.last()) {
                System.out.println("rs.last()    -> linha " + rs.getRow() + " (mais caro): "
                        + formatar(rs));
            } else {
                System.out.println("Nenhum registro no catálogo para navegar.");
                return;
            }

            // rs.previous(): retrocede para a tupla imediatamente anterior
            if (rs.previous()) {
                System.out.println("rs.previous()-> linha " + rs.getRow() + ": " + formatar(rs));
            }

            // rs.first(): salta para o primeiro registro (mais barato)
            if (rs.first()) {
                System.out.println("rs.first()   -> linha " + rs.getRow() + " (mais barato): "
                        + formatar(rs));
            }

            // rs.absolute(2): posiciona diretamente na segunda tupla
            if (rs.absolute(2)) {
                System.out.println("rs.absolute(2) -> linha 2: " + formatar(rs));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha na navegação do cursor rolável: " + e.getMessage());
        }
    }

    public void executarProcedureSaldo(String codigoProduto) {
        // CallableStatement via sintaxe de escape JDBC para procedures
        String sql = "{call sp_calcular_saldo_estoque(?, ?, ?)}";
        try (Connection conexao = FabricaConexao.obterConexao();
             CallableStatement chamada = conexao.prepareCall(sql)) {

            // Parâmetro de entrada (IN)
            chamada.setString(1, codigoProduto);

            // Registro dos parâmetros de saída (OUT)
            chamada.registerOutParameter(2, Types.INTEGER);
            chamada.registerOutParameter(3, Types.NUMERIC);

            // Executa a rotina corporativa no servidor do banco
            chamada.execute();

            int quantidade = chamada.getInt(2);
            BigDecimal totalReais = chamada.getBigDecimal(3);

            System.out.println(">>> Saldo em unidades do produto " + codigoProduto + ": " + quantidade);
            System.out.println(">>> Patrimônio monetário em estoque: R$ " + totalReais);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao executar a procedure: " + e.getMessage());
        }
    }

    private String formatar(ResultSet rs) throws SQLException {
        // Demonstra get por POSIÇÃO e por NOME de coluna
        return rs.getString(1) + " | " + rs.getString("nome")
                + " | R$ " + rs.getBigDecimal("preco")
                + " | est. " + rs.getInt("quantidade_estoque");
    }
}