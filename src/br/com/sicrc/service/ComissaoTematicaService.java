package br.com.sicrc.service;

import br.com.sicrc.entidade.ComissaoTematica;

public interface ComissaoTematicaService {

    Integer recuperarUltimaComissaoTematica(Integer idRepresentante) throws Exception;

    ComissaoTematica carregaComissaoTematicaDaUltimaComissaoTematica(Integer idRepresentante) throws Exception;

}
