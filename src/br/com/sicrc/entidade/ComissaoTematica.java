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

@Relatorio(titulo = "Comissões Temáticas", nomeArquivo = "comissoestematicas")
@Entity
@Table(name = "comissao_tematica", schema = "SICRC")
@Audited
public class ComissaoTematica extends Entidade<Integer> {

    private static final long serialVersionUID = 3388769996258245307L;

    @Id
    @SequenceGenerator(name = "comissao_tematica_id_comissao_tematica_generator", sequenceName = "sicrc.comissao_tematica_id_comissao_tematica_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comissao_tematica_id_comissao_tematica_generator")
    @Column(name = "id_comissao_tematica", unique = true, nullable = false)
    private Integer id;

    @ColunaRelatorio(titulo = "Descrição", largura = 70)
    @Column(name = "dsc_comissao_tecnica")
    private String descricao;

    @ColunaRelatorio(titulo = "Ativo", largura = 20, alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Column(name = "flg_ativo")
    private Boolean flgAtivo;

    public ComissaoTematica() {
    }

    public ComissaoTematica(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

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
}