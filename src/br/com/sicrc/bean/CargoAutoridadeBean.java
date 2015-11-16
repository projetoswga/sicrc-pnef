package br.com.sicrc.bean;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.arquitetura.bean.PaginableBean;
import br.com.sicrc.entidade.CargoAutoridade;

@ManagedBean
@ViewScoped
public class CargoAutoridadeBean extends PaginableBean<CargoAutoridade> {

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
    public CargoAutoridade createModel() {
        return new CargoAutoridade();
    }

    @Override
    public String getQualifiedName() {
        return "Cargo Autoridade";
    }

    @Override
    public boolean isFeminino() {
        return false;
    }

}
