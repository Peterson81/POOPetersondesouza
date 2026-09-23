package br.com.ecommerce.servico;

/**
 * Regra de negócio: exceção disparada quando o estoque não comporta a venda.
 * Interrompe deliberadamente o fluxo transacional para que o rollback ocorra.
 */
public class EstoqueInsuficienteException extends RuntimeException {

    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }
}