package br.com.sicrc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sicrc.DAO.EsferaGovernoDAO;
import br.com.sicrc.entidade.EsferaGoverno;
import br.com.sicrc.service.EsferaGovernoService;

@Service(value = "esferaGovernoService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class EsferaGovernoServiceImpl implements EsferaGovernoService {

    @Autowired(required = true)
    @Qualifier(value = "esferaGovernoDAO")
    protected EsferaGovernoDAO esferaGovernoDAO;

    @Override
    public List<EsferaGoverno> getAll() throws Exception {
        return esferaGovernoDAO.getAll();
    }

    public EsferaGovernoDAO getEsferaGovernoDAO() {
        return esferaGovernoDAO;
    }

    public void setEsferaGovernoDAO(EsferaGovernoDAO esferaGovernoDAO) {
        this.esferaGovernoDAO = esferaGovernoDAO;
    }

}
