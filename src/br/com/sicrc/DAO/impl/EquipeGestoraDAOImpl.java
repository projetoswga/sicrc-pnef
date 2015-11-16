package br.com.sicrc.DAO.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import br.com.arquitetura.DAO.UniversalDAO;
import br.com.sicrc.DAO.EquipeGestoraDAO;
import br.com.sicrc.entidade.Usuario;

@Repository(value = "equipeGestoraDAO")
public class EquipeGestoraDAOImpl extends HibernateDaoSupport implements EquipeGestoraDAO {

    @Autowired(required = true)
    public void setFactory(SessionFactory factory) {
        super.setSessionFactory(factory);
    }

    @Autowired(required = true)
    @Qualifier(value = "universalDAO")
    protected UniversalDAO dao;

    @SuppressWarnings("unchecked")
    @Override
    public List<Usuario> findAll() {
        String sql = "from Usuario u where u.flgGeref is true";
        return dao.getCurrentSession().createQuery(sql).list();
    }
}
