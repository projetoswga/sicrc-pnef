package br.com.sicrc.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.arquitetura.DAO.UniversalDAO;
import br.com.arquitetura.util.ThreadEnviarEmail;
import br.com.sicrc.DAO.RepresentanteDAO;
import br.com.sicrc.entidade.CargoAutoridade;
import br.com.sicrc.entidade.Municipio;
import br.com.sicrc.entidade.Orgao;
import br.com.sicrc.entidade.Representante;
import br.com.sicrc.entidade.RepresentanteArquivo;
import br.com.sicrc.entidade.Uf;
import br.com.sicrc.service.RepresentanteService;
import br.com.sicrc.thread.ThreadEmail;
import br.com.sicrc.util.Constantes;

@Service(value = "representanteService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RepresentanteServiceImpl implements RepresentanteService {

    @Autowired(required = true)
    @Qualifier(value = "universalDAO")
    protected UniversalDAO dao;

    @Autowired(required = true)
    @Qualifier(value = "representanteDAO")
    protected RepresentanteDAO representanteDAO;

    @Autowired(required = true)
    @Qualifier(value = "mailSender")
    private JavaMailSender simpleMailSender;

    public UniversalDAO getDao() {
        return dao;
    }

    public void setDao(UniversalDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void save(Representante representante, String senhaGerada, boolean edicao) throws Exception {
        dao.save(representante);

        Municipio municipio = (Municipio) dao.get(Municipio.class, representante.getMunicipio().getId());
        Uf uf = (Uf) dao.get(Uf.class, municipio.getUf().getId());
        Orgao orgao = (Orgao) dao.get(Orgao.class, representante.getOrgao().getId());
        CargoAutoridade cargoAutoridade = (CargoAutoridade) dao.get(CargoAutoridade.class, representante.getCargoAutoridade().getId());

        /**
         * Salvando arquivos
         */
        for (RepresentanteArquivo arquivo : representante.getArquivos()) {
            if (arquivo.getId() == null) {
                arquivo.setRepresentante(representante);

                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

                arquivo.setNomeArmazenagem("RP_" + representante.getId() + "_DC_" + df.format(arquivo.getDtCadastro()) + "_NA_" + arquivo.getNome());

                /** Definindo path de armazenamento */
                String os = System.getProperty("os.name");
                String path = null;
                if (os.contains("win") || os.trim().toLowerCase().contains("windows") || os.trim().toLowerCase().contains("win")) {
                    path = Constantes.PATH_IMG_WINDOWS;
                } else {
                    path = Constantes.PATH_IMG_LINUX;
                }

                path += arquivo.getNomeArmazenagem();
                arquivo.setUrlArquivo(path);

                dao.save(arquivo);

                /** Copiando arquivo para diretório */

                File file = new File(path);

                if (!file.exists()) {
                    file.createNewFile();
                }

                OutputStream out = new FileOutputStream(file);
                out.write(arquivo.getConteudo());
                out.flush();
                out.close();

            }
        }

        // prepara conteudo do email
        if (!edicao) {
            StringBuilder textoEmail = new StringBuilder("Cadastro realizado com sucesso!");
            textoEmail.append("<br/>");
            textoEmail.append(" <table border='0' cellspacing='0' cellpadding='0' width='750'>" + "<tr>" + "<td>" + "<table border='0' cellspacing='0' cellpadding='0' align='left' width='85%'>" + "<tr>" + "<td width='100%'>" + "<p>Prezado(a) " + camposEmail(representante.getNome()) + ", O seu cadastro foi realizado com sucesso. A sua senha de acesso &Eacute; <b>" + senhaGerada + "</b>. Seguem os seus dados cadastrais para confer&ecirc;ncia. Caso deseje alterar a sua senha ou dados cadastrais, " + "por favor selecione a op&ccedil;&atilde;o editar Representante no SICRC - Sistema de Cadastro de Representantes e coordenadores - PNEF. " +

            "<p><b>DADOS</b></p> " + "Nome Completo: " + camposEmail(representante.getNome()) + "<br/>" + "Email: " + camposEmail(representante.getEmail()) + "<br/>" + "CPF: " + camposEmail(representante.getCpf()) + "<br/>" + "UF: " + camposEmail(uf.getSigla()) + "<br/>" + "Munic&iacute;pio: " + camposEmail(municipio.getDescricao()) + "<br/>" + "Autoridade: " + camposEmail(representante.getNomeAutoridade()) + "<br/>" + "Logradouro Autoridade: " + camposEmail(representante.getLogradouroAutoridade()) + "<br/>" + "CEP Autoridade: " + camposEmail(representante.getCepAutoridade()) + "<br/>" + "Email Autoridade: " + camposEmail(representante.getEmailAutoridade()) + "<br/>" + "Cargo Autoridade: " + camposEmail(cargoAutoridade.getDescricao()) + "<br/>" + "&Oacute;rg&atilde;o: " + camposEmail(orgao.getNomeSiglaFormat()) + "<br/>" + "</td>" + "</tr>" + "</table>" + "</td>" + "</tr>" + "</table> ");
            textoEmail.append("<br/>");
            textoEmail.append("<b>Login: </b>" + representante.getCpf());
            textoEmail.append("<br/>");
            textoEmail.append("<b>Senha: </b>" + senhaGerada);
            textoEmail.append("<br/>");
            textoEmail.append("<b>Acesso: </b>" + Constantes.URL_SISTEMA);
            textoEmail.append("<br/>");
            textoEmail.append("<br/>");
            textoEmail.append("Mensagem Autom&aacute;tica - Não responder");
            textoEmail.append("<br/>");
            textoEmail.append("<br/>");

            String assunto = "Sistema SICRC - SENHA DE ACESSO E CONFIRMAÇÃO DOS DADOS";

            // envia email
            Thread thread = new ThreadEnviarEmail(simpleMailSender, assunto, textoEmail.toString(), representante.getEmail(), Constantes.EMAIL_SISTEMA, true);
            thread.start();
        }
    }

    private String camposEmail(String campo) throws UnsupportedEncodingException {
        if (campo == null) {
            return "Sem informa&ccedil;&atilde;o";
        }
        return campo;
    }

    public JavaMailSender getSimpleMailSender() {
        return simpleMailSender;
    }

    public void setSimpleMailSender(JavaMailSender simpleMailSender) {
        this.simpleMailSender = simpleMailSender;
    }

    @Override
    public List<Representante> pesquisarRepresentante(String query) {
        return representanteDAO.pesquisarRepresentante(query);
    }

    public RepresentanteDAO getRepresentanteDAO() {
        return representanteDAO;
    }

    public void setRepresentanteDAO(RepresentanteDAO representanteDAO) {
        this.representanteDAO = representanteDAO;
    }

    @Override
    public void enviarEmails(List<Representante> representantes, String assunto, String corpoEmail, String email, List<UploadedFile> anexos) {
        Thread thread = new ThreadEmail(simpleMailSender, representantes, assunto, corpoEmail, email, anexos);
        thread.start();
    }

    @Override
    public Long countRepresentante(Representante model) {
        return representanteDAO.countRepresentante(model);
    }

    @Override
    public List<Representante> paginateRepresentante(int first, int pageSize, Representante model) {
        return representanteDAO.paginateRepresentante(first, pageSize, model);
    }

    @Override
    public List<RepresentanteArquivo> obtemArquivos(Representante representante) {
        if (representante != null) {
            return representanteDAO.obtemArquivos(representante);
        }

        return null;
    }

    @Override
    public void alteraAtivacao(Representante representante) {
        if (representante != null) {
            representanteDAO.alteraAtivacao(representante);
        }
    }

    @Override
    public Date recuperaDatavalidadeCadastroAtual() {
        Date dataValidadeAtual = representanteDAO.recuperaDatavalidadeCadastroAtual();

        Calendar calendar = Calendar.getInstance();

        if (dataValidadeAtual == null || dataValidadeAtual.before(calendar.getTime())) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 30);
            dataValidadeAtual = calendar.getTime();
        }

        return dataValidadeAtual;
    }
}
