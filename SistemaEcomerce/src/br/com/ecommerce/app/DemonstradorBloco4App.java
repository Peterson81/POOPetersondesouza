
package br.com.ecommerce.app;

import br.com.ecommerce.util.FilaArmazenamento;
import br.com.ecommerce.util.AuditorMetadadosReflection;
import br.com.ecommerce.logistica.PacoteExpresso;

public class DemonstradorBloco4App {

    public static void main(String[] args) {

        // =====================================
        // 1. POOL DE LITERAIS DE STRINGS
        // =====================================

        System.out.println("=== POOL DE STRINGS ===");

        String texto1 = "Logistica";
        String texto2 = "Logistica";

        String texto3 = new String("Logistica");

        System.out.println(
                "texto1 == texto2: " + (texto1 == texto2)
        );

        System.out.println(
                "texto1 == texto3: " + (texto1 == texto3)
        );

        System.out.println(
                "texto1.equals(texto3): "
                        + texto1.equals(texto3)
        );


        // =====================================
        // 2. POOL DE WRAPPERS INTEGER
        // =====================================

        System.out.println("\n=== POOL DE INTEGER ===");

        Integer numero1 = 100;
        Integer numero2 = 100;

        System.out.println(
                "100 usando ==: " + (numero1 == numero2)
        );

        System.out.println(
                "100 usando equals: "
                        + numero1.equals(numero2)
        );

        Integer numero3 = 500;
        Integer numero4 = 500;

        System.out.println(
                "500 usando ==: " + (numero3 == numero4)
        );

        System.out.println(
                "500 usando equals: "
                        + numero3.equals(numero4)
        );


        // =====================================
        // 3. STRING BUILDER
        // =====================================

        System.out.println("\n=== MANIFESTO DE CARGA ===");

        StringBuilder manifesto = new StringBuilder();

        manifesto.append("MANIFESTO LOGISTICO\n");

        manifesto.append("------------------------\n");

        manifesto.append("Pacote: PAC001 - 2kg\n");

        manifesto.append("Pacote: PAC002 - 5kg\n");

        manifesto.append("Pacote: PAC003 - 3kg\n");

        manifesto.append("Pacote: PAC004 - 8kg\n");

        manifesto.append("------------------------\n");

        manifesto.append("Total de pacotes: 4\n");

        System.out.println(manifesto.toString());


        // =====================================
        // 4. METODOS DA CLASSE CHARACTER
        // =====================================

        System.out.println("=== CHARACTER ===");

        char caractere1 = '7';
        char caractere2 = 'A';

        System.out.println(
                "O caractere 7 e digito? "
                        + Character.isDigit(caractere1)
        );

        System.out.println(
                "O caractere A e maiusculo? "
                        + Character.isUpperCase(caractere2)
        );


        // =====================================
        // 5. CONVERSAO COM INTEGER.PARSEINT
        // =====================================

        System.out.println("\n=== CONVERSAO DE TEXTO ===");

        String quantidadeTexto = "250";

        int quantidade = Integer.parseInt(quantidadeTexto);

        System.out.println(
                "Quantidade convertida: " + quantidade
        );

        System.out.println(
                "Quantidade apos multiplicacao: "
                        + (quantidade * 2)
        );


        // =====================================
        // 6. CONSTANTES ESPECIAIS FLOAT
        // =====================================

        System.out.println("\n=== CONSTANTES FLOAT ===");

        System.out.println(
                "Float.NaN: " + Float.NaN
        );

        System.out.println(
                "Float.POSITIVE_INFINITY: "
                        + Float.POSITIVE_INFINITY
        );


        // =====================================
        // 7. REFLECTION
        // =====================================

        System.out.println("\n=== REFLECTION ===");

        AuditorMetadadosReflection.inspecionarClasse(
                FilaArmazenamento.class
        );

        AuditorMetadadosReflection.inspecionarClasse(
                PacoteExpresso.class
        );
    }
}
