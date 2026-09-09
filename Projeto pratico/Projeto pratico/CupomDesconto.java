package Projeto

import java.util.Objects;

public class CupomDesconto {

    private String codigo;
    private double porcentagem;

    public CupomDesconto(String codigo, double porcentagem) {
        this.codigo = codigo;
        this.porcentagem = porcentagem;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPorcentagem() {
        return porcentagem;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CupomDesconto)) {
            return false;
        }

        CupomDesconto outro = (CupomDesconto) obj;

        return Objects.equals(codigo, outro.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Código: " + codigo +
                " | Desconto: " + porcentagem + "%";
    }
}