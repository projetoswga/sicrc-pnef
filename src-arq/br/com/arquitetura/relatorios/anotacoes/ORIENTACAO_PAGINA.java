package br.com.arquitetura.relatorios.anotacoes;

import ar.com.fdvs.dj.domain.constants.Page;

public enum ORIENTACAO_PAGINA {
    A4_Landscape(Page.Page_A4_Landscape()), A4_Portrait(Page.Page_A4_Portrait());

    private ORIENTACAO_PAGINA(Page page) {
        this.page = page;
    }

    private final Page page;

    public Page getPage() {
        return page;
    }

}
