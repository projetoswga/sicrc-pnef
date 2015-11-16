package br.com.sicrc.DAO.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import br.com.arquitetura.DAO.UniversalDAO;
import br.com.sicrc.DAO.RepresentanteDAO;
import br.com.sicrc.entidade.Representante;
import br.com.sicrc.entidade.RepresentanteArquivo;

@Repository(value = "representanteDAO")
public class RepresentanteDAOImpl extends HibernateDaoSupport implements RepresentanteDAO {

    @Autowired(required = true)
    public void setFactory(SessionFactory factory) {
        super.setSessionFactory(factory);
    }

    @Autowired(required = true)
    @Qualifier(value = "universalDAO")
    protected UniversalDAO dao;

    @Override
    public Representante buscarRepresentante(String login, Character tipoUsuario) throws Exception {
        String sql = "from Representante c where c.cpf like'" + login + "' and c.tp_login like '" + tipoUsuario + "'";
        return (Representante) dao.getCurrentSession().createQuery(sql).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Representante> pesquisarRepresentante(String query) {
        if (query == null || query.isEmpty()) {
            return null;
        }
        String sql = "SELECT * FROM SICRC.representante WHERE UPPER(SICRC.sem_acento(nome)) ILIKE UPPER(SICRC.sem_acento('%" + query + "%')) order by nome asc;";
        return getSession().createSQLQuery(sql).addEntity(Representante.class).list();
    }

    @Override
    public Long countRepresentante(Representante model) {
        Criteria c = retornarCriteria(model);
        c.setProjection(Projections.rowCount());
        Long result = (Long) c.list().get(0);
        return result;
    }

    private Criteria retornarCriteria(Representante model) {
        if (model == null) {
            return null;
        }

        Criteria criteria = getSession().createCriteria(Representante.class);

        criteria.add(Restrictions.eq("flgAtivo", true));

        if (model.getNome() != null && !model.getNome().equals("")) {
            criteria.add(Restrictions.ilike("nome", model.getNome(), MatchMode.ANYWHERE));
        }
        if (model.getCpf() != null && !model.getCpf().equals("")) {
            criteria.add(Restrictions.ilike("cpf", model.getCpf().trim().replaceAll("[()-.]", ""), MatchMode.ANYWHERE));
        }
        if (model.getDtValidade() != null) {
            criteria.add(Restrictions.le("dtValidade", model.getDtValidade()));
        }
        if (model.getOrgao() != null) {
            criteria.createAlias("orgao", "o");
            if (model.getOrgao().getId() != null && model.getOrgao().getId() != 0) {
                criteria.add(Restrictions.eq("o.id", model.getOrgao().getId()));
            }
            if (model.getOrgao().getEsferaGoverno() != null && model.getOrgao().getEsferaGoverno().getId() != null && model.getOrgao().getEsferaGoverno().getId() != 0) {
                criteria.createAlias("o.esferaGoverno", "e");
                criteria.add(Restrictions.eq("e.id", model.getOrgao().getEsferaGoverno().getId()));
            }
        }
        if (model.getNomeAutoridade() != null && !model.getNomeAutoridade().equals("")) {
            criteria.add(Restrictions.ilike("nomeAutoridade", model.getNomeAutoridade(), MatchMode.ANYWHERE));
        }
        if (model.getLogradouroAutoridade() != null && !model.getLogradouroAutoridade().equals("")) {
            criteria.add(Restrictions.ilike("logradouroAutoridade", model.getLogradouroAutoridade(), MatchMode.ANYWHERE));
        }
        if (model.getCepAutoridade() != null && !model.getCepAutoridade().equals("")) {
            criteria.add(Restrictions.ilike("cepAutoridade", model.getCepAutoridade(), MatchMode.ANYWHERE));
        }
        if (model.getEmailAutoridade() != null && !model.getEmailAutoridade().equals("")) {
            criteria.add(Restrictions.ilike("emailAutoridade", model.getEmailAutoridade(), MatchMode.ANYWHERE));
        }
        if (model.getMunicipio() != null) {
            criteria.createAlias("municipio", "m");
            if (model.getMunicipio().getId() != null && model.getMunicipio().getId() != 0) {
                criteria.add(Restrictions.eq("m.id", model.getMunicipio().getId()));
            }
            if (model.getMunicipio().getUf() != null && model.getMunicipio().getUf().getId() != null && model.getMunicipio().getUf().getId() != 0) {
                criteria.createAlias("m.uf", "u");
                criteria.add(Restrictions.eq("u.id", model.getMunicipio().getUf().getId()));
            }
        }
        if (model.getCargoAutoridade() != null && model.getCargoAutoridade().getId() != null && model.getCargoAutoridade().getId() != 0) {
            criteria.createAlias("cargoAutoridade", "ca");
            criteria.add(Restrictions.eq("ca.id", model.getCargoAutoridade().getId()));
        }
        if (model.getFlgDef() != null && model.getFlgDef()) {
            criteria.add(Restrictions.eq("flgDef", true));
        }
        if (model.getFlgGef() != null && model.getFlgGef()) {
            criteria.add(Restrictions.eq("flgGef", true));
        }
        if (model.getFlgRepSupComite() != null && model.getFlgRepSupComite()) {
            criteria.add(Restrictions.eq("flgRepSupComite", true));
        }
        if (model.getTpTipoSuplente() != null) {
            criteria.add(Restrictions.eq("tpTipoSuplente", model.getTpTipoSuplente()));
        }
        if (model.getComissaoTematica() != null && model.getComissaoTematica().getId() != null && model.getComissaoTematica().getId() != 0) {
            criteria.createAlias("comissaoTematica", "ct");
            criteria.add(Restrictions.eq("ct.id", model.getComissaoTematica().getId()));
        }
        if (model.getComiteExecutivo() != null && model.getComiteExecutivo().getId() != null && model.getComiteExecutivo().getId() != 0) {
            criteria.createAlias("comiteExecutivo", "ce");
            criteria.add(Restrictions.eq("ce.id", model.getComiteExecutivo().getId()));
        }

        return criteria;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Representante> paginateRepresentante(int first, int pageSize, Representante model) {
        Criteria c = retornarCriteria(model);
        if (first != 0)
            c.setFirstResult(first);
        if (pageSize != 0)
            c.setMaxResults(pageSize);
        return c.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RepresentanteArquivo> obtemArquivos(Representante representante) {
        if (representante != null) {
            String sql = "from RepresentanteArquivo ra where ra.representante.id = " + representante.getId();
            return dao.getCurrentSession().createQuery(sql).list();
        }

        return null;

    }

    @Override
    public void alteraAtivacao(Representante representante) {
        if (representante != null) {
            String sql = "update Representante set flgAtivo = " + !representante.getFlgAtivo() + " where id = " + representante.getId();
            dao.getCurrentSession().createQuery(sql).executeUpdate();
        }
    }

    @Override
    public Date recuperaDatavalidadeCadastroAtual() {
        String sql = "select vr.dtValidade from ValidadeRepresentante vr where vr.id = (select max(vr2.id) from ValidadeRepresentante vr2)";
        return (Date) dao.getCurrentSession().createQuery(sql).uniqueResult();
    }
}
