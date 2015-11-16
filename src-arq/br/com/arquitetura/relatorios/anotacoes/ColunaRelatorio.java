package br.com.arquitetura.relatorios.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColunaRelatorio {
    String titulo();

    int largura() default 50;

    ALINHAMENTO_HORIZONTAL alinhamento() default ALINHAMENTO_HORIZONTAL.ESQUERDA;

    String atributoObjetoRelatorio() default "descricao";
}