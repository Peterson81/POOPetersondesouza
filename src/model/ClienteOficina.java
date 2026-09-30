package model;

import java.util.ArrayList;
import java.util.List;

public class ClienteOficina {

    private int id;
    private String nome;
    private String telefone;
    private String cpf;
    private List<OrdemServico> ordens = new ArrayList<>(); // lado "1" possui lista do lado "N"

    public ClienteOficina() { }

    public ClienteOficina(String nome, String telefone, String cpf) {
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
    }

    public ClienteOficina(int id, String nome, String telefone, String cpf) {
        this(nome, telefone, cpf);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public List<OrdemServico> getOrdens() { return ordens; }
    public void setOrdens(List<OrdemServico> ordens) { this.ordens = ordens; }

    public void adicionarOrdem(OrdemServico os) {
        os.setCliente(this);
        this.ordens.add(os);
    }

    @Override
    public String toString() {
        return "ClienteOficina{id=" + id + ", nome='" + nome + "', telefone='" + telefone
                + "', cpf='" + cpf + "', qtdOS=" + ordens.size() + "}";
    }
}
