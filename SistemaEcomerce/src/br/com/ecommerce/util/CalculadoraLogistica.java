
package br.com.ecommerce.util;

import br.com.ecommerce.anotacoes.AuditoriaLogistica;
import br.com.ecommerce.logistica.StatusRastreio;

public class CalculadoraLogistica {

    @AuditoriaLogistica(
            nivelCriticidade = "MEDIA",
            registrarDataHora = true
    )

    public static double calcularCustoFinal(
            double valorBase,
            double... taxasAdicionais
    ) {

        double total = valorBase;

        for (double taxa : taxasAdicionais) {

            total += taxa;
        }

        return total;
    }

    public static String formatarResumoOperacao(
            String codigo,
            StatusRastreio status,
            double valorFinal
    ) {

        return String.format(
                "Codigo: %s | Status: %s | Valor final: R$ %.2f",
                codigo,
                status,
                valorFinal
        );
    }
}
