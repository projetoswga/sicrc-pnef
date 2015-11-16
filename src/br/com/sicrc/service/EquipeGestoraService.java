package br.com.sicrc.service;

import java.util.List;

import br.com.sicrc.entidade.Usuario;

public interface EquipeGestoraService {

    List<Usuario> findAll();
}