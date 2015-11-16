package br.com.sicrc.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.relatorios.anotacoes.ALINHAMENTO_HORIZONTAL;
import br.com.arquitetura.relatorios.anotacoes.ColunaRelatorio;
import br.com.arquitetura.relatorios.anotacoes.Relatorio;

@Relatorio(titulo = "Comites Executivos", nomeArquivo = "comitesexecutivos")
@Entity
@Table(name = "comite_executivo", schema = "SICRC")
@Audited
public class ComiteExecutivo extends Entidade<Integer> {

    private static final long serialVersionUID = -6858979309450522991L;

    @Id
    @SequenceGenerator(name = "comite_executivo_id_comite_executivo_generator", sequenceName = "sicrc.comite_executivo_id_comite_executivo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comite_executivo_id_comite_executivo_generator")
    @Column(name = "id_comite_executivo", unique = true, nullable = false)
    private Integer id;

    @ColunaRelatorio(titulo = "Descrição", largura = 70)
    @Column(name = "dsc_comite_executivo")
    private String descricao;

    @ColunaRelatorio(titulo = "Ativo", largura = 20, alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Column(name = "flg_ativo")
    private Boolean flgAtivo;

    public ComiteExecutivo() {
    }

    public ComiteExecutivo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getFlgAtivo() {
        return this.flgAtivo;
    }

    public void setFlgAtivo(Boolean flgAtivo) {
        this.flgAtivo = flgAtivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}