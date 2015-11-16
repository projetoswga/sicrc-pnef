package br.com.sicrc.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.primefaces.model.UploadedFile;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import br.com.arquitetura.excecao.ExcecaoUtil;
import br.com.sicrc.entidade.Representante;
import br.com.sicrc.util.Constantes;

public class ThreadEmail extends Thread {

	private JavaMailSender simpleMailSender;

	private List<Representante> representantes;

	private String assunto;

	private String corpoEmail;

	private String email;
	
	private List<UploadedFile> anexos;

	public ThreadEmail(JavaMailSender simpleMailSender, List<Representante> representantes, String assunto, String corpoEmail, String email, List<UploadedFile> anexos) {
		this.simpleMailSender = simpleMailSender;
		this.representantes = representantes;
		this.assunto = assunto;
		this.corpoEmail = corpoEmail;
		this.email = email;
		this.anexos = anexos;
	}

	public void run() {
		List<String> emailsNaoEnviados = new ArrayList<String>();
		List<String> emailsEnviados = new ArrayList<String>();
		try {
			for (Representante obj : representantes) {
				MimeMessage messagem = simpleMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(messagem, true);
				helper.setTo(obj.getEmail());
				helper.setFrom(Constantes.EMAIL_SISTEMA);
				helper.setSubject(assunto);
				helper.setText(corpoEmail, true);
				
				if (anexos != null && !anexos.isEmpty()) {
					for (UploadedFile anexo: anexos){
						File file = File.createTempFile(anexo.getFileName(), null);
						file.deleteOnExit();
						FileOutputStream stream = new FileOutputStream(file);
						stream.write(anexo.getContents());
						stream.flush();
						stream.close();
						helper.addAttachment(anexo.getFileName(), file);
					}
				}
				
				try {
					simpleMailSender.send(messagem);
					emailsEnviados.add(obj.getEmail());
				} catch (MailSendException e) {
					emailsNaoEnviados.add(obj.getEmail());
				}
			}

			// Envia email para o responsável avisando se tudo de certo.
			MimeMessage messagem = simpleMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(messagem, true);
			helper.setTo(email);
			helper.setFrom(Constantes.EMAIL_SISTEMA);
			helper.setSubject("[SICRC - PNEF] - Envio de mala direta");

			StringBuilder msg = new StringBuilder();
			msg.append("<b><font color=\"blue\">Emails enviados com sucesso:</font> </b> <br/><br/>");
			for (String email : emailsEnviados) {
				msg.append(email + "<br/>");
			}
			msg.append("<br/>");
			msg.append("<br/>");
			if (emailsNaoEnviados != null && !emailsNaoEnviados.isEmpty()) {
				msg.append("<b><font color=\"red\">Emails não enviados:</font> </b> <br/><br/>");
				for (String email : emailsNaoEnviados) {
					msg.append(email + "<br/>");
				}
				msg.append("<br/>");
			}

			helper.setText(msg.toString(), true);
			simpleMailSender.send(messagem);
		} catch (Exception e) {
			ExcecaoUtil.tratarExcecao(e);
		}
	}

	public JavaMailSender getSimpleMailSender() {
		return simpleMailSender;
	}

	public void setSimpleMailSender(JavaMailSender simpleMailSender) {
		this.simpleMailSender = simpleMailSender;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Representante> getRepresentantes() {
		return representantes;
	}

	public void setRepresentantes(List<Representante> representantes) {
		this.representantes = representantes;
	}
}
