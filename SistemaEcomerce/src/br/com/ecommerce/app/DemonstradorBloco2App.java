
package br.com.ecommerce.app;

import br.com.ecommerce.logistica.PacoteExpresso;
import br.com.ecommerce.util.FilaArmazenamento;

import br.com.ecommerce.excecoes.LogisticaException;
import br.com.ecommerce.logistica.StatusRastreio;

import static br.com.ecommerce.logistica.StatusRastreio.*;
import static br.com.ecommerce.util.CalculadoraLogistica.*;

public class DemonstradorBloco2App {

    public static void main(String[] args) {

        FilaArmazenamento<PacoteExpresso> fila =
                new FilaArmazenamento<>();

        try {

            fila.enfileirar(
                    new PacoteExpresso("PAC10001", 2.0)
            );

            fila.enfileirar(
                    new PacoteExpresso("PAC10002", 5.0)
            );

            fila.enfileirar(
                    new PacoteExpresso(
                            "PAC10003", 3.5, 0.08, true
                    )
            );

        } catch (LogisticaException e) {

            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("Tamanho da fila: "
                + fila.getTamanho());

        while (!fila.estaVazia()) {

            PacoteExpresso pacote = fila.desenfileirar();

            double custo = calcularCustoFinal(
                    pacote.calcularFreteBase(),
                    5.00,
                    3.50,
                    2.00
            );

            System.out.println(pacote);

            System.out.println(
                    formatarResumoOperacao(
                            pacote.getCodigoEtiqueta(),
                            EM_TRANSITO,
                            custo
                    )
            );

            System.out.println("----------------------");
        }

        System.out.println(
                "Status pendente: " + PENDENTE
        );

        System.out.println(
                "Status entregue: " + ENTREGUE
        );

        System.out.println(
                "Status finalizado: " + ENTREGUE.isFinalizado()
        );
    }
}
