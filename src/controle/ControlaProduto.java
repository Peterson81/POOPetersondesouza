package controle;

import dominio.Produto;
import java.util.ArrayList;
import java.util.Scanner;

public class ControlaProduto {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Produto> listaProdutos = new ArrayList<>();
        int opcao = 0;
        do {
            System.out.println("\n========================================");
            System.out.println("       SISTEMA DE CONTROLE DE PRODUTOS   ");
            System.out.println("========================================");
            System.out.println("1 - Inserir Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("3 - Remover Produto por Código");
            System.out.println("4 - Remover Produto por Posição (Índice)");
            System.out.println("5 - Exibir Quantidade Total");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            // Leitura da opção
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); //
            } else {
                System.out.println("Entrada inválida! Digite apenas números.");
                scanner.nextLine();
                continue;
            }
            switch (opcao) {
                case 1:

                    System.out.println("\n--- Cadastrar Novo Produto ---");
                    System.out.print("Informe o código: ");
                    String codigo = scanner.nextLine().trim();
                    System.out.print("Informe o nome: ");
                    String nome = scanner.nextLine().trim();
                    System.out.print("Informe o preço: R$ ");
                    double preco = scanner.nextDouble();
                    scanner.nextLine(); //
                    Produto novoProduto = new Produto(codigo, nome, preco);
                    listaProdutos.add(novoProduto);
                    System.out.println(">> Produto cadastrado com sucesso!");
                    break;
                case 2:

                    System.out.println("\n--- Lista de Produtos Cadastrados ---");
                    if (listaProdutos.isEmpty()) {
                        System.out.println("Nenhum produto cadastrado no momento.");
                    } else {
                        for (int i = 0; i < listaProdutos.size(); i++) {
                            System.out.println("[" + i + "] " + listaProdutos.get(i));
                        }
                    }
                    break;
                case 3:

                    System.out.println("\n--- Remover Produto por Código ---");
                    if (listaProdutos.isEmpty()) {
                        System.out.println("A lista está vazia.");
                        break;
                    }
                    System.out.print("Informe o código do produto a remover: ");
                    String codRemover = scanner.nextLine().trim();
                    Produto produtoEncontrado = null;
                    for (Produto p : listaProdutos) {
                        if (p.getCodigo().equalsIgnoreCase(codRemover)) {
                            produtoEncontrado = p;
                            break;
                        }
                    }
                    if (produtoEncontrado != null) {
                        listaProdutos.remove(produtoEncontrado); // Remoção por objeto
                        System.out.println(">> Produto '" + produtoEncontrado.getNome() + "' removido com sucesso!");
                    } else {
                        System.out.println(">> Produto com código '" + codRemover + "' não encontrado.");
                    }
                    break;
                case 4:
                    // Remoção por índice
                    System.out.println("\n--- Remover Produto por Índice ---");
                    if (listaProdutos.isEmpty()) {
                        System.out.println("A lista está vazia.");
                        break;
                    }
                    System.out.print("Informe o índice (0 a " + (listaProdutos.size() - 1) + "): ");
                    int indice = scanner.nextInt();
                    scanner.nextLine(); // Limpa buffer
                    if (indice >= 0 && indice < listaProdutos.size()) {
                        Produto removido = listaProdutos.remove(indice); // Remoção por índice
                        System.out.println(">> Produto '" + removido.getNome() + "' removido da posição " + indice + ".");
                    } else {
                        System.out.println(">> Índice inválido! Posição não existe na lista.");
                    }
                    break;
                case 5:

                    System.out.println("\n--- Quantidade de Itens ---");
                    System.out.println("Total de produtos cadastrados na lista: " + listaProdutos.size());
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema...");
                    break;
                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 0);
        scanner.close();
    }
}
