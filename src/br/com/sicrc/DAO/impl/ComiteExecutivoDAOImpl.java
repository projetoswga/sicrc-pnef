package br.com.sicrc.DAO.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import br.com.sicrc.DAO.ComiteExecutivoDAO;
import br.com.sicrc.entidade.ComiteExecutivo;
import br.com.sicrc.entidade.RepresentanteComite;

@Repository(value = "comiteExecutivoDAO")
public class ComiteExecutivoDAOImpl extends HibernateDaoSupport implements ComiteExecutivoDAO {

    @Autowired(required = true)
    public void setFactory(SessionFactory factory) {
        super.setSessionFactory(factory);
    }

    @Override
    public Integer recuperarUltimoComiteExecutivo(Integer idRepresentante) throws Exception {
        Criteria criteria = getSession().createCriteria(RepresentanteComite.class);
        criteria.createAlias("representante", "r");
        criteria.add(Restrictions.eq("r.id", idRepresentante));
        criteria.setProjection(Projections.max("id"));
        Integer idRepresentanteComite = (Integer) criteria.uniqueResult();

        if (idRepresentanteComite != null && idRepresentanteComite != 0) {
            Criteria criteriaComissao = getSession().createCriteria(RepresentanteComite.class);
            criteriaComissao.add(Restrictions.idEq(idRepresentanteComite));
            return ((RepresentanteComite) criteriaComissao.uniqueResult()).getComiteExecutivo().getId();
        } else {
            return null;
        }
    }

    @Override
    public ComiteExecutivo carregaComiteExecutivoDoUltimoComiteExecutivo(Integer idRepresentante) throws Exception {
        Criteria criteria = getSession().createCriteria(RepresentanteComite.class);
        criteria.createAlias("representante", "r");
        criteria.add(Restrictions.eq("r.id", idRepresentante));
        criteria.setProjection(Projections.max("id"));
        Integer idRepresentanteComite = (Integer) criteria.uniqueResult();

        if (idRepresentanteComite != null && idRepresentanteComite != 0) {
            Criteria criteriaComissao = getSession().createCriteria(RepresentanteComite.class);
            criteriaComissao.add(Restrictions.idEq(idRepresentanteComite));
            return ((RepresentanteComite) criteriaComissao.uniqueResult()).getComiteExecutivo();
        } else {
            return null;
        }
    }
}