package br.com.sicrc.bean;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.arquitetura.bean.PaginableBean;
import br.com.arquitetura.excecao.ExcecaoUtil;
import br.com.arquitetura.relatorios.util.GeradorRelatorio;
import br.com.arquitetura.util.FacesMessagesUtil;
import br.com.sicrc.entidade.Usuario;
import br.com.sicrc.service.EquipeGestoraService;
import br.com.sicrc.util.CpfUtil;
import br.com.sicrc.util.CriptoUtil;

@ManagedBean
@ViewScoped
public class UsuarioBean extends PaginableBean<Usuario> {

    private static final long serialVersionUID = 1L;

    private List<Usuario> equipeGestora;

    @ManagedProperty(value = "#{equipeGestoraService}")
    private EquipeGestoraService equipeGestoraService;

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
        return "nome";
    }

    @Override
    public Usuario createModel() {
        return new Usuario();
    }

    @Override
    public String getQualifiedName() {
        return "Usuário";
    }

    @Override
    public boolean isFeminino() {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String save() {
        try {
            String cpf = getModel().getCpf();
            cpf = cpf.replaceAll("[()-.]", "");
            if (!CpfUtil.isValidCPF(cpf)) {
                FacesMessagesUtil.addErrorMessage("CPF ", "Inválido");
                return "";
            } else {
                Usuario usuario = new Usuario();
                usuario.setCpf(cpf);
                List<Usuario> listaUsuarioVerificacao = universalManager.listBy(usuario);
                if (getModel().getId() == null && listaUsuarioVerificacao != null && !listaUsuarioVerificacao.isEmpty()) {
                    FacesMessagesUtil.addErrorMessage("CPF ", "Já Cadastrado!");
                    return "";
                }
                getModel().setCpf(cpf);
            }
            if (getModel().getId() == null) {
                getModel().setSenha(CriptoUtil.getCriptografia(getModel().getSenha()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.save();
    }

    public List<Usuario> getEquipeGestora() {
        if (equipeGestora == null) {
            equipeGestora = equipeGestoraService.findAll();
        }
        return equipeGestora;
    }

    public void geraRelatorioGestores(String tipo) {
        try {
            String caminhoImagem = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/design/imagem-default/logo-pnef-branca.png");
            GeradorRelatorio.gerarRelatorio(getEquipeGestora(), tipo, caminhoImagem);
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
    }

    public void setEquipeGestora(List<Usuario> equipeGestora) {
        this.equipeGestora = equipeGestora;
    }

    public EquipeGestoraService getEquipeGestoraService() {
        return equipeGestoraService;
    }

    public void setEquipeGestoraService(EquipeGestoraService equipeGestoraService) {
        this.equipeGestoraService = equipeGestoraService;
    }
}