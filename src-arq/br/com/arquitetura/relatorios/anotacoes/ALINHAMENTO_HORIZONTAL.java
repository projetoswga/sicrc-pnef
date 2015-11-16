package br.com.arquitetura.relatorios.anotacoes;

import ar.com.fdvs.dj.domain.constants.HorizontalAlign;

public enum ALINHAMENTO_HORIZONTAL {
    DIREITA(HorizontalAlign.RIGHT), CENTRO(HorizontalAlign.CENTER), ESQUERDA(HorizontalAlign.LEFT);

    private ALINHAMENTO_HORIZONTAL(HorizontalAlign alinhamento) {
        this.alinhamento = alinhamento;
    }

    private final HorizontalAlign alinhamento;

    public HorizontalAlign getAlinhamento() {
        return alinhamento;
    }
}
