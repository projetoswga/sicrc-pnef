package br.com.arquitetura.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.arquitetura.excecao.ExcecaoUtil;
import br.com.sicrc.util.Constantes;

@ManagedBean(name = "sair")
@RequestScoped
public class Sair {

	public void logout() {
		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

			session.invalidate();

			FacesContext.getCurrentInstance().getExternalContext().redirect("/" + Constantes.CONTEXT_PATH + "/login.jsf");
		} catch (Exception e) {
			ExcecaoUtil.tratarExcecao(e);
		}
	}

}
