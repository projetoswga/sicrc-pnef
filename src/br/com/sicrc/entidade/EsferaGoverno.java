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

@Entity
@Table(name = "esfera_governo", catalog = "SICRC")
@Audited
public class EsferaGoverno extends Entidade<Integer> {

	private static final long serialVersionUID = 1L;
	
	public static final Integer ESTADUAL = 1;
	public static final Integer FEDERAL = 2;
	public static final Integer MUNICIPAL = 3;

	@Id
	@Column(name = "id_esfera_gov", unique = true, nullable = false)
	@SequenceGenerator(name = "EsferaGoverno", allocationSize = 1, sequenceName = "sicrc.esfera_governo_id_esfera_gov_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EsferaGoverno")
	private Integer id;

	@Column(name = "dsc_esfera_gov", nullable = false)
	private String descricao;

	public EsferaGoverno() {
	}

	public EsferaGoverno(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
