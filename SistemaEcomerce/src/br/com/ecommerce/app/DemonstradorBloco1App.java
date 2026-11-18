
package br.com.ecommerce.app;

import br.com.ecommerce.excecoes.*;
import br.com.ecommerce.logistica.*;

public class DemonstradorBloco1App {

    public static void main(String[] args) {

        try {

            PacoteExpresso pacote = new PacoteExpresso(
                    "LOG12345", 5.5, 0.08, true
            );

            System.out.println(pacote);

        } catch (
                LogisticaException | EstoqueInvalidoException e
        ) {

            System.out.println("Erro: " + e.getMessage());

        }

        System.out.println("\nContador de itens: "
                + ItemLogistico.getContadorItens());

        System.out.println("\nDemonstracao do finally:");

        int resultado = demonstrarFinally();

        System.out.println("Valor retornado: " + resultado);

        try {

            System.out.println("\nExecutando rotina local...");

        } finally {

            System.out.println(
                    "Finally executado para liberar recursos."
            );
        }
    }

    public static int demonstrarFinally() {

        try {

            System.out.println("Executando bloco try.");

            return 100;

        } finally {

            System.out.println(
                    "Finally executado antes do retorno."
            );
        }
    }
}
