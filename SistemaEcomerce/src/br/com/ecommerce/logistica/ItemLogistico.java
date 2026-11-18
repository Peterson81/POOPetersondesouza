
package br.com.ecommerce.logistica;

import br.com.ecommerce.excecoes.LogisticaException;

public abstract class ItemLogistico {

    private static int contadorItens;

    static {
        contadorItens = 0;
        System.out.println("Sistema logistico inicializado.");
    }

    private String codigoEtiqueta;
    protected double pesoKg;
    double cubagemM3;

    private boolean despachado;

    public ItemLogistico(
            String codigoEtiqueta,
            double pesoKg,
            double cubagemM3
    ) throws LogisticaException {

        if (codigoEtiqueta == null || codigoEtiqueta.length() < 5) {
            throw new LogisticaException(
                    "Codigo da etiqueta invalido!"
            );
        }

        this.codigoEtiqueta = codigoEtiqueta;
        this.pesoKg = pesoKg;
        this.cubagemM3 = cubagemM3;
        this.despachado = false;

        contadorItens++;
    }

    public abstract double calcularFreteBase();

    public String getCodigoEtiqueta() {
        return codigoEtiqueta;
    }

    public boolean isDespachado() {
        return despachado;
    }

    public void setDespachado(boolean despachado) {
        this.despachado = despachado;
    }

    public static int getContadorItens() {
        return contadorItens;
    }

    @Override
    public String toString() {
        return "Etiqueta: " + codigoEtiqueta
                + " | Peso: " + pesoKg
                + " kg | Cubagem: " + cubagemM3
                + " m3 | Despachado: " + despachado;
    }
}
