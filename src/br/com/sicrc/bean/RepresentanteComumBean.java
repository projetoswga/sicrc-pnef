package br.com.sicrc.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.arquitetura.bean.PaginableBean;
import br.com.arquitetura.excecao.ExcecaoUtil;
import br.com.sicrc.entidade.CargoAutoridade;
import br.com.sicrc.entidade.ComissaoTematica;
import br.com.sicrc.entidade.ComiteExecutivo;
import br.com.sicrc.entidade.EsferaGoverno;
import br.com.sicrc.entidade.Municipio;
import br.com.sicrc.entidade.Orgao;
import br.com.sicrc.entidade.Representante;
import br.com.sicrc.entidade.Uf;
import br.com.sicrc.service.EsferaGovernoService;
import br.com.sicrc.service.OrgaoService;
import br.com.sicrc.service.RepresentanteService;

public abstract class RepresentanteComumBean extends PaginableBean<Representante> {

    private static final long serialVersionUID = -2555352483151953808L;

    @ManagedProperty(value = "#{esferaGovernoService}")
    protected EsferaGovernoService esferaGovernoService;

    @ManagedProperty(value = "#{representanteService}")
    protected RepresentanteService representanteService;

    @ManagedProperty(value = "#{orgaoService}")
    protected OrgaoService orgaoService;

    @ManagedProperty(value = "#{loginBean}")
    protected LoginBean loginBean;

    @ManagedProperty(value = "#{parametroBean}")
    protected ParametroBean parametroBean;

    private List<Uf> ufs;
    private Boolean habilitaOrgao = Boolean.TRUE;
    private List<EsferaGoverno> esferaGovernos;
    private List<CargoAutoridade> cargoAutoridades;
    private List<ComissaoTematica> comissaoTematicas;
    private List<ComiteExecutivo> comiteExecutivos;
    private Orgao orgaoSelecionado;
    private Integer idMunicipio;
    private List<Municipio> municipios;
    private Integer idEsferaGoverno;
    private Integer idCargoAutoridade;
    private Integer idUf;
    private Integer idComissaoTematica;
    private Integer idComiteExecutivo;
    private boolean solicitarPesquisa;
    private boolean mostrarFlgRepSupComite;
    private boolean mostrarCargos;
    private boolean listaRepresentantesVazia;

