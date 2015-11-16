package br.com.arquitetura.etiquetas.vo;


public class MalaDiretoVO {
    private final String remetente;
    private final String destinatario;

    public MalaDiretoVO(String remetente, String destinatario) {
        this.remetente = remetente;
        this.destinatario = destinatario;
    }

	public String getRemetente() {
		return remetente;
	}

	public String getDestinatario() {
		return destinatario;
	}

}
