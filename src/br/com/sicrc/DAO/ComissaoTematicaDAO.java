package br.com.sicrc.DAO;

import br.com.sicrc.entidade.ComissaoTematica;

public interface ComissaoTematicaDAO {

    Integer recuperarUltimaComissaoTematica(Integer idRepresentante) throws Exception;

    ComissaoTematica carregaComissaoTematicaDaUltimaComissaoTematica(Integer idRepresentante) throws Exception;

}
