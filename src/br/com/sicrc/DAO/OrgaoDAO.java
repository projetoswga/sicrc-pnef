package br.com.sicrc.DAO;

import java.util.List;

import br.com.sicrc.entidade.Orgao;

public interface OrgaoDAO {

    List<Orgao> pesquisarOrgao(String query, Integer idEsferaGoverno) throws Exception;

}
