
package br.com.ecommerce.logistica;

import br.com.ecommerce.excecoes.LogisticaException;
import br.com.ecommerce.excecoes.EstoqueInvalidoException;

import java.util.Date;

public class PacoteExpresso extends ItemLogistico {

    private boolean entregaNoMesmoDia;
    private Date dataPostagem;

    // Exemplo de colisao entre pacotes Date:
    private java.sql.Date dataRegistroBanco;

    public PacoteExpresso(
            String codigoEtiqueta,
            double pesoKg,
            double cubagemM3,
            boolean entregaNoMesmoDia
    ) throws LogisticaException {

        super(codigoEtiqueta, pesoKg, cubagemM3);

        if (pesoKg <= 0) {
            throw new EstoqueInvalidoException(
                    "O peso precisa ser maior que zero."
            );
        }

        this.entregaNoMesmoDia = entregaNoMesmoDia;
        this.dataPostagem = new Date();
        this.dataRegistroBanco = null;
    }

    public PacoteExpresso(
            String codigoEtiqueta,
            double pesoKg
    ) throws LogisticaException {

        this(codigoEtiqueta, pesoKg, 0.05, false);
    }

    public boolean isEntregaNoMesmoDia() {
        return entregaNoMesmoDia;
    }

    @Override
    public double calcularFreteBase() {

        double frete = pesoKg * 8.50;

        frete += cubagemM3 * 20;

        if (entregaNoMesmoDia) {
            frete += 25.00;
        }

        return frete;
    }

    @Override
    public String toString() {

        return super.toString()
                + " | Entrega no mesmo dia: "
                + entregaNoMesmoDia
                + " | Frete: R$ "
                + String.format("%.2f", calcularFreteBase());
    }
}
