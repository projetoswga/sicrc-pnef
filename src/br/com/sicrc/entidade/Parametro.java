package br.com.sicrc.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import br.com.arquitetura.entidade.Entidade;

@Entity
@Table(name = "parametro", schema = "SICRC")
@Audited
public class Parametro extends Entidade<Integer> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_parametro", unique = true, nullable = false)
	private Integer id;

	@Column(name = "valor_parametro", nullable = false)
    private String valor;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
