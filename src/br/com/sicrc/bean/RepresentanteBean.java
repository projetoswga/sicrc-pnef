package br.com.sicrc.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.arquitetura.excecao.ExcecaoUtil;
import br.com.arquitetura.util.FacesMessagesUtil;
import br.com.sicrc.entidade.CargoAutoridade;
import br.com.sicrc.entidade.ComissaoTematica;
import br.com.sicrc.entidade.ComiteExecutivo;
import br.com.sicrc.entidade.EsferaGoverno;
import br.com.sicrc.entidade.Municipio;
import br.com.sicrc.entidade.Orgao;
import br.com.sicrc.entidade.Regiao;
import br.com.sicrc.entidade.Representante;
import br.com.sicrc.entidade.RepresentanteArquivo;
import br.com.sicrc.service.ComissaoTematicaService;
import br.com.sicrc.service.ComiteExecutivoService;
import br.com.sicrc.util.Constantes;
import br.com.sicrc.util.CpfUtil;
import br.com.sicrc.util.CriptoUtil;
import br.com.sicrc.util.PasswordUtil;
import br.com.sicrc.util.ValidacaoUtil;

@ManagedBean(name = "representanteBean")
@ViewScoped
public class RepresentanteBean extends RepresentanteComumBean {

    private static final long serialVersionUID = 7790474341461779345L;

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    @ManagedProperty(value = "#{comissaoTematicaService}")
    protected ComissaoTematicaService comissaoTematicaService;

    @ManagedProperty(value = "#{comiteExecutivoService}")
    protected ComiteExecutivoService comiteExecutivoService;

    private Integer idRegiao;
    private List<Regiao> regioes;
    private String confirmacaoEmail;
    private boolean edicao;
    private String template;

    /* Captcha */
    private Integer num1 = 0;
    private Integer num2 = 0;
    private Integer num3 = 0;
    private String resposta;
    private String pergunta;

    public RepresentanteBean() {
        regioes = new ArrayList<Regiao>();
        regenarCaptcha();
    }

