package br.com.sicrc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sicrc.DAO.ComiteExecutivoDAO;
import br.com.sicrc.entidade.ComiteExecutivo;
import br.com.sicrc.service.ComiteExecutivoService;

@Service(value = "comiteExecutivoService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ComiteExecutivoServiceImpl implements ComiteExecutivoService {

    @Autowired(required = true)
    @Qualifier(value = "comiteExecutivoDAO")
    protected ComiteExecutivoDAO comiteExecutivoDAO;

    public ComiteExecutivoDAO getComiteExecutivoDAO() {
        return comiteExecutivoDAO;
    }

    public void setComiteExecutivoDAO(ComiteExecutivoDAO comiteExecutivoDAO) {
        this.comiteExecutivoDAO = comiteExecutivoDAO;
    }

    @Override
    public Integer recuperarUltimoComiteExecutivo(Integer idRepresentante) throws Exception {
        return comiteExecutivoDAO.recuperarUltimoComiteExecutivo(idRepresentante);
    }

    @Override
    public ComiteExecutivo carregaComiteExecutivoDoUltimoComiteExecutivo(Integer idRepresentante) throws Exception {
        return comiteExecutivoDAO.carregaComiteExecutivoDoUltimoComiteExecutivo(idRepresentante);
    }
}
