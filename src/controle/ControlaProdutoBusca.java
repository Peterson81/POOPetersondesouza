package controle;
import dominio.Produto;
import java.util.ArrayList;
import java.util.Scanner;

public class ControlaProdutoBusca {
    // Método estático para busca por código na coleção
    public static Produto buscarPorCodigo(ArrayList<Produto> lista, String codigo) {
        for (Produto p : lista) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return p; // Retorna a referência do produto encontrado
            }
        }
        return null; // Retorna null caso não encontre
    }
    // Método estático para aplicar reajuste percentual em toda a lista
    public static void aplicarReajusteGeral(ArrayList<Produto> lista, double percentual) {
        for (Produto p : lista) {
            double precoAtual = p.getPreco();
            double novoPreco = precoAtual + (precoAtual * (percentual / 100.0));
            p.setPreco(novoPreco);
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Produto> listaProdutos = new ArrayList<>();
        int opcao = 0;
        // Pré-carregamento de alguns produtos para facilitar os testes
        listaProdutos.add(new Produto("P01", "Teclado", 150.00));
        listaProdutos.add(new Produto("P02", "Mouse", 80.00));
        listaProdutos.add(new Produto("P03", "Monitor", 750.00));
        do {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA DE BUSCA E REAJUSTE (1.2)    ");
            System.out.println("========================================");
            System.out.println("1 - Inserir Novo Produto");
            System.out.println("2 - Listar Todos os Produtos");
            System.out.println("3 - Buscar Produto por Código");
            System.out.println("4 - Aplicar Reajuste Geral nos Preços");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer do teclado
            } else {
                System.out.println("Entrada inválida! Digite um número.");
                scanner.nextLine();
                continue;
            }
            switch (opcao) {
                case 1:
                    System.out.println("\n--- Cadastrar Produto ---");
                    System.out.print("Código: ");
                    String cod = scanner.nextLine();
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Preço: R$ ");
                    double preco = scanner.nextDouble();
                    scanner.nextLine();
                    listaProdutos.add(new Produto(cod, nome, preco));
                    System.out.println(">> Produto cadastrado com sucesso!");
                    break;
                case 2:
                    System.out.println("\n--- Lista de Produtos ---");
                    if (listaProdutos.isEmpty()) {
                        System.out.println("Lista vazia.");
                    } else {
                        for (Produto p : listaProdutos) {
                            System.out.println(p);
                        }
                    }
                    break;
                case 3:
                    System.out.println("\n--- Busca por Código ---");
                    System.out.print("Digite o código a ser pesquisado: ");
                    String codigoBusca = scanner.nextLine();
                    // Chamada ao método estático de busca
                    Produto prodEncontrado = buscarPorCodigo(listaProdutos, codigoBusca);
                    if (prodEncontrado != null) {
                        System.out.println("\n>> Produto Encontrado:");
                        System.out.println(prodEncontrado);
                    } else {
                        System.out.println("\n>> Atenção: Produto com o código '" + codigoBusca + "' não foi localizado.");
                    }
                    break;
                case 4:
                    System.out.println("\n--- Reajuste de Preços ---");
                    if (listaProdutos.isEmpty()) {
                        System.out.println("Não há produtos cadastrados para reajustar.");
                        break;
                    }
                    System.out.print("Informe a porcentagem de reajuste (ex: 10 para +10%, -5 para desconto): ");
                    double taxa = scanner.nextDouble();
                    scanner.nextLine();
                    // Aplica a alteração nos objetos da lista
                    aplicarReajusteGeral(listaProdutos, taxa);
                    System.out.println(">> Reajuste de " + taxa + "% aplicado com sucesso a todos os produtos!");
                    break;
                case 0:
                    System.out.println("\nEncerrando o programa...");
                    break;
                default:
                    System.out.println("\nOpção inválida!");
                    break;
            }
        } while (opcao != 0);
        scanner.close();
    }
}

