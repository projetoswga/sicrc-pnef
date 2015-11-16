package br.com.sicrc.entidade;

import java.util.Date;

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

import org.hibernate.envers.Audited;

import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.relatorios.anotacoes.ALINHAMENTO_HORIZONTAL;
import br.com.arquitetura.relatorios.anotacoes.ColunaRelatorio;
import br.com.arquitetura.relatorios.anotacoes.Relatorio;

@Relatorio(nomeArquivo = "datasvalidade", titulo = "Lista de Datas de Validade para Representantes")
@Entity
@Table(name = "validade_representante", catalog = "SICRC")
@Audited
public class ValidadeRepresentante extends Entidade<Integer> {

	private static final long serialVersionUID = -606410215819790522L;

	@Id
	@SequenceGenerator(name = "validade_representante_id_validade_representante_generator", sequenceName = "sicrc.validade_representante_id_validade_representante_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "validade_representante_id_validade_representante_generator")
	@Column(name = "id_validade_representante", unique = true, nullable = false)
	private Integer id;

    @ColunaRelatorio(alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO, titulo = "Data do Cadastro", largura = 30)
    @Temporal(TemporalType.DATE)
    @Column(name = "dt_cadastro", nullable = false, length = 13)
    private Date dtCadastro;

    @ColunaRelatorio(alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO, titulo = "Data de Validade", largura = 30)
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_Validade", nullable = false, length = 13)
	private Date dtValidade;

    @ColunaRelatorio(titulo = "Usuário", atributoObjetoRelatorio = "nome")
	@ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDtValidade() {
		return dtValidade;
	}

	public void setDtValidade(Date dtValidade) {
		this.dtValidade = dtValidade;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
