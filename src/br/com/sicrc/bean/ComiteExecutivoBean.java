package br.com.sicrc.bean;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.arquitetura.bean.PaginableBean;
import br.com.sicrc.entidade.ComiteExecutivo;

@ManagedBean
@ViewScoped
public class ComiteExecutivoBean extends PaginableBean<ComiteExecutivo> {

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
    public ComiteExecutivo createModel() {
        return new ComiteExecutivo();
    }

    @Override
    public String getQualifiedName() {
        return "Comite Executivo";
    }

    @Override
    public boolean isFeminino() {
        return false;
    }

}
