package br.com.ecommerce.modelo;

import java.math.BigDecimal;

/**
 * Entidade Produto mapeada a partir da tabela "produto" do PostgreSQL.
 */
public class Produto {

    private String codigo;
    private String nome;
    private BigDecimal preco;
    private int quantidadeEstoque;

    public Produto() {
    }

    public Produto(String codigo, String nome, BigDecimal preco, int quantidadeEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @Override
    public String toString() {
        return String.format("Cód: %-6s | Nome: %-22s | Preço: R$ %-8.2f | Estoque: %d",
                codigo, nome, preco, quantidadeEstoque);
    }
}
