package br.com.arquitetura.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.excecao.ExcecaoUtil;
import br.com.arquitetura.relatorios.util.GeradorRelatorio;
import br.com.arquitetura.util.ConstantesARQ;

@SuppressWarnings("rawtypes")
public abstract class PaginableBean<T extends Entidade> extends BaseBean<T> {

    private static final long serialVersionUID = 6758303059773814977L;

    protected LazyDataModel<T> lazyDataModel;

    public Map<String, String> filters = new HashMap<String, String>();

    public abstract Map<String, String> getFilters();

    public abstract String getSortField();

    private boolean order = false;

    @SuppressWarnings("unchecked")
    public LazyDataModel<T> getLazyDataModel() {
        try {
            if (lazyDataModel == null)
                lazyDataModel = new LazyDataModel() {

                    private static final long serialVersionUID = 2595093445602418759L;

                    @Override
                    public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                        try {
                            order = false;

                            if (sortOrder.equals(SortOrder.ASCENDING)) {
                                order = true;
                            }

                            if (getFilters() != null && !getFilters().isEmpty()) {
                                filters.putAll(getFilters());
                            }

                            if (getSortField() != null && !getSortField().isEmpty()) {
                                sortField = getSortField();
                            }

                            // Reduzindo o contador depois de filtrado
                            int rowCount = universalManager.getCount(first, pageSize, sortField, order, filters, getModel().getClass()).intValue();
                            lazyDataModel.setRowCount(rowCount);
                            return universalManager.paginate(first, pageSize, sortField, order, filters, getModel().getClass());
                        } catch (Exception e) {
                            ExcecaoUtil.tratarExcecao(e);
                        }
                        return null;
                    }
                };

        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<T> lazyDataModel) {
        this.lazyDataModel = lazyDataModel;
    }

    @Override
    public String getPaginacao() {
        return ConstantesARQ.PAGINACAO;
    }

    @SuppressWarnings("unchecked")
    public void geraRelatorio(String tipo) {
        try {
            List<? extends Entidade> lista = null;
            if (lazyDataModel.getRowCount() > lazyDataModel.getPageSize()) {
                lista = lazyDataModel.load(0, lazyDataModel.getRowCount(), getSortField(), order ? SortOrder.ASCENDING : SortOrder.UNSORTED, getFilters());
            } else {
                lista = (List<? extends Entidade>) lazyDataModel.getWrappedData();
            }
            String caminhoImagem = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/design/imagem-default/logo-pnef-branca.png");
            GeradorRelatorio.gerarRelatorio(lista, tipo, caminhoImagem);
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
    }

    protected void executaJQuery(String comando) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(comando);
    }
}
