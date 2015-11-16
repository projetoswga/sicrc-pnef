package br.com.sicrc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.arquitetura.DAO.UniversalDAO;
import br.com.sicrc.DAO.RepresentanteDAO;
import br.com.sicrc.DAO.UsuarioDAO;
import br.com.sicrc.entidade.Representante;
import br.com.sicrc.entidade.Usuario;
import br.com.sicrc.service.LoginService;

@Service(value = "loginService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class LoginServiceImpl implements LoginService {

	@Autowired(required = true)
	@Qualifier(value = "mailSender")
	private JavaMailSender mailSender;

	@Autowired(required = true)
	@Qualifier(value = "universalDAO")
	protected UniversalDAO dao;
	
	@Autowired(required = true)
	@Qualifier(value = "representanteDAO")
	protected RepresentanteDAO representanteDAO;
	
	@Autowired(required = true)
	@Qualifier(value = "usuarioDAO")
	protected UsuarioDAO usuarioDAO;

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void esqueciSenha(Representante user, SimpleMailMessage message) throws Exception {
		dao.save(user);
		mailSender.send(message);
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public UniversalDAO getDao() {
		return dao;
	}

	public void setDao(UniversalDAO dao) {
		this.dao = dao;
	}

	@Override
	public Representante buscarRepresentante(String login, Character tipoUsuario) throws Exception {
		return representanteDAO.buscarRepresentante(login, tipoUsuario);
	}

	@Override
	public Usuario buscarUsuario(String login, Character tipoUsuario) throws Exception {
		return usuarioDAO.buscarUsuario(login,tipoUsuario);
	}
}