    public RepresentanteComumBean() {
        ufs = new ArrayList<Uf>();
        hoje = new Date();
        esferaGovernos = new ArrayList<EsferaGoverno>();
        cargoAutoridades = new ArrayList<CargoAutoridade>();
        comissaoTematicas = new ArrayList<ComissaoTematica>();
        comiteExecutivos = new ArrayList<ComiteExecutivo>();
        orgaoSelecionado = new Orgao();
        municipios = new ArrayList<Municipio>();
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void carregarTela() {
        try {
            if (ufs.isEmpty()) {
                ufs = universalManager.getAll(Uf.class, "sigla");
            }
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }

        try {
            if (esferaGovernos.isEmpty()) {
                esferaGovernos = esferaGovernoService.getAll();
            }
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }

        try {
            if (cargoAutoridades.isEmpty()) {
                cargoAutoridades = universalManager.getAll(CargoAutoridade.class, "descricao");
            }
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }

        try {
            if (comissaoTematicas.isEmpty()) {
                comissaoTematicas = universalManager.getAll(ComissaoTematica.class, "descricao");
            }
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }

        try {
            if (comiteExecutivos.isEmpty()) {
                comiteExecutivos = universalManager.getAll(ComiteExecutivo.class, "descricao");
            }
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
    }

    public void pesquisar() throws Exception {
        if (getOrgaoSelecionado() != null && getOrgaoSelecionado().getId() != null) {
            getModel().setOrgao(new Orgao(getOrgaoSelecionado().getId()));
            getModel().getOrgao().setEsferaGoverno(new EsferaGoverno(getIdEsferaGoverno()));
        }
        Municipio municipio = new Municipio(getIdMunicipio());
        municipio.setUf(new Uf(getIdUf()));
        if (getIdMunicipio() != null || getIdUf() != null) {
            getModel().setMunicipio(municipio);
        }
        getModel().setCargoAutoridade(new CargoAutoridade(getIdCargoAutoridade()));
        getModel().setComissaoTematica(new ComissaoTematica(getIdComissaoTematica()));
        getModel().setComiteExecutivo(new ComiteExecutivo(getIdComiteExecutivo()));

        setSolicitarPesquisa(Boolean.TRUE);

        getLazyDataModel();
    }

    @Override
    public LazyDataModel<Representante> getLazyDataModel() {
        if (isSolicitarPesquisa()) {

            lazyDataModel = new LazyDataModel<Representante>() {

                private static final long serialVersionUID = -3128388997477577261L;

                @SuppressWarnings("rawtypes")
                @Override
                public List<Representante> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                    // Reduzindo o contador depois de filtrado
                    try {
                        setRowCount(representanteService.countRepresentante(getModel()).intValue());
                        lazyDataModel.setRowCount(getRowCount());
                        return representanteService.paginateRepresentante(first, pageSize, getModel());
                    } catch (Exception e) {
                        ExcecaoUtil.tratarExcecao(e);
                    }
                    return null;
                }
            };
        }
        setSolicitarPesquisa(Boolean.FALSE);

        listaRepresentantesVazia = lazyDataModel == null || lazyDataModel.getRowCount() == 0;
        return lazyDataModel;
    }

    public void changeUF(AjaxBehaviorEvent event) {
        carregarMunicipios(idUf);
    }

    @SuppressWarnings("unchecked")
    protected void carregarMunicipios(Integer idUf) {
        Municipio municipio = new Municipio();
        municipio.setUf(new Uf(idUf));
        try {
            municipios = universalManager.listBy(municipio);
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
    }

    public List<Orgao> completeOrgao(String query) {
        List<Orgao> sugestoes = new ArrayList<Orgao>();
        try {
            if (idEsferaGoverno != null && idEsferaGoverno != 0)
                sugestoes = orgaoService.pesquisarOrgao(query, idEsferaGoverno);
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
        return sugestoes;
    }

    public void limparCampoOrgao(AjaxBehaviorEvent event) {
    	habilitaOrgao = Boolean.FALSE;
        setOrgaoSelecionado(new Orgao());
        mostrarCamposRegioes();
    }

    public void mostrarCamposRegioes() {
        if (idEsferaGoverno == EsferaGoverno.FEDERAL) {
            mostrarFlgRepSupComite = true;
        } else {
            mostrarFlgRepSupComite = false;
        }
    }

    public void mostrarCampoCargo(AjaxBehaviorEvent event) {
        if (getModel().getFlgRepSupComite()) {
            mostrarCargos = true;
        } else {
            mostrarCargos = false;
            getModel().setTpTipoSuplente(null);
        }
    }

    @Override
    public Representante createModel() {
        return new Representante();
    }

    @Override
    public String getQualifiedName() {
        return "Representante";
    }

    @Override
    public boolean isFeminino() {
        return false;
    }

    @Override
    public Map<String, String> getFilters() {
        return null;
    }

    @Override
    public String getSortField() {
        return "nome";
    }

    public EsferaGovernoService getEsferaGovernoService() {
        return esferaGovernoService;
    }

    public void setEsferaGovernoService(EsferaGovernoService esferaGovernoService) {
        this.esferaGovernoService = esferaGovernoService;
    }

    public List<Uf> getUfs() {
        return ufs;
    }

    public void setUfs(List<Uf> ufs) {
        this.ufs = ufs;
    }

    public List<EsferaGoverno> getEsferaGovernos() {
        return esferaGovernos;
    }

    public void setEsferaGovernos(List<EsferaGoverno> esferaGovernos) {
        this.esferaGovernos = esferaGovernos;
    }

    public List<CargoAutoridade> getCargoAutoridades() {
        return cargoAutoridades;
    }

    public void setCargoAutoridades(List<CargoAutoridade> cargoAutoridades) {
        this.cargoAutoridades = cargoAutoridades;
    }

    public List<ComissaoTematica> getComissaoTematicas() {
        return comissaoTematicas;
    }

    public void setComissaoTematicas(List<ComissaoTematica> comissaoTematicas) {
        this.comissaoTematicas = comissaoTematicas;
    }

    public List<ComiteExecutivo> getComiteExecutivos() {
        return comiteExecutivos;
    }

    public void setComiteExecutivos(List<ComiteExecutivo> comiteExecutivos) {
        this.comiteExecutivos = comiteExecutivos;
    }

    public Orgao getOrgaoSelecionado() {
        return orgaoSelecionado;
    }

    public void setOrgaoSelecionado(Orgao orgaoSelecionado) {
        this.orgaoSelecionado = orgaoSelecionado;
    }

    public OrgaoService getOrgaoService() {
        return orgaoService;
    }

    public void setOrgaoService(OrgaoService orgaoService) {
        this.orgaoService = orgaoService;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public Integer getIdEsferaGoverno() {
        return idEsferaGoverno;
    }

    public void setIdEsferaGoverno(Integer idEsferaGoverno) {
        this.idEsferaGoverno = idEsferaGoverno;
    }

    public Integer getIdCargoAutoridade() {
        return idCargoAutoridade;
    }

    public void setIdCargoAutoridade(Integer idCargoAutoridade) {
        this.idCargoAutoridade = idCargoAutoridade;
    }

    public Integer getIdUf() {
        return idUf;
    }

    public void setIdUf(Integer idUf) {
        this.idUf = idUf;
    }

    public Integer getIdComissaoTematica() {
        return idComissaoTematica;
    }

    public void setIdComissaoTematica(Integer idComissaoTematica) {
        this.idComissaoTematica = idComissaoTematica;
    }

    public Integer getIdComiteExecutivo() {
        return idComiteExecutivo;
    }

    public void setIdComiteExecutivo(Integer idComiteExecutivo) {
        this.idComiteExecutivo = idComiteExecutivo;
    }

    public boolean isSolicitarPesquisa() {
        return solicitarPesquisa;
    }

    public void setSolicitarPesquisa(boolean solicitarPesquisa) {
        this.solicitarPesquisa = solicitarPesquisa;
    }

    public boolean isMostrarFlgRepSupComite() {
        return mostrarFlgRepSupComite;
    }

    public void setMostrarFlgRepSupComite(boolean mostrarFlgRepSupComite) {
        this.mostrarFlgRepSupComite = mostrarFlgRepSupComite;
    }

    public boolean isMostrarCargos() {
        return mostrarCargos;
    }

    public void setMostrarCargos(boolean mostrarCargos) {
        this.mostrarCargos = mostrarCargos;
    }

    public RepresentanteService getRepresentanteService() {
        return representanteService;
    }

    public void setRepresentanteService(RepresentanteService representanteService) {
        this.representanteService = representanteService;
    }

    public Boolean getListaRepresentantesVazia() {
        return listaRepresentantesVazia;
    }

    public void setListaRepresentantesVazia(boolean listaRepresentantesVazia) {
        this.listaRepresentantesVazia = listaRepresentantesVazia;
    }

    public ParametroBean getParametroBean() {
        return parametroBean;
    }

    public void setParametroBean(ParametroBean parametroBean) {
        this.parametroBean = parametroBean;
    }

	public Boolean getHabilitaOrgao() {
		return habilitaOrgao;
	}

	public void setHabilitaOrgao(Boolean habilitaOrgao) {
		this.habilitaOrgao = habilitaOrgao;
	}
 
}
