package br.com.sicrc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sicrc.DAO.EquipeGestoraDAO;
import br.com.sicrc.entidade.Usuario;
import br.com.sicrc.service.EquipeGestoraService;

@Service(value = "equipeGestoraService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class EquipeGestoraServiceImpl implements EquipeGestoraService {

    @Autowired(required = true)
    @Qualifier(value = "equipeGestoraDAO")
    protected EquipeGestoraDAO equipeGestoraDAO;

    @Override
    public List<Usuario> findAll() {
        return equipeGestoraDAO.findAll();
    }

}
