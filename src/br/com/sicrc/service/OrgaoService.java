package br.com.sicrc.service;

import java.util.List;

import br.com.sicrc.entidade.Orgao;

public interface OrgaoService {

    List<Orgao> pesquisarOrgao(String query, Integer idEsferaGoverno) throws Exception;

}
