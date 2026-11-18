
package br.com.ecommerce.excecoes;

public class EstoqueInvalidoException extends RuntimeException {

    public EstoqueInvalidoException(String mensagem) {
        super(mensagem);
    }
}
