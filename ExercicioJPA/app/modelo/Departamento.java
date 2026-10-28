package br.com.empresa.modelo;

/**
 * Entidade Departamento mapeada a partir da tabela "departamento" do PostgreSQL.
 * O gerente (gerente_cpf) é um empregado da empresa, podendo ainda não existir.
 */
public class Departamento {

    private String sigla;
    private String nome;
    private String gerenteCpf;
    private String gerenteNome; // preenchido por LEFT JOIN (apenas exibição)

    public Departamento() {
    }

    public Departamento(String sigla, String nome) {
        this.sigla = sigla;
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGerenteCpf() {
        return gerenteCpf;
    }

    public void setGerenteCpf(String gerenteCpf) {
        this.gerenteCpf = gerenteCpf;
    }

    public String getGerenteNome() {
        return gerenteNome;
    }

    public void setGerenteNome(String gerenteNome) {
        this.gerenteNome = gerenteNome;
    }

    @Override
    public String toString() {
        String gerente = "não definido";
        if (gerenteCpf != null) {
            gerente = gerenteCpf + (gerenteNome != null ? " (" + gerenteNome + ")" : "");
        }
        return String.format("Sigla: %-6s | Nome: %-20s | Gerente: %s", sigla, nome, gerente);
    }
}