package br.com.sicrc.entidadeexterna.sisfie;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import br.com.arquitetura.entidade.Entidade;

@Entity
@Table(name = "ORGAO", schema = "SISFIE")
@Audited
public class Orgao extends Entidade<Integer> {

    private static final long serialVersionUID = -7596662986854003656L;

    public final static Integer INSTITUICAO_PARTICULAR = 99999;

    @Id
    @Column(name = "ID_ORGAO", unique = true, nullable = false)
    @SequenceGenerator(name = "Orgao", allocationSize = 1, sequenceName = "sicrc.orgao_id_orgao_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Orgao")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGAO_PAI", insertable = true, updatable = true, nullable = true)
    private Orgao orgao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_esfera_gov", insertable = true, updatable = true, nullable = true)
    private EsferaGoverno esferaGoverno;

    @Column(name = "NOM_ORGAO", nullable = false)
    private String nome;

    @Column(name = "LOGRADOURO")
    private String logradouro;

    @Column(name = "TEL_CONTATO")
    private String telefone;

    @Column(name = "SGL_ORGAO")
    private String sigla;

    @Column(name = "NOM_SIGLA")
    private String nomeSiglaFormat;

    public Orgao() {
    }

    public Orgao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Orgao getOrgao() {
        return this.orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNomeSiglaFormat() {
        return nomeSiglaFormat;
    }

    public void setNomeSiglaFormat(String nomeSiglaFormat) {
        this.nomeSiglaFormat = nomeSiglaFormat;
    }

    public EsferaGoverno getEsferaGoverno() {
        return esferaGoverno;
    }

    public void setEsferaGoverno(EsferaGoverno esferaGoverno) {
        this.esferaGoverno = esferaGoverno;
    }

}
