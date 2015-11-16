package br.com.sicrc.bean;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.arquitetura.bean.PaginableBean;
import br.com.sicrc.entidade.Parametro;
import br.com.sicrc.service.ParametroService;

@ManagedBean(name = "parametroBean")
@ViewScoped
public class ParametroBean extends PaginableBean<Parametro> {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{parametroService}")
    protected ParametroService parametroService;

    public Parametro getParametro(Integer idParametro) {
        return parametroService.getParametro(idParametro);
    }

    @Override
    public Map<String, String> getFilters() {
        return null;
    }

    @Override
    public String getSortField() {
        return null;
    }

    @Override
    public Parametro createModel() {
        return null;
    }

    @Override
    public String getQualifiedName() {
        return null;
    }

    @Override
    public boolean isFeminino() {
        return false;
    }

    public ParametroService getParametroService() {
        return parametroService;
    }

    public void setParametroService(ParametroService parametroService) {
        this.parametroService = parametroService;
    }

}
