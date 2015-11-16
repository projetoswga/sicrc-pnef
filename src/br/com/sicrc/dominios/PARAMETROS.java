package br.com.sicrc.dominios;

public enum PARAMETROS {
    REMETENTE_PADRAO(1, "destinatario_padrao"), ETIQUETA_MALA_DIRETA(2, "jrxml_etiqueta_maladireta");

    private PARAMETROS(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    private final Integer id;
    private final String nome;

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}