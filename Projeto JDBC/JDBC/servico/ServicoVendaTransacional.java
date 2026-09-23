package br.com.ecommerce.servico;

import br.com.ecommerce.jdbc.FabricaConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * NÍVEL 4 - Controle de transação MANUAL com integridade ACID.
 *
 * O checkout é atômico: debita o estoque do produto E insere o pedido. Se
 * qualquer etapa falhar (incluindo regra de negócio de saldo), nada persiste.
 */
public class ServicoVendaTransacional {

    public boolean processarVenda(String idPedido, String codigoProduto, int quantidadeComprada) {
        Connection conexao = null;
        try {
            conexao = FabricaConexao.obterConexao();

            // Desativa a efetivação automática: passa a existir uma transação aberta
            conexao.setAutoCommit(false);

            // 1) Verifica o saldo disponível em estoque
            int estoqueDisponivel = obterEstoqueDisponivel(conexao, codigoProduto);
            if (estoqueDisponivel < quantidadeComprada) {
                // Exceção de REGRA DE NEGÓCIO interrompe o fluxo deliberadamente
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente. Disponível: " + estoqueDisponivel
                                + ", solicitado: " + quantidadeComprada);
            }

            // 2) Debita a quantidade do estoque
            String update = "UPDATE produto SET quantidade_estoque = quantidade_estoque - ? "
                    + "WHERE codigo = ?";
            try (PreparedStatement debito = conexao.prepareStatement(update)) {
                debito.setInt(1, quantidadeComprada);
                debito.setString(2, codigoProduto);
                debito.executeUpdate();
            }

            // 3) Registra o comprovante do pedido (NOW() é função do PostgreSQL)
            String insert = "INSERT INTO pedido (id_pedido, codigo_produto, quantidade_comprada, data_pedido) "
                    + "VALUES (?, ?, ?, NOW())";
            try (PreparedStatement comprovante = conexao.prepareStatement(insert)) {
                comprovante.setString(1, idPedido);
                comprovante.setString(2, codigoProduto);
                comprovante.setInt(3, quantidadeComprada);
                comprovante.executeUpdate();
            }

            // 4) Ambos os comandos executados sem erro: GRAVA definitivamente
            conexao.commit();
            return true;
        } catch (Exception e) {
            // Qualquer falha desfaz TODAS as alterações parciais do PostgreSQL
            if (conexao != null) {
                try {
                    conexao.rollback();
                } catch (SQLException retorno) {
                    System.err.println("Falha no rollback: " + retorno.getMessage());
                }
            }
            System.err.println("Venda NÃO efetivada: " + e.getMessage());
            return false;
        } finally {
            if (conexao != null) {
                try {
                    // Restaura o comportamento padrão antes de devolver a conexão
                    conexao.setAutoCommit(true);
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Falha ao restaurar/fechar a conexão: " + e.getMessage());
                }
            }
        }
    }

    private int obterEstoqueDisponivel(Connection conexao, String codigoProduto) throws SQLException {
        String sql = "SELECT quantidade_estoque FROM produto WHERE codigo = ?";
        try (PreparedStatement consulta = conexao.prepareStatement(sql)) {
            consulta.setString(1, codigoProduto);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new EstoqueInsuficienteException("Produto não cadastrado: " + codigoProduto);
            }
        }
    }
}