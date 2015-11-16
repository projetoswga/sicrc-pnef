package br.com.sicrc.bean;

import java.util.Date;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.arquitetura.bean.PaginableBean;
import br.com.sicrc.entidade.Usuario;
import br.com.sicrc.entidade.ValidadeRepresentante;

@ManagedBean(name = "dataValidadeBean")
@ViewScoped
public class DataValidadeBean extends PaginableBean<ValidadeRepresentante> {

	private static final long serialVersionUID = 3057414754029708292L;

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	
	@Override
	public String save() {
		getModel().setDtCadastro(new Date());
		getModel().setUsuario(new Usuario(loginBean.getUsuario().getId()));
		return super.save();
	}

	@Override
	public Map<String, String> getFilters() {
		return null;
	}

	@Override
	public String getSortField() {
		return null;
	}

	@Override
	public ValidadeRepresentante createModel() {
		return new ValidadeRepresentante();
	}

	@Override
	public String getQualifiedName() {
		return "Data de Validade para Representante";
	}

	@Override
	public boolean isFeminino() {
		return true;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
}
