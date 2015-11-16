package br.com.sicrc.DAO.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import br.com.arquitetura.util.SpringUtil;
import br.com.sicrc.DAO.EsferaGovernoDAO;
import br.com.sicrc.entidade.EsferaGoverno;

@Repository(value = "esferaGovernoDAO")
public class EsferaGovernoDAOImpl extends HibernateDaoSupport implements EsferaGovernoDAO {

    @Autowired(required = true)
    public void setFactory(SessionFactory factory) {
        super.setSessionFactory((SessionFactory) SpringUtil.getBean("sessionFactorySisfie", "applicationContext-hibernate.xml"));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EsferaGoverno> getAll() throws Exception {
        List<br.com.sicrc.entidadeexterna.sisfie.EsferaGoverno> retorno = null;
        if (retorno == null) {
            retorno = getSession().createQuery("FROM EsferaGoverno eg order by eg.descricao").list();
        }
        return trataLista(retorno);
    }

    private List<EsferaGoverno> trataLista(List<br.com.sicrc.entidadeexterna.sisfie.EsferaGoverno> retorno) {
        List<EsferaGoverno> listaFinal = null;
        if (retorno != null) {
            listaFinal = new ArrayList<EsferaGoverno>();
            for (br.com.sicrc.entidadeexterna.sisfie.EsferaGoverno esferaSisfie : retorno) {
                EsferaGoverno esferaSicrc = new EsferaGoverno(esferaSisfie.getId());
                esferaSicrc.setDescricao(esferaSisfie.getDescricao());
                listaFinal.add(esferaSicrc);
            }
        }
        return listaFinal;
    }

}