package br.com.sicrc.entidade;

import br.com.arquitetura.entidade.Entidade;

public class TipSisOrgao extends Entidade<Integer> {
	
	private static final long serialVersionUID = -284599660219504482L;
	private Integer id;
	private String descricao;

	public TipSisOrgao() {
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
