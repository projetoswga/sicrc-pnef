package br.com.sicrc.DAO.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import br.com.arquitetura.DAO.UniversalDAO;
import br.com.sicrc.DAO.UsuarioDAO;
import br.com.sicrc.entidade.Usuario;

@Repository(value = "usuarioDAO")
public class UsuarioDAOImpl extends HibernateDaoSupport implements UsuarioDAO {

	@Autowired(required = true)
	public void setFactory(SessionFactory factory) {
		super.setSessionFactory(factory);
	}

	@Autowired(required = true)
	@Qualifier(value = "universalDAO")
	protected UniversalDAO dao;

	@Override
	public Usuario buscarUsuario(String login, Character tipoUsuario) throws Exception {
		String sql = "from Usuario u where u.cpf like'" + login + "' and u.tp_login like '" + tipoUsuario + "'";
		return (Usuario) dao.getCurrentSession().createQuery(sql).uniqueResult();
	}
}
