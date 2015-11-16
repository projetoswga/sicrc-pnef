package br.com.sicrc.entidade;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "comissao_representante", catalog = "SICRC")
@Audited
public class ComissaoRepresentante extends Entidade<Integer> {

	private static final long serialVersionUID = 4627077423779029429L;

	@Id
	@SequenceGenerator(name = "comissao_representante_id_comissao_representante_generator", sequenceName = "sicrc.comissao_representante_id_comissao_representante_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comissao_representante_id_comissao_representante_generator")
	@Column(name = "id_comissao_representante", unique = true, nullable = false)
	private Integer id;

	@Column(name = "dt_cadastro")
	private Timestamp dtCadastro;

	@ManyToOne
	@JoinColumn(name = "id_comissao_tematica")
	private ComissaoTematica comissaoTematica;

	@ManyToOne
	@JoinColumn(name = "id_representante")
	private Representante representante;

	public ComissaoRepresentante() {
	}

	public ComissaoRepresentante(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDtCadastro() {
		return this.dtCadastro;
	}

	public void setDtCadastro(Timestamp dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public ComissaoTematica getComissaoTematica() {
		return comissaoTematica;
	}

	public void setComissaoTematica(ComissaoTematica comissaoTematica) {
		this.comissaoTematica = comissaoTematica;
	}
}