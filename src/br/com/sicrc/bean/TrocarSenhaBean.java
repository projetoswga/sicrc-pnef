package br.com.sicrc.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.arquitetura.bean.BaseBean;
import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.excecao.ExcecaoUtil;
import br.com.arquitetura.service.UniversalManager;
import br.com.arquitetura.util.ConstantesARQ;
import br.com.arquitetura.util.FacesMessagesUtil;
import br.com.sicrc.entidade.Representante;
import br.com.sicrc.util.Constantes;
import br.com.sicrc.util.CriptoUtil;

@ManagedBean(name = "trocarSenhaBean")
@ViewScoped
public class TrocarSenhaBean extends BaseBean<Entidade<Integer>> {

	private static final long serialVersionUID = 1L;

	private String senhaT1;
	private String senhaT2;

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	public TrocarSenhaBean() {
		senhaT1 = "";
		senhaT2 = "";
	}

	public String save() {
		try {

			if (senhaT1 == null || senhaT1.trim().equals("")) {
				FacesMessagesUtil.addErrorMessage("Nova Senha", Constantes.CAMPO_OBRIGATORIO);
				return "";
			}

			if (senhaT1.length() < 6) {
				FacesMessagesUtil.addErrorMessage("Nova Senha", " Deve ter mais de 6 caracteres ");
				return "";
			}
			if (senhaT1.equals(Constantes.SENHA_DEFAULT)) {
				FacesMessagesUtil.addErrorMessage("Nova Senha ", " Não pode ser igual a senha inicial.");
				return "";
			}
			if (senhaT2 == null || senhaT2.trim().equals("")) {
				FacesMessagesUtil.addErrorMessage("Confirmação", Constantes.CAMPO_OBRIGATORIO);
				return "";
			}

			Representante user = loginBean.getModel();

			if (!senhaT1.equals(senhaT2)) {
				FacesMessagesUtil.addErrorMessage("", Constantes.AMBAS_SENHAS_IDENTICAS);
				return "";
			}

			user.setSenha(CriptoUtil.getCriptografia(senhaT1));
			Representante userClone = (Representante) user.clone();
			userClone.setFlgPrimeiroAcesso(false);
			this.universalManager.save(userClone);
		} catch (Exception e) {
			FacesMessagesUtil.addErrorMessage(this.getQualifiedName(), ConstantesARQ.ERRO_SALVAR);
			ExcecaoUtil.tratarExcecao(e);
			return "";
		}

		// Abri o popup.
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("confirmation.show()");
		return SUCCESS;

	}

	public String redirecionarPrincipal() {
		return redirect("/pages/principal.jsf");
	}

	public String getQualifiedName() {
		return "Usuário";
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public UniversalManager getUniversalManager() {
		return universalManager;
	}

	public void setUniversalManager(UniversalManager universalManager) {
		this.universalManager = universalManager;
	}

	@Override
	public Representante createModel() {
		return null;
	}

	@Override
	public boolean isFeminino() {
		return false;
	}

	public String getSenhaT1() {
		return senhaT1;
	}

	public void setSenhaT1(String senhaT1) {
		this.senhaT1 = senhaT1;
	}

	public String getSenhaT2() {
		return senhaT2;
	}

	public void setSenhaT2(String senhaT2) {
		this.senhaT2 = senhaT2;
	}

}
