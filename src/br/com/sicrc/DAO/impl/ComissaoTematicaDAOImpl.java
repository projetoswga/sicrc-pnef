package br.com.sicrc.DAO.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import br.com.sicrc.DAO.ComissaoTematicaDAO;
import br.com.sicrc.entidade.ComissaoRepresentante;
import br.com.sicrc.entidade.ComissaoTematica;

@Repository(value = "comissaoTematicaDAO")
public class ComissaoTematicaDAOImpl extends HibernateDaoSupport implements ComissaoTematicaDAO {

    @Autowired(required = true)
    public void setFactory(SessionFactory factory) {
        super.setSessionFactory(factory);
    }

    @Override
    public Integer recuperarUltimaComissaoTematica(Integer idRepresentante) throws Exception {
        Criteria criteria = getSession().createCriteria(ComissaoRepresentante.class);
        criteria.createAlias("representante", "r");
        criteria.add(Restrictions.eq("r.id", idRepresentante));
        criteria.setProjection(Projections.max("id"));
        Integer idComissaoRepresentante = (Integer) criteria.uniqueResult();

        if (idComissaoRepresentante != null && idComissaoRepresentante != 0) {
            Criteria criteriaComissao = getSession().createCriteria(ComissaoRepresentante.class);
            criteriaComissao.add(Restrictions.idEq(idComissaoRepresentante));
            return ((ComissaoRepresentante) criteriaComissao.uniqueResult()).getComissaoTematica().getId();
        } else {
            return null;
        }
    }

    @Override
    public ComissaoTematica carregaComissaoTematicaDaUltimaComissaoTematica(Integer idRepresentante) throws Exception {
        Criteria criteria = getSession().createCriteria(ComissaoRepresentante.class);
        criteria.createAlias("representante", "r");
        criteria.add(Restrictions.eq("r.id", idRepresentante));
        criteria.setProjection(Projections.max("id"));
        Integer idComissaoRepresentante = (Integer) criteria.uniqueResult();

        if (idComissaoRepresentante != null && idComissaoRepresentante != 0) {
            Criteria criteriaComissao = getSession().createCriteria(ComissaoRepresentante.class);
            criteriaComissao.add(Restrictions.idEq(idComissaoRepresentante));
            return ((ComissaoRepresentante) criteriaComissao.uniqueResult()).getComissaoTematica();
        } else {
            return null;
        }
    }
}
