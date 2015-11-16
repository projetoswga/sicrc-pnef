package br.com.sicrc.DAO.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import br.com.sicrc.DAO.OrgaoDAO;
import br.com.sicrc.entidade.Orgao;

@Repository(value = "orgaoDAO")
public class OrgaoDAOImpl extends HibernateDaoSupport implements OrgaoDAO {

	@Autowired(required = true)
	public void setFactory(SessionFactory factory) {
		super.setSessionFactory(factory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Orgao> pesquisarOrgao(String query, Integer idEsferaGoverno) throws Exception {
		if (query == null || query.isEmpty()) {
			return null;
		}
		String sql = "SELECT * FROM SICRC.orgao WHERE UPPER(SICRC.sem_acento(nom_sigla)) ILIKE UPPER(SICRC.sem_acento('%" + query
				+ "%')) AND id_esfera_gov_orgao = " + idEsferaGoverno + " order by nom_sigla asc,nom_orgao asc;";
		return getSession().createSQLQuery(sql).addEntity(Orgao.class).list();
	}
}
