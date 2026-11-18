
package br.com.ecommerce.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import br.com.ecommerce.anotacoes.AuditoriaLogistica;

public class AuditorMetadadosReflection {

    public static void inspecionarClasse(Class<?> clazz) {

        System.out.println("\n==================================");
        System.out.println("AUDITORIA DE METADADOS");
        System.out.println("==================================");

        // Nome completo da classe

        System.out.println(
                "Nome da classe: " + clazz.getName()
        );

        // Superclasse direta

        Class<?> superClasse = clazz.getSuperclass();

        if (superClasse != null) {

            System.out.println(
                    "Superclasse: " + superClasse.getName()
            );

        } else {

            System.out.println("Superclasse: Nenhuma");
        }

        // Verificacao da anotacao

        if (clazz.isAnnotationPresent(
                AuditoriaLogistica.class)) {

            AuditoriaLogistica auditoria =
                    clazz.getAnnotation(AuditoriaLogistica.class);

            System.out.println(
                    "Anotacao AuditoriaLogistica encontrada!"
            );

            System.out.println(
                    "Nivel de criticidade: "
                            + auditoria.nivelCriticidade()
            );

            System.out.println(
                    "Registrar data e hora: "
                            + auditoria.registrarDataHora()
            );

        } else {

            System.out.println(
                    "A classe nao possui a anotacao."
            );
        }

        // Inspecao dos atributos declarados

        System.out.println("\n--- ATRIBUTOS ---");

        Field[] atributos = clazz.getDeclaredFields();

        for (Field atributo : atributos) {

            System.out.println(
                    "Nome: " + atributo.getName()
                            + " | Tipo: " + atributo.getType().getName()
            );
        }

        // Inspecao dos metodos publicos

        System.out.println("\n--- METODOS ---");

        Method[] metodos = clazz.getMethods();

        int quantidade = 0;

        for (Method metodo : metodos) {

            if (metodo.getDeclaringClass().equals(clazz)) {

                System.out.println(
                        "Metodo: " + metodo.getName()
                );

                quantidade++;
            }
        }

        System.out.println(
                "\nTotal de metodos pertencentes a classe: "
                        + quantidade
        );

        System.out.println("==================================");
    }
}
