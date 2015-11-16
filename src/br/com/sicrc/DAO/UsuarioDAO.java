package br.com.sicrc.DAO;

import br.com.sicrc.entidade.Usuario;

public interface UsuarioDAO {

	Usuario buscarUsuario(String login, Character tipoUsuario) throws Exception;

}
