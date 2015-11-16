package br.com.sicrc.entidade;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.excecao.ExcecaoUtil;
import br.com.sicrc.util.Constantes;

@Entity
@Table(name = "representante_arquivo", catalog = "SICRC")
@Audited
public class RepresentanteArquivo extends Entidade<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "representante_arquivo_id_representante_arquivo_generator", sequenceName = "sicrc.representante_arquivo_id_representante_arquivo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "representante_arquivo_id_representante_arquivo_generator")
    @Column(name = "id_representante_arquivo", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_representante")
    private Representante representante;

    @Column(name = "dt_cadastro")
    private Date dtCadastro;

    @Column(name = "nome")
    private String nome;

    @Column(name = "nome_armazenagem")
    private String nomeArmazenagem;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "url_arquivo")
    private String urlArquivo;

    @Transient
    private byte[] conteudo;

    @Transient
    private StreamedContent content;

    public RepresentanteArquivo() {
        super();
        dtCadastro = Calendar.getInstance().getTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrlArquivo() {
        return urlArquivo;
    }

    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public StreamedContent getContent() {
        try {

            if (Constantes.ATIVA_IMG_LINK) {
                if (nome != null && !nome.isEmpty()) {
                    InputStream is = new URL(Constantes.PATH_LINK_IMG + nome).openStream();
                    content = new DefaultStreamedContent(is, this.getTipo(), this.getNome());
                }
            } else {
                File file = new File(urlArquivo);
                InputStream is = new FileInputStream(file);
                content = new DefaultStreamedContent(is, this.getTipo(), this.getNome());
            }

        } catch (Exception e) {
            ExcecaoUtil.tratarExcecao(e);
        }
        return content;
    }

    public String getNomeArmazenagem() {
        return nomeArmazenagem;
    }

    public void setNomeArmazenagem(String nomeArmazenagem) {
        this.nomeArmazenagem = nomeArmazenagem;
    }

}