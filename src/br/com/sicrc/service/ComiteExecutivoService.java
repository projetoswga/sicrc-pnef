package br.com.sicrc.service;

import br.com.sicrc.entidade.ComiteExecutivo;

public interface ComiteExecutivoService {

    Integer recuperarUltimoComiteExecutivo(Integer idRepresentante) throws Exception;

    ComiteExecutivo carregaComiteExecutivoDoUltimoComiteExecutivo(Integer idRepresentante) throws Exception;

}
