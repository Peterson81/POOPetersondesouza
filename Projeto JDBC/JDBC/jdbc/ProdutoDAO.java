package br.com.ecommerce.jdbc;

import br.com.ecommerce.modelo.Produto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * NÍVEIS 2 e 3 - Camada de persistência (DAO) de produtos.
 *
 * usa PreparedStatement com marcadores posicionais (?) para prevenir SQL
 * Injection. O mapeamento monetário é feito com setBigDecimal() para garantir
 * precisão contra o tipo NUMERIC do PostgreSQL.
 */
public class ProdutoDAO {

    // ---------------- NÍVEL 2: OPERAÇÕES DML PARAMETRIZADAS ----------------

    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produto (codigo, nome, preco, quantidade_estoque) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, produto.getCodigo());
            comando.setString(2, produto.getNome());
            comando.setBigDecimal(3, produto.getPreco()); // precisão monetária
            comando.setInt(4, produto.getQuantidadeEstoque());

            // executeUpdate retorna o total de linhas afetadas
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao inserir produto: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarPreco(String codigo, BigDecimal novoPreco) {
        String sql = "UPDATE produto SET preco = ? WHERE codigo = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setBigDecimal(1, novoPreco);
            comando.setString(2, codigo);
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao atualizar preço: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(String codigo) {
        String sql = "DELETE FROM produto WHERE codigo = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, codigo);
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao excluir produto: " + e.getMessage());
            return false;
        }
    }

    // ---------------- NÍVEL 3: CONSULTAS E MAPEAMENTO OBJETO-RELACIONAL ----------------

    public List<Produto> listarTodos() {
        String sql = "SELECT codigo, nome, preco, quantidade_estoque FROM produto ORDER BY nome ASC";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet rs = comando.executeQuery()) {

            // Cursor sequencial: avança tupla a tupla com next()
            while (rs.next()) {
                Produto p = new Produto();
                // Acesso MISTO: por posição (1) e por nome de coluna
                p.setCodigo(rs.getString(1));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getBigDecimal("preco"));
                p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produtos.add(p);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    public Produto buscarPorCodigo(String codigo) {
        String sql = "SELECT codigo, nome, preco, quantidade_estoque FROM produto WHERE codigo = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, codigo);
            try (ResultSet rs = comando.executeQuery()) {
                Produto p = null;
                if (rs.next()) {
                    p = new Produto();
                    p.setCodigo(rs.getString(1));
                    p.setNome(rs.getString("nome"));
                    p.setPreco(rs.getBigDecimal("preco"));
                    p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                }
                return p; // null quando nenhuma tupla for encontrada
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao buscar produto: " + e.getMessage());
            return null;
        }
    }

    /*
     * NOTA sobre os try-with-resources acima: os recursos são encerrados em
     * CASCATA e na ordem inversa da declaração (rs -> comando -> conexao),
     * exatamente como pedido: primeiro rs.close(), depois comando.close()
     * e por fim conexao.close().
     */
}