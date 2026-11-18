
package br.com.ecommerce.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
        ElementType.TYPE,
        ElementType.METHOD
})

@Retention(RetentionPolicy.RUNTIME)

public @interface AuditoriaLogistica {

    String nivelCriticidade() default "PADRAO";

    boolean registrarDataHora() default true;
}
