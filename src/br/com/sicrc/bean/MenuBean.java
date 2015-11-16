package br.com.sicrc.bean;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import br.com.sicrc.util.Constantes;

@ManagedBean(name = "menuBean")
@ViewScoped
public class MenuBean {

    private MenuModel menu;

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    public MenuBean() {
    }

    @PostConstruct
    public void carregarMenu() {
        menu = new DefaultMenuModel();

        DefaultSubMenu representante = new DefaultSubMenu(loginBean.getTipoUsuario().equals('U') ? Constantes.LABEL_SUBMENU_REPRESENTANTE : Constantes.LABEL_SUBMENU_INFORMACOES_CADASTRAIS);
        DefaultSubMenu apoio = new DefaultSubMenu(Constantes.LABEL_SUBMENU_APOIO);
        DefaultSubMenu sistema = new DefaultSubMenu(Constantes.LABEL_SUBMENU_SISTEMA);

        /**
         * Coloca o menu em ordem específica e verifica a permissão da pessoa logada.
         */
        menu.getElements().add(representante);
        menu.getElements().add(apoio);
        if (loginBean.getTipoUsuario().equals('U')) {
            representante.getElements().add(criarItemVisualizarRepresentante());
            representante.getElements().add(criarItemMalaDireta());

            apoio.getElements().add(criaItemCargoAutoridade());
            apoio.getElements().add(criaItemComissaoTematica());
            apoio.getElements().add(criaItemComiteExecutivo());
            apoio.getElements().add(criaItemDataValidade());

            /**
             * Usado somente em desenvolvimento para correção de sequence. sistema.getElements().add(criarItemSequence());
             */

            menu.getElements().add(criaMenuUsuario());
        } else {
            apoio.getElements().add(criaItemEquipeGeref());
            representante.getElements().add(criarItemEditarRepresentante());
        }
        sistema.getElements().add(criarItemSair());
        menu.getElements().add(sistema);
    }

    private MenuElement criarItemSair() {
        return criaItemMenuPadraoSemIcone(Constantes.ITEM_MENU_SAIR_ID, Constantes.ITEM_MENU_SAIR_LABEL, Constantes.ITEM_MENU_SAIR_URL);
    }

    @SuppressWarnings("unchecked")
    private MenuElement criarItemEditarRepresentante() {
        getSessionMap().put("idRepresentante", loginBean.getModel().getId());
        return criaItemMenuPadraoSemIcone(Constantes.ITEM_MENU_EDITAR_REPRESENTANTE_ID, Constantes.ITEM_MENU_EDITAR_REPRESENTANTE_LABEL, Constantes.ITEM_MENU_EDITAR_REPRESENTANTE_URL);
    }

    private MenuElement criarItemMalaDireta() {
        return criaItemMenuPadraoSemIcone(Constantes.ITEM_MENU_MALA_DIRETA_ID, Constantes.ITEM_MENU_MALA_DIRETA_LABEL, Constantes.ITEM_MENU_MALA_DIRETA_URL);
    }

    private MenuElement criarItemVisualizarRepresentante() {
        return criaItemMenuPadraoSemIcone(Constantes.ITEM_MENU_VISUALIZAR_REPRESENTANTE_ID, Constantes.ITEM_MENU_VISUALIZAR_REPRESENTANTE_LABEL, Constantes.ITEM_MENU_VISUALIZAR_REPRESENTANTE_URL);
    }

    private MenuElement criaMenuUsuario() {
        return criaItemMenuPadraoSemIcone(Constantes.ITEM_MENU_USUARIO_ID, Constantes.ITEM_MENU_USUARIO_LABEL, Constantes.ITEM_MENU_USUARIO_URL);
    }

    private MenuElement criaItemCargoAutoridade() {
        return criaItemMenuPadraoSemIcone(Constantes.ITEM_MENU_CARGO_AUTORIDADE_ID, Constantes.ITEM_MENU_CARGO_AUTORIDADE_LABEL, Constantes.ITEM_MENU_CARGO_AUTORIDADE_URL);
    }

    private MenuElement criaItemComissaoTematica() {
        return criaItemMenuPadraoSemIcone(Constantes.ITEM_MENU_COMISSAO_TEMATICA_ID, Constantes.ITEM_MENU_COMISSAO_TEMATICA_LABEL, Constantes.ITEM_MENU_COMISSAO_TEMATICA_URL);
    }

    private MenuElement criaItemComiteExecutivo() {
        return criaItemMenuPadraoSemIcone(Constantes.ITEM_MENU_COMITE_EXECUTIVO_ID, Constantes.ITEM_MENU_COMITE_EXECUTIVO_LABEL, Constantes.ITEM_MENU_COMITE_EXECUTIVO_URL);
    }

    private MenuElement criaItemDataValidade() {
        return criaItemMenuPadraoSemIcone(Constantes.ITEM_MENU_DATA_VALIDADE_ID, Constantes.ITEM_MENU_DATA_VALIDADE_LABEL, Constantes.ITEM_MENU_DATA_VALIDADE_URL);
    }

    private MenuElement criaItemEquipeGeref() {
        return criaItemMenuPadraoSemIcone(Constantes.ITEM_MENU_EQUIPE_GESTORA_ID, Constantes.ITEM_MENU_EQUIPE_GESTORA_LABEL, Constantes.ITEM_MENU_EQUIPE_GESTORA_URL);
    }

    private DefaultMenuItem criaItemMenuPadraoSemIcone(String id, String value, String url) {
        DefaultMenuItem defaultMenuItem = new DefaultMenuItem();
        defaultMenuItem.setId(id);
        defaultMenuItem.setValue(value);
        defaultMenuItem.setUrl(url);
        return defaultMenuItem;
    }

    @SuppressWarnings("rawtypes")
    protected Map getSessionMap() {
        if (FacesContext.getCurrentInstance() != null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap() != null) {
            return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        } else {
            return null;
        }
    }

    public MenuModel getMenu() {
        return menu;
    }

    public void setMenu(MenuModel menu) {
        this.menu = menu;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
}
