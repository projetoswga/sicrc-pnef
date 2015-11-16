package br.com.sicrc.entidade;

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
@Table(name = "representante_comite", catalog = "SICRC")
@Audited
public class RepresentanteComite extends Entidade<Integer> {

	private static final long serialVersionUID = -2326539107032158131L;

	@Id
	@SequenceGenerator(name = "representante_comite_id_representante_comite_generator", sequenceName = "sicrc.representante_comite_id_representante_comite_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "representante_comite_id_representante_comite_generator")
	@Column(name = "id_representante_comite", unique = true, nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_comite_executivo")
	private ComiteExecutivo comiteExecutivo;

	@ManyToOne
	@JoinColumn(name = "id_representante")
	private Representante representante;

	public RepresentanteComite() {
	}

	public RepresentanteComite(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ComiteExecutivo getComiteExecutivo() {
		return this.comiteExecutivo;
	}

	public void setComiteExecutivo(ComiteExecutivo comiteExecutivo) {
		this.comiteExecutivo = comiteExecutivo;
	}

	public Representante getRepresentante() {
		return this.representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}
}