    @Override
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void carregarTela() {
        super.carregarTela();
        Integer idRepresentante = (Integer) getSessionMap().remove("idRepresentante");
        if (idRepresentante != null && !idRepresentante.equals(0)) {
            getModel().setId(idRepresentante);
            edicao = true;
            this.load();
        }

        try {
            if (regioes.isEmpty()) {
                regioes = universalManager.getAll(Regiao.class, "descricao");
            }
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }

        if (loginBean.getTipoUsuario() != null && loginBean.getTipoUsuario().equals('U')) {
            template = "/templates/template.xhtml";
        } else {
            template = "/templates/template-sem-menu.xhtml";
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public String save() {
        try {
            if ((getModel().getFlgDef() == null || !getModel().getFlgDef()) && (getModel().getFlgGef() == null || !getModel().getFlgGef())) {
                FacesMessagesUtil.addErrorMessage("Seleção Obrigatório ", "É necessário escolher pelo menos uma das opções:  Representante GEF ou Coordenador DEF");
                return "";
            }

            if (super.getOrgaoSelecionado() == null || super.getOrgaoSelecionado().getId() == null) {
                FacesMessagesUtil.addErrorMessage("Orgão ", Constantes.CAMPO_OBRIGATORIO);
                return "";
            }

            if (!getModel().getEmail().trim().equals(confirmacaoEmail)) {
                FacesMessagesUtil.addErrorMessage("Confirmação do Email ", "Inválida");
                return "";
            }

            if (getModel().getCpf() == null || getModel().getCpf().isEmpty()) {
                FacesMessagesUtil.addErrorMessage("CPF ", Constantes.CAMPO_OBRIGATORIO);
                return "";
            }
            String cpf = getModel().getCpf().trim().replaceAll("[()-.]", "");
            if (!CpfUtil.isValidCPF(cpf)) {
                FacesMessagesUtil.addErrorMessage("CPF ", "Inválido");
                return "";
            }

            if (getModel().getCpf() != null && !getModel().getCpf().trim().equals("")) {
                getModel().setCpf(getModel().getCpf().trim().replaceAll("[()-.]", ""));
            }

            Representante representantePesq = new Representante();
            representantePesq.setCpf(cpf);
            List<Representante> listaRepresentantes;
            listaRepresentantes = universalManager.listBy(representantePesq, false);
            if (listaRepresentantes != null && !listaRepresentantes.isEmpty() && !listaRepresentantes.get(0).getId().equals(getModel().getId())) {
                FacesMessagesUtil.addErrorMessage("CPF ", " Já cadastrado");
                return ERROR;
            }

            String senhaGerada = "";
            if (edicao) {
                senhaGerada = getModel().getSenha();
            } else {
                senhaGerada = PasswordUtil.gerarPassword(6);
                getModel().setSenha(CriptoUtil.getCriptografia(senhaGerada));
                getModel().setFlgPrimeiroAcesso(true);
                getModel().setDtCadastro(Calendar.getInstance().getTime());
                getModel().setDtValidade(representanteService.recuperaDatavalidadeCadastroAtual());
            }
            getModel().setMunicipio(new Municipio(super.getIdMunicipio()));
            getModel().setOrgao(new Orgao(super.getOrgaoSelecionado().getId()));
            getModel().getOrgao().setEsferaGoverno(new EsferaGoverno(super.getIdEsferaGoverno()));
            getModel().setCargoAutoridade(new CargoAutoridade(super.getIdCargoAutoridade()));
            getModel().setRegiao(new Regiao(idRegiao));
            if (super.getIdComissaoTematica() != null && super.getIdComissaoTematica() != 0) {
                getModel().setComissaoTematica(new ComissaoTematica(super.getIdComissaoTematica()));
            }
            if (super.getIdComiteExecutivo() != null && super.getIdComiteExecutivo() != 0) {
                getModel().setComiteExecutivo(new ComiteExecutivo(super.getIdComiteExecutivo()));
            }
            representanteService.save(getModel(), senhaGerada, edicao);

            RequestContext context = RequestContext.getCurrentInstance();
            if (edicao) {
                context.execute("popConfirmacao.show();");
            } else {
                context.execute("popAviso.show();");
            }
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
        return SUCCESS;
    }

    public void redirectTelaPrincipal() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/" + Constantes.CONTEXT_PATH + "/pages/principal.jsf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String load() {
        try {
            super.load();
            if (getModel() != null) {
                confirmacaoEmail = getModel().getEmail();
                if (getModel().getMunicipio() != null) {
                    if (getModel().getMunicipio().getUf() != null) {
                        super.setIdUf(getModel().getMunicipio().getUf().getId());
                        super.carregarMunicipios(super.getIdUf());
                    }
                    super.setIdMunicipio(getModel().getMunicipio().getId());
                }
                if (getModel().getOrgao() != null) {
                    super.setOrgaoSelecionado((Orgao) universalManager.get(Orgao.class, getModel().getOrgao().getId()));
                    if (getModel().getOrgao().getEsferaGoverno() != null) {
                        super.setIdEsferaGoverno(getModel().getOrgao().getEsferaGoverno().getId());
                    }
                }
                if (getModel().getCargoAutoridade() != null) {
                    super.setIdCargoAutoridade(getModel().getCargoAutoridade().getId());
                }
                if (getModel().getRegiao() != null) {
                    idRegiao = getModel().getRegiao().getId();
                }
                super.setIdComissaoTematica(comissaoTematicaService.recuperarUltimaComissaoTematica(getModel().getId()));
                super.setIdComiteExecutivo(comiteExecutivoService.recuperarUltimoComiteExecutivo(getModel().getId()));

                getModel().setArquivos(representanteService.obtemArquivos(getModel()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @SuppressWarnings("unchecked")
    public String redirecionarTelaPrimeiroAcesso() {
        getSessionMap().put("idRepresentante", getModel().getId());
        return redirect("primeiroAcesso.jsf");
    }

    @SuppressWarnings("unchecked")
    public String validarCaptcha() {
        try {
            boolean captchaValido = false;
            if (getSessionMap().get("captchaValido") != null) {
                captchaValido = (Boolean) getSessionMap().get("captchaValido");
            }
            if (captchaValido) {
                getSessionMap().put("captchaValido", captchaValido);
                return redirect("primeiroAcesso.jsf");
            }

            Integer soma = num1 + num2 + num3;
            if (resposta == null || resposta.isEmpty()) {
                FacesMessagesUtil.addErrorMessage(" ", " Resposta da equação está errada.");
                FacesContext.getCurrentInstance().getExternalContext().redirect("captcha.jsf");
                return "";
            }
            Integer respostaInt = new Integer(ValidacaoUtil.somenteNumero(resposta));
            if (soma != respostaInt.intValue()) {
                regenarCaptcha();
                FacesMessagesUtil.addErrorMessage(" ", " Resposta da equação está errada.");
                return "";
            } else {
                getSessionMap().put("captchaValido", true);
                return redirect("primeiroAcesso.jsf");
            }

        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
        return SUCCESS;
    }

    private void regenarCaptcha() {

        num1 = (int) (Math.random() * 10);
        num2 = (int) (Math.random() * 10);
        num3 = (int) (Math.random() * 10);

        while (num1 == num2 && num1 == num3 && num2 == num3) {
            num1 = (int) (Math.random() * 10);
            num2 = (int) (Math.random() * 10);
            num3 = (int) (Math.random() * 10);
        }

        pergunta = num1 + "+" + num2 + "+" + num3 + "=";
    }

    public void anexarArquivo(FileUploadEvent event) {
        try {
            RepresentanteArquivo arquivo = new RepresentanteArquivo();
            arquivo.setNome(event.getFile().getFileName());
            arquivo.setConteudo(event.getFile().getContents());
            arquivo.setTipo(event.getFile().getContentType());
            arquivo.setRepresentante(new Representante(getModel().getId()));
            getModel().getArquivos().add(arquivo);
            FacesMessagesUtil.addInfoMessage(" ", "Arquivo " + event.getFile().getFileName() + " adicionado com sucesso!");
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
    }

    public void removerArquivoNaoSalvo(RepresentanteArquivo arquivo) {
        if (arquivo.getId() == null) {
            getModel().getArquivos().remove(arquivo);
            FacesMessagesUtil.addInfoMessage("Arquivo ", " removido com sucesso!");
        }
    }

    public void alteraAtivacao() {
        representanteService.alteraAtivacao(getModel());
        FacesMessagesUtil.addInfoMessage("Representante ", getModel().getFlgAtivo() ? "Desativado" : "Ativado" + " com sucesso!");
    }

    public Boolean getExibeDataValidade() {
        try {
            return loginBean.getUsuario().getTp_login().toString().equals("U");
        } catch (NullPointerException e) {
            return false;
        }
    }

    public Integer getNum1() {
        return num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public Integer getNum2() {
        return num2;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }

    public Integer getNum3() {
        return num3;
    }

    public void setNum3(Integer num3) {
        this.num3 = num3;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getConfirmacaoEmail() {
        return confirmacaoEmail;
    }

    public void setConfirmacaoEmail(String confirmacaoEmail) {
        this.confirmacaoEmail = confirmacaoEmail;
    }

    public ComissaoTematicaService getComissaoTematicaService() {
        return comissaoTematicaService;
    }

    public void setComissaoTematicaService(ComissaoTematicaService comissaoTematicaService) {
        this.comissaoTematicaService = comissaoTematicaService;
    }

    public ComiteExecutivoService getComiteExecutivoService() {
        return comiteExecutivoService;
    }

    public void setComiteExecutivoService(ComiteExecutivoService comiteExecutivoService) {
        this.comiteExecutivoService = comiteExecutivoService;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public Integer getIdRegiao() {
        return idRegiao;
    }

    public void setIdRegiao(Integer idRegiao) {
        this.idRegiao = idRegiao;
    }

    public List<Regiao> getRegioes() {
        return regioes;
    }

    public void setRegioes(List<Regiao> regioes) {
        this.regioes = regioes;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public LoginBean getLoginBean() {
        return loginBean;
    }

    @Override
    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
}
