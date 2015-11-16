package br.com.sicrc.bean;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.arquitetura.bean.PaginableBean;
import br.com.sicrc.entidade.ComissaoTematica;

@ManagedBean
@ViewScoped
public class ComissaoTematicaBean extends PaginableBean<ComissaoTematica> {

    private static final long serialVersionUID = 1L;

    @Override
    public String load() {
        return super.load();
    }

    @Override
    public Map<String, String> getFilters() {
        return null;
    }

    @Override
    public String getSortField() {
        return "descricao";
    }

    @Override
    public ComissaoTematica createModel() {
        return new ComissaoTematica();
    }

    @Override
    public String getQualifiedName() {
        return "Comissão Temática";
    }

    @Override
    public boolean isFeminino() {
        return false;
    }

}
