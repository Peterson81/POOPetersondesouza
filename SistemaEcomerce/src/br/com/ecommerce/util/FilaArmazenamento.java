
package br.com.ecommerce.util;

import br.com.ecommerce.anotacoes.AuditoriaLogistica;

import java.util.NoSuchElementException;

@AuditoriaLogistica(
        nivelCriticidade = "ALTA",
        registrarDataHora = true
)

public class FilaArmazenamento<T> {

    private class No<E> {

        E dado;
        No<E> proximo;

        No(E dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }

    private No<T> inicio;
    private No<T> fim;

    private int tamanho;

    public FilaArmazenamento() {

        inicio = null;
        fim = null;
        tamanho = 0;
    }

    public void enfileirar(T elemento) {

        No<T> novo = new No<>(elemento);

        if (estaVazia()) {

            inicio = novo;
            fim = novo;

        } else {

            fim.proximo = novo;
            fim = novo;
        }

        tamanho++;
    }

    public T desenfileirar() {

        if (estaVazia()) {

            throw new NoSuchElementException(
                    "A fila esta vazia!"
            );
        }

        T elemento = inicio.dado;

        inicio = inicio.proximo;

        tamanho--;

        if (inicio == null) {
            fim = null;
        }

        return elemento;
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return inicio == null;
    }
}
