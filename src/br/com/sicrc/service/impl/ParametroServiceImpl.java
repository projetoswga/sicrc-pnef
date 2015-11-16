package br.com.sicrc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.arquitetura.DAO.UniversalDAO;
import br.com.sicrc.entidade.Parametro;
import br.com.sicrc.service.ParametroService;

@Service(value = "parametroService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ParametroServiceImpl implements ParametroService {

    @Autowired(required = true)
    @Qualifier(value = "universalDAO")
    protected UniversalDAO dao;

    @Override
    public Parametro getParametro(Integer idParametro) {
        return (Parametro) dao.get(Parametro.class, idParametro);
    }

    public UniversalDAO getDao() {
        return dao;
    }

    public void setDao(UniversalDAO dao) {
        this.dao = dao;
    }

}
