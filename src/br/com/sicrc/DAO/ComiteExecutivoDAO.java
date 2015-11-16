package br.com.sicrc.DAO;

import br.com.sicrc.entidade.ComiteExecutivo;

public interface ComiteExecutivoDAO {

    Integer recuperarUltimoComiteExecutivo(Integer idRepresentante) throws Exception;

    ComiteExecutivo carregaComiteExecutivoDoUltimoComiteExecutivo(Integer idRepresentante) throws Exception;

}
