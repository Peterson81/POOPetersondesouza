
package br.com.ecommerce.logistica;

public enum StatusRastreio {

    PENDENTE(5, false),
    EM_TRANSITO(3, false),
    SAIU_PARA_ENTREGA(1, true),
    ENTREGUE(0, true),
    EXTRAVIADO(0, false);

    private final int prazoDiasUteis;
    private final boolean requerAssinatura;

    StatusRastreio(
            int prazoDiasUteis,
            boolean requerAssinatura
    ) {

        this.prazoDiasUteis = prazoDiasUteis;
        this.requerAssinatura = requerAssinatura;
    }

    public int getPrazoDiasUteis() {
        return prazoDiasUteis;
    }

    public boolean isRequerAssinatura() {
        return requerAssinatura;
    }

    public boolean isFinalizado() {

        return this == ENTREGUE || this == EXTRAVIADO;
    }
}
