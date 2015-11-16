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
@Table(name = "regiao", catalog = "SICRC")
@Audited
public class Regiao extends Entidade<Integer> {

	private static final long serialVersionUID = 203412601945259760L;

	@Id
	@Column(name = "id_regiao", unique = true, nullable = false)
	@SequenceGenerator(name = "regiao_id_regiao_generator", sequenceName = "sicrc.regiao_id_regiao_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regiao_id_regiao_generator")
	private Integer id;

	@Column(name = "descricao")
	private String descricao;
	
	public Regiao() {
	}
	
	public Regiao(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
