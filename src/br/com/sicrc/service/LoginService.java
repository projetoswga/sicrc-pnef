package br.com.sicrc.service;

import org.springframework.mail.SimpleMailMessage;

import br.com.sicrc.entidade.Representante;
import br.com.sicrc.entidade.Usuario;

public interface LoginService {

	public void esqueciSenha(Representante user, SimpleMailMessage message) throws Exception;

	public Representante buscarRepresentante(String login, Character tipoUsuario) throws Exception;

	public Usuario buscarUsuario(String login, Character tipoUsuario) throws Exception;
}
