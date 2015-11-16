package br.com.sicrc.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.etiquetas.anotacoes.ColunaEtiqueta;
import br.com.arquitetura.relatorios.anotacoes.ALINHAMENTO_HORIZONTAL;
import br.com.arquitetura.relatorios.anotacoes.ColunaRelatorio;
import br.com.arquitetura.relatorios.anotacoes.ORIENTACAO_PAGINA;
import br.com.arquitetura.relatorios.anotacoes.Relatorio;

@Relatorio(nomeArquivo = "representantes", titulo = "Lista de representantes", orientacao = ORIENTACAO_PAGINA.A4_Landscape)
@Entity
@Table(name = "representante", catalog = "SICRC")
@Audited
public class Representante extends Entidade<Integer> {

    private static final long serialVersionUID = 4353961614151766646L;

    @Id
    @Column(name = "id_representante", unique = true, nullable = false)
    @SequenceGenerator(name = "representante_id_representante_generator", sequenceName = "sicrc.representante_id_representante_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "representante_id_representante_generator")
    private Integer id;

    @ColunaEtiqueta(ordem = 1)
    @ColunaRelatorio(titulo = "Nome")
    @Column(name = "nome")
    private String nome;

    @ColunaEtiqueta(ordem = 4)
    @ColunaRelatorio(titulo = "Município")
    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @ColunaEtiqueta(atributoObjetoRelatorio = "sigla", ordem = 4)
    @Transient
    private Uf uf;

    @ColunaRelatorio(titulo = "Autoridade")
    @Column(name = "nome_autoridade")
    private String nomeAutoridade;

    @ColunaRelatorio(titulo = "Órgão", atributoObjetoRelatorio = "nome")
    @ManyToOne
    @JoinColumn(name = "id_orgao")
    private Orgao orgao;

    @ColunaRelatorio(titulo = "Data do Cadastro", alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Temporal(TemporalType.DATE)
    @Column(name = "dt_cadastro", nullable = false, length = 13)
    private Date dtCadastro;

    @ColunaRelatorio(titulo = "Data Validade", alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Temporal(TemporalType.DATE)
    @Column(name = "dt_Validade", nullable = false, length = 13)
    private Date dtValidade;

    @ColunaRelatorio(titulo = "Comissão Temática")
    @ManyToOne
    @JoinColumn(name = "id_comissao_tematica")
    private ComissaoTematica comissaoTematica;

    @ColunaRelatorio(titulo = "Comite executivo")
    @ManyToOne
    @JoinColumn(name = "id_comite_executivo")
    private ComiteExecutivo comiteExecutivo;

    @ColunaRelatorio(titulo = "Representante GEF", alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Column(name = "FLG_GEF")
    private Boolean flgGef = false;

    @ColunaRelatorio(titulo = "Coordenador DEF", alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Column(name = "FLG_DEF")
    private Boolean flgDef = false;

    @ColunaRelatorio(titulo = "Ativo", alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Column(name = "FLG_ATIVO")
    private Boolean flgAtivo = true;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "senha")
    private String senha;

    @Column(name = "email_login")
    private String email;

    @ColunaEtiqueta(ordem = 2)
    @ManyToOne
    @JoinColumn(name = "id_cargo_autoridade")
    private CargoAutoridade cargoAutoridade;

    @ManyToOne
    @JoinColumn(name = "id_regiao")
    private Regiao regiao;

    @ColunaEtiqueta(ordem = 3)
    @Column(name = "logradouro_autoridade")
    private String logradouroAutoridade;

    @ColunaEtiqueta(ordem = 6)
    @Column(name = "CEP_AUTORIDADE")
    private String cepAutoridade;

    @Column(name = "EMAIL_AUTORIDADE")
    private String emailAutoridade;

    @Column(name = "FLG_PRIMEIRO_ACESSO")
    private Boolean flgPrimeiroAcesso;

    @Column(name = "FLG_REP_SUP_COMITE")
    private Boolean flgRepSupComite;

    @Column(name = "TP_TITULAR_SUPLENTE")
    private Character tpTipoSuplente;

    @Column(name = "tp_login")
    private Character tp_login = 'R';

    @Transient
    private List<RepresentanteArquivo> arquivos = new ArrayList<RepresentanteArquivo>();

    public Representante() {
    }

    public Representante(Integer id) {
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

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDtValidade() {
        return dtValidade;
    }

    public void setDtValidade(Date dtValidade) {
        this.dtValidade = dtValidade;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getFlgPrimeiroAcesso() {
        return flgPrimeiroAcesso;
    }

    public void setFlgPrimeiroAcesso(Boolean flgPrimeiroAcesso) {
        this.flgPrimeiroAcesso = flgPrimeiroAcesso;
    }

    public String getNomeAutoridade() {
        return nomeAutoridade;
    }

    public void setNomeAutoridade(String nomeAutoridade) {
        this.nomeAutoridade = nomeAutoridade;
    }

    public String getLogradouroAutoridade() {
        return logradouroAutoridade;
    }

    public void setLogradouroAutoridade(String logradouroAutoridade) {
        this.logradouroAutoridade = logradouroAutoridade;
    }

    public CargoAutoridade getCargoAutoridade() {
        return cargoAutoridade;
    }

    public void setCargoAutoridade(CargoAutoridade cargoAutoridade) {
        this.cargoAutoridade = cargoAutoridade;
    }

    public String getCepAutoridade() {
        return cepAutoridade;
    }

    public void setCepAutoridade(String cepAutoridade) {
        this.cepAutoridade = cepAutoridade;
    }

    public String getEmailAutoridade() {
        return emailAutoridade;
    }

    public void setEmailAutoridade(String emailAutoridade) {
        this.emailAutoridade = emailAutoridade;
    }

    public Boolean getFlgGef() {
        return flgGef;
    }

    public void setFlgGef(Boolean flgGef) {
        this.flgGef = flgGef;
    }

    public Boolean getFlgDef() {
        return flgDef;
    }

    public void setFlgDef(Boolean flgDef) {
        this.flgDef = flgDef;
    }

    public Boolean getFlgRepSupComite() {
        return flgRepSupComite;
    }

    public void setFlgRepSupComite(Boolean flgRepSupComite) {
        this.flgRepSupComite = flgRepSupComite;
    }

    public Character getTpTipoSuplente() {
        return tpTipoSuplente;
    }

    public void setTpTipoSuplente(Character tpTipoSuplente) {
        this.tpTipoSuplente = tpTipoSuplente;
    }

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

    public List<RepresentanteArquivo> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<RepresentanteArquivo> arquivos) {
        this.arquivos = arquivos;
    }

    public Boolean getFlgAtivo() {
        if (flgAtivo == null) {
            flgAtivo = false;
        }
        return flgAtivo;
    }

    public void setFlgAtivo(Boolean flgAtivo) {
        this.flgAtivo = flgAtivo;
    }

    public Character getTp_login() {
        return tp_login;
    }

    public void setTp_login(Character tp_login) {
        this.tp_login = tp_login;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public ComissaoTematica getComissaoTematica() {
        return comissaoTematica;
    }

    public void setComissaoTematica(ComissaoTematica comissaoTematica) {
        this.comissaoTematica = comissaoTematica;
    }

    public ComiteExecutivo getComiteExecutivo() {
        return comiteExecutivo;
    }

    public void setComiteExecutivo(ComiteExecutivo comiteExecutivo) {
        this.comiteExecutivo = comiteExecutivo;
    }

    public Uf getUf() {
        if (municipio != null) {
            uf = municipio.getUf();
        }
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
    }

}