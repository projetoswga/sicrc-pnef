package br.com.sicrc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sicrc.DAO.OrgaoDAO;
import br.com.sicrc.entidade.Orgao;
import br.com.sicrc.service.OrgaoService;

@Service(value = "orgaoService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class OrgaoServiceImpl implements OrgaoService {

    @Autowired(required = true)
    @Qualifier(value = "orgaoDAO")
    protected OrgaoDAO orgaoDAO;

    @Override
    public List<Orgao> pesquisarOrgao(String query, Integer idEsferaGoverno) throws Exception {
        return orgaoDAO.pesquisarOrgao(query, idEsferaGoverno);
    }

    public OrgaoDAO getOrgaoDAO() {
        return orgaoDAO;
    }

    public void setOrgaoDAO(OrgaoDAO orgaoDAO) {
        this.orgaoDAO = orgaoDAO;
    }
}
