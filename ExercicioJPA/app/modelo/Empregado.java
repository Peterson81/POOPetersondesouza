package br.com.empresa.modelo;

/**
 * Entidade Empregado mapeada a partir da tabela "empregado" do PostgreSQL.
 */
public class Empregado {

    private String cpf;
    private String nome;
    private String departamentoSigla;
    private String departamentoNome; // preenchido por JOIN (apenas exibição)

    public Empregado() {
    }

    public Empregado(String cpf, String nome, String departamentoSigla) {
        this.cpf = cpf;
        this.nome = nome;
        this.departamentoSigla = departamentoSigla;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDepartamentoSigla() {
        return departamentoSigla;
    }

    public void setDepartamentoSigla(String departamentoSigla) {
        this.departamentoSigla = departamentoSigla;
    }

    public String getDepartamentoNome() {
        return departamentoNome;
    }

    public void setDepartamentoNome(String departamentoNome) {
        this.departamentoNome = departamentoNome;
    }

    @Override
    public String toString() {
        String depto = departamentoSigla
                + (departamentoNome != null ? " (" + departamentoNome + ")" : "");
        return String.format("CPF: %-11s | Nome: %-20s | Departamento: %s",
                cpf, nome, depto);
    }
}