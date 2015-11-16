package br.com.sicrc.DAO;

import java.util.Date;
import java.util.List;

import br.com.sicrc.entidade.Representante;
import br.com.sicrc.entidade.RepresentanteArquivo;

public interface RepresentanteDAO {

    Representante buscarRepresentante(String login, Character tipoUsuario) throws Exception;

    List<Representante> pesquisarRepresentante(String query);

    Long countRepresentante(Representante model);

    List<Representante> paginateRepresentante(int first, int pageSize, Representante model);

    List<RepresentanteArquivo> obtemArquivos(Representante representante);

    void alteraAtivacao(Representante representante);

    Date recuperaDatavalidadeCadastroAtual();

}
