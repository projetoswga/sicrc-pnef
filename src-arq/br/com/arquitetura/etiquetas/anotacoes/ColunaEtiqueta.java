package br.com.arquitetura.etiquetas.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColunaEtiqueta {
    int ordem() default 0;

    String atributoObjetoRelatorio() default "descricao";
}
