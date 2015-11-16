package br.com.sicrc.bean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.arquitetura.etiquetas.util.GeradorEtiquetaMalaDireta;
import br.com.arquitetura.excecao.ExcecaoUtil;
import br.com.arquitetura.util.FacesMessagesUtil;
import br.com.sicrc.dominios.PARAMETROS;
import br.com.sicrc.entidade.Representante;
import br.com.sicrc.util.Constantes;

@ManagedBean(name = "malaDiretaBean")
@ViewScoped
public class MalaDiretaBean extends RepresentanteComumBean {

    private static final long serialVersionUID = 8643666611168672762L;

    private Representante representante;
    private List<Representante> representantes;
    private String assunto;
    private String corpoEmail;
    private String msgAviso;
    private List<UploadedFile> anexos;

    public MalaDiretaBean() {
        anexos = new ArrayList<UploadedFile>();
    }

    public List<Representante> completeRepresentante(String query) {
        List<Representante> sugestoes = new ArrayList<Representante>();
        try {
            sugestoes = representanteService.pesquisarRepresentante(query);
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
        return sugestoes;
    }

    public void mensagemAviso() {
        try {
            if (assunto == null || assunto.isEmpty()) {
                FacesMessagesUtil.addErrorMessage("Assunto", Constantes.CAMPO_OBRIGATORIO);
                return;
            }
            if (corpoEmail == null || corpoEmail.isEmpty()) {
                FacesMessagesUtil.addErrorMessage("Email", Constantes.CAMPO_OBRIGATORIO);
                return;
            }
            if (representantes == null || representantes.size() == 0) {
                FacesMessagesUtil.addErrorMessage("", "É necessário selecionar pelo menos um representante!");
                return;
            }

            StringBuilder msg = new StringBuilder();
            msg.append("<b>Confirma o envio de " + representantes.size() + " E-mail(s) para:</b> <br/><br/>");
            for (Representante representante : representantes) {
                msg.append(representante.getEmail() + "<br/>");
            }
            msg.append("<br/>");
            msgAviso = msg.toString();

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("enviarEmail.show();");
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
    }

    public void enviarEmail() {
        try {

            if (assunto == null || assunto.isEmpty()) {
                FacesMessagesUtil.addErrorMessage("Assunto", Constantes.CAMPO_OBRIGATORIO);
                return;
            }
            if (corpoEmail == null || corpoEmail.isEmpty()) {
                FacesMessagesUtil.addErrorMessage("Email", Constantes.CAMPO_OBRIGATORIO);
                return;
            }

            if (representantes == null || representantes.size() == 0) {
                FacesMessagesUtil.addErrorMessage("", "É necessário selecionar pelo menos um candidato!");
                return;
            }

            representanteService.enviarEmails(representantes, assunto, corpoEmail, loginBean.getModel().getEmail(), anexos);

            assunto = null;
            corpoEmail = null;
            representante = null;
            representantes = null;
            anexos = new ArrayList<UploadedFile>();
            FacesMessagesUtil.addInfoMessage("Os E-mails estão sendo enviados, ", " assim que for concluído você receberá um e-mail, no seguinte endereço " + loginBean.getModel().getEmail());
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
    }

    public void anexar(FileUploadEvent event) {
        if (anexos.size() < 3) {
            anexos.add(event.getFile());
        } else {
            FacesMessagesUtil.addInfoMessage("Anexo ", " Quantidade de anexos excedida!");
        }
    }

    public void removerArquivo(UploadedFile arquivo) {
        anexos.remove(arquivo);
        FacesMessagesUtil.addInfoMessage("Arquivo ", " removido com sucesso!");
    }

    @Override
    public void pesquisar() {
        try {
            super.pesquisar();
            assunto = null;
            corpoEmail = null;
            msgAviso = null;
            representantes = null;
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
    }

    public void preProcessamentoEtiqueta() {
        if (representantes == null || representantes.size() == 0) {
            FacesMessagesUtil.addErrorMessage("", "É necessário selecionar pelo menos um candidato!");
            return;
        }

        StringBuilder msg = new StringBuilder();
        msg.append("<b>Serão geradas " + representantes.size() + " etiquetas</b>");
        msgAviso = msg.toString();

        executaJQuery("selecionaTipoEtiquetaWV.show();");
    }

    public void geraEtiquetas() {
        try {
            String htmlDadosRepresentante = parametroBean.getParametro(PARAMETROS.REMETENTE_PADRAO.getId()).getValor();
            String jrxmlEtiqueta = parametroBean.getParametro(PARAMETROS.ETIQUETA_MALA_DIRETA.getId()).getValor();
            InputStream isRelatorio = new ByteArrayInputStream(jrxmlEtiqueta.getBytes(StandardCharsets.UTF_8));
            GeradorEtiquetaMalaDireta.gerarEtiqueta(isRelatorio, representantes, htmlDadosRepresentante);
        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getCorpoEmail() {
        return corpoEmail;
    }

    public void setCorpoEmail(String corpoEmail) {
        this.corpoEmail = corpoEmail;
    }

    public String getMsgAviso() {
        return msgAviso;
    }

    public void setMsgAviso(String msgAviso) {
        this.msgAviso = msgAviso;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<Representante> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(List<Representante> representantes) {
        this.representantes = representantes;
    }

    public List<UploadedFile> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<UploadedFile> anexos) {
        this.anexos = anexos;
    }
}
