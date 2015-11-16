package br.com.sicrc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sicrc.DAO.ComissaoTematicaDAO;
import br.com.sicrc.entidade.ComissaoTematica;
import br.com.sicrc.service.ComissaoTematicaService;

@Service(value = "comissaoTematicaService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ComissaoTematicaServiceImpl implements ComissaoTematicaService {

    @Autowired(required = true)
    @Qualifier(value = "comissaoTematicaDAO")
    protected ComissaoTematicaDAO comissaoTematicaDAO;

    public ComissaoTematicaDAO getComissaoTematicaDAO() {
        return comissaoTematicaDAO;
    }

    public void setComissaoTematicaDAO(ComissaoTematicaDAO comissaoTematicaDAO) {
        this.comissaoTematicaDAO = comissaoTematicaDAO;
    }

    @Override
    public Integer recuperarUltimaComissaoTematica(Integer idRepresentante) throws Exception {
        return comissaoTematicaDAO.recuperarUltimaComissaoTematica(idRepresentante);
    }

    @Override
    public ComissaoTematica carregaComissaoTematicaDaUltimaComissaoTematica(Integer idRepresentante) throws Exception {
        return comissaoTematicaDAO.carregaComissaoTematicaDaUltimaComissaoTematica(idRepresentante);
    }

}
