package model;

import java.time.LocalDate;

public class OrdemServico {

    private int id;
    private String numeroOS;
    private LocalDate dataAbertura;
    private String descricaoDefeito;
    private double valorTotal;
    private ClienteOficina cliente; // lado "N" referencia o pai

    public OrdemServico() { }

    public OrdemServico(String numeroOS, LocalDate dataAbertura, String descricaoDefeito,
                        double valorTotal, ClienteOficina cliente) {
        this.numeroOS = numeroOS;
        this.dataAbertura = dataAbertura;
        this.descricaoDefeito = descricaoDefeito;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
    }

    public OrdemServico(int id, String numeroOS, LocalDate dataAbertura, String descricaoDefeito,
                        double valorTotal, ClienteOficina cliente) {
        this(numeroOS, dataAbertura, descricaoDefeito, valorTotal, cliente);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumeroOS() { return numeroOS; }
    public void setNumeroOS(String numeroOS) { this.numeroOS = numeroOS; }

    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }

    public String getDescricaoDefeito() { return descricaoDefeito; }
    public void setDescricaoDefeito(String descricaoDefeito) { this.descricaoDefeito = descricaoDefeito; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public ClienteOficina getCliente() { return cliente; }
    public void setCliente(ClienteOficina cliente) { this.cliente = cliente; }

    @Override
    public String toString() {
        return "OrdemServico{id=" + id + ", numeroOS='" + numeroOS + "', dataAbertura=" + dataAbertura
                + ", descricaoDefeito='" + descricaoDefeito + "', valorTotal=" + String.format("%.2f", valorTotal)
                + ", cliente=" + (cliente != null ? cliente.getNome() : "null") + "}";
    }
}
