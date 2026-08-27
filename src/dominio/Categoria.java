package dominio;
import java.util.ArrayList;
public class Categoria {
    // Atributos privados
    private int id;
    private String descricao;

    // Atributo de relacionamento N-ário (Coleção de Produtos)
    private ArrayList<Produto> listaProdutos;
    // Construtor
    public Categoria(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
        // Inicialização obrigatória da lista para evitar NullPointerException
        this.listaProdutos = new ArrayList<>();
    }
    // Getters e Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public ArrayList<Produto> getListaProdutos() {
        return listaProdutos;
    }
    // Método para adicionar produto à categoria
    public void adicionarProduto(Produto p) {
        if (p != null && !listaProdutos.contains(p)) {
            listaProdutos.add(p);
            System.out.println(">> Produto '" + p.getNome() + "' associado à categoria '" + this.descricao + "'.");
        }
    }
    // Método para remover produto da categoria
    public void removerProduto(Produto p) {
        if (listaProdutos.contains(p)) {
            listaProdutos.remove(p);
            System.out.println(">> Produto '" + p.getNome() + "' desvinculado da categoria '" + this.descricao + "'.");
        } else {
            System.out.println(">> Produto não encontrado nesta categoria.");
        }
    }
    // Método para listar todos os produtos vinculados
    public void listarProdutos() {
        System.out.println("\n=== Produtos da Categoria: " + this.descricao + " (ID: " + this.id + ") ===");
        if (listaProdutos.isEmpty()) {
            System.out.println("Nenhum produto associado a esta categoria.");
        } else {
            for (Produto p : listaProdutos) {
                System.out.println(p);
            }
            System.out.println("Total de itens na categoria: " + listaProdutos.size());
        }
    }
}
