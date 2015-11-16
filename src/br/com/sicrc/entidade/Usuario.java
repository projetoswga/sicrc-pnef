package br.com.sicrc.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;

import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.relatorios.anotacoes.ALINHAMENTO_HORIZONTAL;
import br.com.arquitetura.relatorios.anotacoes.ColunaRelatorio;
import br.com.arquitetura.relatorios.anotacoes.ORIENTACAO_PAGINA;
import br.com.arquitetura.relatorios.anotacoes.Relatorio;

@Relatorio(titulo = "Usuários", nomeArquivo = "usuarios", orientacao = ORIENTACAO_PAGINA.A4_Landscape)
@Entity
@Table(name = "USUARIO", schema = "SICRC")
@Audited
public class Usuario extends Entidade<Integer> {

    private static final long serialVersionUID = -8769397922072091316L;

    @Id
    @Column(name = "ID_USUARIO", unique = true, nullable = false)
    @SequenceGenerator(name = "Usuario", allocationSize = 1, sequenceName = "sicrc.usuario_id_usuario_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Usuario")
    private Integer id;

    @ColunaRelatorio(titulo = "Nome")
    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "SENHA", nullable = false)
    private String senha;

    @ColunaRelatorio(titulo = "E-mail")
    @Email(message = "Email inválido")
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @ColunaRelatorio(titulo = "CPF", alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Column(name = "CPF")
    private String cpf;

    @ColunaRelatorio(titulo = "Mala direta", largura = 20, alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Column(name = "FLG_MALA_DIRETA", nullable = false)
    private Boolean flgMalaDireta;

    @ColunaRelatorio(titulo = "Gerenciamento", largura = 20, alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Column(name = "FLG_GERENCIAMENTO", nullable = false)
    private Boolean flgGerenciamento;

    @ColunaRelatorio(titulo = "Exportar", largura = 20, alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Column(name = "FLG_EXPORTAR", nullable = false)
    private Boolean flgExportar;

    @ColunaRelatorio(titulo = "GEREF", largura = 20, alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    @Column(name = "FLG_GEREF", nullable = false)
    private Boolean flgGeref;

    @Column(name = "tp_login")
    private Character tp_login = 'U';

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getFlgMalaDireta() {
        return flgMalaDireta;
    }

    public void setFlgMalaDireta(Boolean flgMalaDireta) {
        this.flgMalaDireta = flgMalaDireta;
    }

    public Boolean getFlgGerenciamento() {
        return flgGerenciamento;
    }

    public void setFlgGerenciamento(Boolean flgGerenciamento) {
        this.flgGerenciamento = flgGerenciamento;
    }

    public Boolean getFlgExportar() {
        return flgExportar;
    }

    public void setFlgExportar(Boolean flgExportar) {
        this.flgExportar = flgExportar;
    }

    public Character getTp_login() {
        return tp_login;
    }

    public void setTp_login(Character tp_login) {
        this.tp_login = tp_login;
    }

    public Boolean getFlgGeref() {
        return flgGeref;
    }

    public void setFlgGeref(Boolean flgGeref) {
        this.flgGeref = flgGeref;
    }
}
