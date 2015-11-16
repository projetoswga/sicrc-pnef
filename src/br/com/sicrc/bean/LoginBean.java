package br.com.sicrc.bean;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.arquitetura.bean.BaseBean;
import br.com.arquitetura.excecao.ExcecaoUtil;
import br.com.arquitetura.util.ConstantesARQ;
import br.com.arquitetura.util.FacesMessagesUtil;
import br.com.sicrc.entidade.Representante;
import br.com.sicrc.entidade.Usuario;
import br.com.sicrc.service.LoginService;
import br.com.sicrc.util.Constantes;
import br.com.sicrc.util.CpfUtil;
import br.com.sicrc.util.CriptoUtil;
import br.com.sicrc.util.PasswordUtil;
import br.com.sicrc.util.ValidacaoUtil;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean extends BaseBean<Representante> {

	private static final long serialVersionUID = 4642443873495984558L;

	@ManagedProperty(value = "#{loginManager}")
	private AuthenticationManager am;

	@ManagedProperty(value = "#{loginService}")
	private LoginService loginService;

	private String userName;
	private String password;
	private String senhaAnterior;
	private String senha;
	private String senha2;
	private Usuario usuario;
	private Integer qtdTentativa = 0;
	private String context = getContextPath();
	private boolean mostrarMsgInvalidate;
	private boolean mostrarDataValidade;
	private String cpf;
	private Character tipoUsuario;
	private String caminhoImagemPnef = "../resources/design/imagem-default/logo-pnef-branca.png";

	/* Captcha */
	private Integer num1 = 0;
	private Integer num2 = 0;
	private Integer num3 = 0;
	private String resposta;
	private String pergunta;
	
	public void verificarSessionInvalidate() {
		Boolean mostrar = (Boolean) getSessionMap().remove("mostrarMsgSession");
		if (mostrar != null && mostrar) {
			mostrarMsgInvalidate = true;
		} else {
			mostrarMsgInvalidate = false;
		}
	}

	@SuppressWarnings("unchecked")
	public String logar() {
		try {
			mostrarDataValidade = false;
			qtdTentativa++;

			if (cpf.trim() == null || cpf.trim().equals("")) {
				FacesMessagesUtil.addErrorMessage("Login ", Constantes.CAMPO_OBRIGATORIO);
				return "";
			}
			cpf = cpf.replaceAll("[()-.]", "");
			if (!CpfUtil.isValidCPF(cpf)) {
				FacesMessagesUtil.addErrorMessage("CPF ", "Inválido");
				return "";
			}
			if (senha == null || senha.equals("")) {
				FacesMessagesUtil.addErrorMessage("Senha ", Constantes.CAMPO_OBRIGATORIO);
				return "";
			}
			if (!Character.isLetter(tipoUsuario)) {
				FacesMessagesUtil.addErrorMessage("Tipo de Usuário ", Constantes.CAMPO_OBRIGATORIO);
				return "";
			}

			Authentication request = new UsernamePasswordAuthenticationToken(cpf, senha.trim());
			Authentication result = am.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);

			if (tipoUsuario.equals('U')) {
				mostrarDataValidade = true;
				this.usuario = getUsuarioLogado();
				if (this.usuario == null) {
					throw new LoginException();
				}
			} else {
				this.setModel(getRepresentanteLogando());
				if (this.getModel() == null) {
					throw new LoginException();
				}
			}

			getSessionMap().put("captchaValido", true);

			qtdTentativa = 0;
		} catch (DisabledException e) {
			FacesMessagesUtil.addErrorMessage("Login ", " Usuário com data de validade expirada.");
			return "";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			tratarExcecaoAuthenticationException();
		} catch (LoginException l) {
			return apresentarMensagemLoginErro();
		} catch (Exception e) {
			ExcecaoUtil.tratarExcecao(e);
		}

		if (tipoUsuario.equals('R') && getModel().getFlgPrimeiroAcesso() != null && getModel().getFlgPrimeiroAcesso()) {
			return redirect("/pages/trocarSenha.jsf");
		} else {
			return redirect("/pages/principal.jsf");
		}
	}

	private String tratarExcecaoAuthenticationException() {
		if (tipoUsuario.equals('R')) {
			Representante representante;
			try {
				representante = getRepresentanteLogando();
				if (representante == null) {
					return apresentarMensagemLoginErro();
				}
				if (!representante.getSenha().equals(getModel().getSenha())) {
					return apresentarMensagemLoginErro();
				}
				if (qtdTentativa > 2) {
					return redirect("/captchaLogin.jsf");
				}
			} catch (DisabledException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			Usuario usuario = getUsuario();
			if (usuario == null) {
				return apresentarMensagemLoginErro();
			}
			if (!usuario.getSenha().equals(usuario.getSenha())) {
				return apresentarMensagemLoginErro();
			}
			if (qtdTentativa > 2) {
				return redirect("/captchaLogin.jsf");
			}
		}
		return null;
	}

	private String apresentarMensagemLoginErro() {
		FacesMessagesUtil.addErrorMessage("Login ", " Usuário ou senha inválidos");
		return "";
	}

	private Usuario getUsuarioLogado() throws Exception {
		Usuario usuario = new Usuario();
		usuario = loginService.buscarUsuario(cpf, tipoUsuario);
		if (usuario == null) {
			return null;
		} else {
			return usuario;
		}
	}

	private void regenarCaptcha() {

		num1 = (int) (Math.random() * 10);
		num2 = (int) (Math.random() * 10);
		num3 = (int) (Math.random() * 10);

		while (num1 == num2 && num1 == num3 && num2 == num3) {
			num1 = (int) (Math.random() * 10);
			num2 = (int) (Math.random() * 10);
			num3 = (int) (Math.random() * 10);
		}

		pergunta = num1 + "+" + num2 + "+" + num3 + "=";
	}

	public String cadastrarPrimeiraVez() {
		return redirect("/pages/primeiroAcesso.jsf");
	}

	public String validarCaptcha() {

		if (resposta == null || resposta.isEmpty()) {
			FacesMessagesUtil.addErrorMessage(" ", " Resposta da equação está errada.");
			return "";
		}
		Integer soma = num1 + num2 + num3;
		Integer respostaInt = new Integer(ValidacaoUtil.somenteNumero(resposta));
		if (soma != respostaInt.intValue()) {
			regenarCaptcha();
			FacesMessagesUtil.addErrorMessage(" ", " Resposta da equação está errada.");
			return "";
		} else {
			qtdTentativa = 0;
			return redirect("login.jsf");
		}
	}

	public String verificarCaptcha() {
		try {
			if (qtdTentativa < 3) {
				return redirect("login.jsf");

			} else {
				regenarCaptcha();
				FacesContext.getCurrentInstance().getExternalContext().redirect("captchaLogin.jsf");
			}
		} catch (Exception e) {
			ExcecaoUtil.tratarExcecao(e);
		}

		return SUCCESS;

	}

	public Representante getRepresentanteLogando() throws Exception, DisabledException {
		Representante representante = new Representante();
		representante = loginService.buscarRepresentante(cpf, tipoUsuario);

		if (representante == null) {
			return null;
		} else {
			if (dataValidadeExpirou(representante)) {
				throw new DisabledException("Usuário com data de validade expirada.");
			}
			return representante;
		}
	}

	private boolean dataValidadeExpirou(Representante representante) {
		if (representante.getDtValidade().before(new Date())){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public String saveDadosAlterarSenha() {
		try {
			boolean temErroValidacao = false;

			if (userName == null || userName.trim().equals("")) {
				FacesMessagesUtil.addErrorMessage("Usuário ", Constantes.CAMPO_OBRIGATORIO);
				temErroValidacao = true;
			}

			if (senhaAnterior == null || senhaAnterior.trim().equals("")) {
				FacesMessagesUtil.addErrorMessage("Senha Atual ", Constantes.CAMPO_OBRIGATORIO);
				temErroValidacao = true;

			}
			if (senha == null || senha.trim().equals("")) {
				FacesMessagesUtil.addErrorMessage("Nova Senha ", Constantes.CAMPO_OBRIGATORIO);
				temErroValidacao = true;

			}
			if (senha.length() < 6) {
				FacesMessagesUtil.addErrorMessage("Nova Senha ", " Deve ter mais de 6 caracteres ");
				temErroValidacao = true;
			}
			if (senha2 == null || senha2.trim().equals("")) {
				FacesMessagesUtil.addErrorMessage("Confirmação ", Constantes.CAMPO_OBRIGATORIO);
				temErroValidacao = true;
			}

			if (temErroValidacao) {
				return "";
			}

			Representante user = new Representante();
			user.setCpf(userName);
			List<Representante> lista = universalManager.listBy(user, false);

			if (lista == null || lista.isEmpty()) {
				FacesMessagesUtil.addErrorMessage("Login ", "Não Cadastrado");
				return "";
			}

			user = lista.get(0);
			if (!user.getSenha().equalsIgnoreCase(CriptoUtil.getCriptografia(senhaAnterior))) {
				FacesMessagesUtil.addErrorMessage("", Constantes.SENHA_ATUAL_NAO_CONFERE);
				temErroValidacao = true;
			}
			if (!senha.equals(senha2)) {
				FacesMessagesUtil.addErrorMessage("", Constantes.AMBAS_SENHAS_IDENTICAS);
				temErroValidacao = true;
			}

			if (temErroValidacao) {
				return "";
			}

			user.setSenha(CriptoUtil.getCriptografia(senha));
			Representante userClone = (Representante) user.clone();
			this.universalManager.save(userClone);
			limpar();
			FacesMessagesUtil.addInfoMessage("Senha", "Alterada " + ConstantesARQ.COM_SUCESSO);
		} catch (Exception e) {
			FacesMessagesUtil.addErrorMessage(this.getQualifiedName(), ConstantesARQ.ERRO_SALVAR);
			ExcecaoUtil.tratarExcecao(e);
			return ERROR;
		}

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String sendMail() {
		try {
			boolean temErroValidacao = false;

			if (userName == null || userName.equals("")) {
				FacesMessagesUtil.addErrorMessage("Login ", Constantes.CAMPO_OBRIGATORIO);
				temErroValidacao = true;
			}

			if (temErroValidacao) {
				return "";
			}

			Representante user = new Representante();
			user.setCpf(userName.trim());

			List<Representante> lista = universalManager.listBy(user, false);

			if (lista == null || lista.isEmpty()) {
				FacesMessagesUtil.addErrorMessage(" ", "Dados não conferem");
				return "";
			} else {
				user = lista.get(0);

				String passwordNv = PasswordUtil.gerarPassword(6);
				user.setSenha(CriptoUtil.getCriptografia(passwordNv));

				SimpleMailMessage message = new SimpleMailMessage();

				message.setFrom(Constantes.EMAIL_SISTEMA);
				message.setTo(user.getEmail());

				message.setSubject("Sistema SICRC - Informação sobre senha do usuário " + user.getEmail());
				message.setText("A sua nova senha é:  " + passwordNv + "\n\n\n\n");

				Representante userClone = (Representante) user.clone();
				// userClone.setFlgPrimeiroAcesso(true);
				loginService.esqueciSenha(userClone, message);
				FacesMessagesUtil.addInfoMessage("E-mail Enviado " + ConstantesARQ.COM_SUCESSO);
			}
			limpar();
		} catch (Exception e) {
			FacesMessagesUtil.addErrorMessage("Login ", "Usuário Inválido");
			ExcecaoUtil.tratarExcecao(e);
		}
		return "success";
	}

	public void limpar() {
		this.senha = "";
		this.senha2 = "";
		this.senhaAnterior = "";
		this.userName = "";
	}

	public void limparForm() {
		createModel();
	}

	public void loginTimeOut() {
		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.invalidate();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mostrarMsgSession", true);
			FacesContext.getCurrentInstance().getExternalContext().redirect("/" + Constantes.CONTEXT_PATH + "/login.jsf");
		} catch (Exception e) {
			ExcecaoUtil.tratarExcecao(e);
		}

	}

	@Override
	public String getQualifiedName() {
		return "Usuário";
	}

	@Override
	public boolean isFeminino() {
		return false;
	}

	public AuthenticationManager getAm() {
		return am;
	}

	public void setAm(AuthenticationManager am) {
		this.am = am;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSenhaAnterior() {
		return senhaAnterior;
	}

	public void setSenhaAnterior(String senhaAnterior) {
		this.senhaAnterior = senhaAnterior;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@Override
	public Representante createModel() {
		return new Representante();
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getQtdTentativa() {
		return qtdTentativa;
	}

	public void setQtdTentativa(Integer qtdTentativa) {
		this.qtdTentativa = qtdTentativa;
	}

	public boolean isMostrarMsgInvalidate() {
		return mostrarMsgInvalidate;
	}

	public void setMostrarMsgInvalidate(boolean mostrarMsgInvalidate) {
		this.mostrarMsgInvalidate = mostrarMsgInvalidate;
	}

	public Integer getNum1() {
		return num1;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	public Integer getNum2() {
		return num2;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

	public Integer getNum3() {
		return num3;
	}

	public void setNum3(Integer num3) {
		this.num3 = num3;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Character getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Character tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public boolean isMostrarDataValidade() {
		return mostrarDataValidade;
	}

	public void setMostrarDataValidade(boolean mostrarDataValidade) {
		this.mostrarDataValidade = mostrarDataValidade;
	}

	public String getCaminhoImagemPnef() {
		return caminhoImagemPnef;
	}

	public void setCaminhoImagemPnef(String caminhoImagemPnef) {
		this.caminhoImagemPnef = caminhoImagemPnef;
	}
}
