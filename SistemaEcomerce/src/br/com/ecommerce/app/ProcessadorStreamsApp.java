
package br.com.ecommerce.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import br.com.ecommerce.logistica.PacoteExpresso;
import br.com.ecommerce.excecoes.LogisticaException;

public class ProcessadorStreamsApp {

    public static void main(String[] args) {

        List<PacoteExpresso> pacotes = new ArrayList<>();

        try {

            pacotes.add(
                    new PacoteExpresso("STR10001", 2.0, 0.04, true)
            );

            pacotes.add(
                    new PacoteExpresso("STR10002", 5.0, 0.08, false)
            );

            pacotes.add(
                    new PacoteExpresso("STR10003", 3.0, 0.06, true)
            );

            pacotes.add(
                    new PacoteExpresso("STR10004", 8.0, 0.10, false)
            );

            pacotes.add(
                    new PacoteExpresso("STR10005", 1.5, 0.03, true)
            );

        } catch (LogisticaException e) {

            System.out.println("Erro: " + e.getMessage());
            return;
        }

        System.out.println("=== LISTA ORIGINAL ===");

        pacotes.forEach(System.out::println);

        // 1. Ordenacao com classe interna anonima

        Collections.sort(
                pacotes,
                new Comparator<PacoteExpresso>() {

                    @Override
                    public int compare(
                            PacoteExpresso p1,
                            PacoteExpresso p2
                    ) {

                        return Double.compare(
                                p1.calcularFreteBase(),
                                p2.calcularFreteBase()
                        );
                    }
                }
        );

        System.out.println("\n=== ORDENACAO COM CLASSE ANONIMA ===");

        pacotes.forEach(System.out::println);

        // 2. Ordenacao com expressao Lambda

        Collections.sort(
                pacotes,
                (p1, p2) -> Double.compare(
                        p1.calcularFreteBase(),
                        p2.calcularFreteBase()
                )
        );

        System.out.println("\n=== ORDENACAO COM LAMBDA ===");

        pacotes.forEach(System.out::println);

        // 3. Filtragem dos pacotes com entrega no mesmo dia

        List<PacoteExpresso> entregasRapidas =
                pacotes.stream()

                        .filter(
                                PacoteExpresso::isEntregaNoMesmoDia
                        )

                        .collect(Collectors.toList());

        System.out.println(
                "\n=== PACOTES COM ENTREGA NO MESMO DIA ==="
        );

        entregasRapidas.forEach(System.out::println);

        // 4. Calculo da media de frete

        OptionalDouble media =
                pacotes.stream()

                        .mapToDouble(
                                PacoteExpresso::calcularFreteBase
                        )

                        .average();

        if (media.isPresent()) {

            System.out.println(
                    "\nMedia dos fretes: R$ "
                            + String.format("%.2f", media.getAsDouble())
            );

        } else {

            System.out.println("Nao existem pacotes.");
        }

        // 5. Contagem de pacotes com frete superior a R$ 50

        long quantidade = pacotes.stream()

                .filter(
                        p -> p.calcularFreteBase() > 50.00
                )

                .count();

        System.out.println(
                "\nPacotes com frete acima de R$ 50: "
                        + quantidade
        );

        // 6. Identificacao do pacote com menor frete

        pacotes.stream()

                .min(
                        Comparator.comparingDouble(
                                PacoteExpresso::calcularFreteBase
                        )
                )

                .ifPresent(
                        pacote -> System.out.println(
                                "\nPacote com menor frete: " + pacote
                        )
                );
    }
}
