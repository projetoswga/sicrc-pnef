package br.com.sicrc.service;

import java.util.Date;
import java.util.List;

import org.primefaces.model.UploadedFile;

import br.com.sicrc.entidade.Representante;
import br.com.sicrc.entidade.RepresentanteArquivo;

public interface RepresentanteService {

    void save(Representante model, String senhaGerada, boolean edicao) throws Exception;

    List<Representante> pesquisarRepresentante(String query);

    void enviarEmails(List<Representante> representantes, String assunto, String corpoEmail, String email, List<UploadedFile> anexos);

    Long countRepresentante(Representante model);

    List<Representante> paginateRepresentante(int first, int pageSize, Representante model);

    List<RepresentanteArquivo> obtemArquivos(Representante representante);

    void alteraAtivacao(Representante model);

    Date recuperaDatavalidadeCadastroAtual();
}