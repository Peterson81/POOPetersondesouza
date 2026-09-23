package br.com.ecommerce.app;

import br.com.ecommerce.jdbc.ProdutoDAO;
import br.com.ecommerce.jdbc.RecursosAvancadosDAO;
import br.com.ecommerce.jdbc.TesteConexaoPostgreSQL;
import br.com.ecommerce.modelo.Produto;
import br.com.ecommerce.servico.ServicoVendaTransacional;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Aplicação principal do Módulo 11 (JDBC + PostgreSQL).
 *
 * Requer o banco "bdecommerce" criado conforme o arquivo sql/bdecommerce.sql
 * e o PostgreSQL acessível em localhost:5432.
 */
public class MainEcommerce {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        ProdutoDAO dao = new ProdutoDAO();
        RecursosAvancadosDAO avancados = new RecursosAvancadosDAO();
        ServicoVendaTransacional venda = new ServicoVendaTransacional();

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            System.out.print("Opção: ");
            opcao = lerInteiro();
            SC.nextLine(); // limpa o buffer após o número

            switch (opcao) {
                case 1 -> TesteConexaoPostgreSQL.main(new String[0]);

                case 2 -> { // NÍVEL 2 - inserir produto
                    System.out.print("Código do produto: ");
                    String codigo = SC.nextLine();
                    System.out.print("Nome: ");
                    String nome = SC.nextLine();
                    System.out.print("Preço (ex.: 199.90): ");
                    BigDecimal preco = lerBigDecimal();
                    System.out.print("Estoque inicial: ");
                    int estoque = lerInteiro();
                    SC.nextLine();

                    boolean ok = dao.inserir(new Produto(codigo, nome, preco, estoque));
                    System.out.println(ok ? ">>> Produto inserido com sucesso."
                            : ">>> Falha ao inserir o produto.");
                }

                case 3 -> { // NÍVEL 2 - atualizar preço
                    System.out.print("Código do produto: ");
                    String codigo = SC.nextLine();
                    System.out.print("Novo preço: ");
                    BigDecimal novoPreco = lerBigDecimal();
                    SC.nextLine();

                    boolean ok = dao.atualizarPreco(codigo, novoPreco);
                    System.out.println(ok ? ">>> Preço atualizado."
                            : ">>> Nenhuma linha alterada (código inexistente?).");
                }

                case 4 -> { // NÍVEL 2 - excluir produto
                    System.out.print("Código do produto a excluir: ");
                    String codigo = SC.nextLine();
                    boolean ok = dao.excluir(codigo);
                    System.out.println(ok ? ">>> Produto excluído."
                            : ">>> Nenhuma linha excluída.");
                }

                case 5 -> { // NÍVEL 3 - listar todos
                    System.out.println("\n--- Catálogo de produtos (ordem alfabética) ---");
                    dao.listarTodos().forEach(System.out::println);
                }

                case 6 -> { // NÍVEL 3 - buscar por código
                    System.out.print("Código do produto: ");
                    String codigo = SC.nextLine();
                    Produto p = dao.buscarPorCodigo(codigo);
                    System.out.println(p != null ? p : ">>> Produto não encontrado.");
                }

                case 7 -> { // NÍVEL 4 - venda transacional
                    System.out.print("ID do pedido: ");
                    String idPedido = SC.nextLine();
                    System.out.print("Código do produto: ");
                    String codigo = SC.nextLine();
                    System.out.print("Quantidade comprada: ");
                    int quantidade = lerInteiro();
                    SC.nextLine();

                    boolean ok = venda.processarVenda(idPedido, codigo, quantidade);
                    System.out.println(ok
                            ? ">>> Venda confirmada e transação COMMITADA."
                            : ">>> Venda cancelada (rollback executado).");
                }

                case 8 -> avancados.demonstrarCursorRolaVel();

                case 9 -> { // NÍVEL 5 - stored procedure
                    System.out.print("Código do produto: ");
                    String codigo = SC.nextLine();
                    avancados.executarProcedureSaldo(codigo);
                }

                case 0 -> System.out.println("Encerrando aplicação...");

                default -> System.out.println("Opção inválida!");
            }
            if (opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                SC.nextLine();
            }
        }
        SC.close();
    }

    private static void exibirMenu() {
        System.out.println("\n============================================================");
        System.out.println("  SISTEMA E-COMMERCE - PERSISTÊNCIA JDBC + POSTGRESQL");
        System.out.println("============================================================");
        System.out.println("  NÍVEL 1   [1] Testar conexão com o PostgreSQL");
        System.out.println("  NÍVEL 2   [2] Inserir produto   [3] Atualizar preço   [4] Excluir");
        System.out.println("  NÍVEL 3   [5] Listar produtos   [6] Buscar por código");
        System.out.println("  NÍVEL 4   [7] Processar venda (transação ACID)");
        System.out.println("  NÍVEL 5   [8] Cursor rolável    [9] Procedure de saldo");
        System.out.println("------------------------------------------------------------");
        System.out.println("  [0] Sair");
    }

    private static int lerInteiro() {
        while (!SC.hasNextInt()) {
            System.out.print("Valor inválido. Digite um inteiro: ");
            SC.next();
        }
        return SC.nextInt();
    }

    private static BigDecimal lerBigDecimal() {
        while (!SC.hasNextBigDecimal()) {
            System.out.print("Valor inválido. Digite um número: ");
            SC.next();
        }
        return SC.nextBigDecimal();
    }
}