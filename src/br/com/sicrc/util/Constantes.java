package br.com.sicrc.util;

import br.com.arquitetura.util.ConstantesARQ;

public class Constantes extends ConstantesARQ {

    public static final String USUARIO_SESSAO = getString("usuario_sessao");

    public static String SENHA_ATUAL_NAO_CONFERE = getString("senha_atual_nao_confere");
    public static String AMBAS_SENHAS_IDENTICAS = getString("ambas_senhas_identicas");

    public static String EMAIL_SISTEMA = getString("email_sistema");
    public static String SENHA_DEFAULT = getString("senha_default");
    public static Integer USUARIO_SICRC_ID = Integer.valueOf(getString("usuario_sicrc_id"));

    public static String PATH_IMG_WINDOWS = getString("path_img_windows");
    public static String PATH_IMG_LINUX = getString("path_img_linux");
    public static String PATH_LINK_IMG = getString("path_link_img");
    public static Boolean ATIVA_IMG_LINK = new Boolean(getString("ativa_img_link").trim());

    public static String LABEL_SUBMENU_REPRESENTANTE = "Representante";
    public static String LABEL_SUBMENU_INFORMACOES_CADASTRAIS = "Informações Cadastrais";
    public static String LABEL_SUBMENU_SISTEMA = "Sistema";
    public static String LABEL_SUBMENU_APOIO = "Apoio";

    public static final String ITEM_MENU_VISUALIZAR_REPRESENTANTE_ID = getString("item.menu.visualizar.representante.id");
    public static final String ITEM_MENU_VISUALIZAR_REPRESENTANTE_LABEL = getString("item.menu.visualizar.representante.label");
    public static final String ITEM_MENU_VISUALIZAR_REPRESENTANTE_URL = getString("item.menu.visualizar.representante.url");

    public static final String ITEM_MENU_EDITAR_REPRESENTANTE_ID = getString("item.menu.editar.representante.id");
    public static final String ITEM_MENU_EDITAR_REPRESENTANTE_LABEL = getString("item.menu.editar.representante.label");
    public static final String ITEM_MENU_EDITAR_REPRESENTANTE_URL = getString("item.menu.editar.representante.url");

    public static final String ITEM_MENU_MALA_DIRETA_ID = getString("item.menu.mala.direta.id");
    public static final String ITEM_MENU_MALA_DIRETA_LABEL = getString("item.menu.mala.direta.label");
    public static final String ITEM_MENU_MALA_DIRETA_URL = getString("item.menu.mala.direta.url");

    public static final String ITEM_MENU_CARGO_AUTORIDADE_ID = getString("item.menu.cargoautoridade.id");
    public static final String ITEM_MENU_CARGO_AUTORIDADE_LABEL = getString("item.menu.cargoautoridade.label");
    public static final String ITEM_MENU_CARGO_AUTORIDADE_URL = getString("item.menu.cargoautoridade.url");

    public static final String ITEM_MENU_COMISSAO_TEMATICA_ID = getString("item.menu.comissaotematica.id");
    public static final String ITEM_MENU_COMISSAO_TEMATICA_LABEL = getString("item.menu.comissaotematica.label");
    public static final String ITEM_MENU_COMISSAO_TEMATICA_URL = getString("item.menu.comissaotematica.url");

    public static final String ITEM_MENU_COMITE_EXECUTIVO_ID = getString("item.menu.comiteexecutivo.id");
    public static final String ITEM_MENU_COMITE_EXECUTIVO_LABEL = getString("item.menu.comiteexecutivo.label");
    public static final String ITEM_MENU_COMITE_EXECUTIVO_URL = getString("item.menu.comiteexecutivo.url");

    public static final String ITEM_MENU_DATA_VALIDADE_ID = getString("item.menu.data.validade.id");
    public static final String ITEM_MENU_DATA_VALIDADE_LABEL = getString("item.menu.data.validade.label");
    public static final String ITEM_MENU_DATA_VALIDADE_URL = getString("item.menu.data.validade.url");

    public static final String ITEM_MENU_EQUIPE_GESTORA_ID = getString("item.menu.equipe.gestora.id");
    public static final String ITEM_MENU_EQUIPE_GESTORA_LABEL = getString("item.menu.equipe.gestora.label");
    public static final String ITEM_MENU_EQUIPE_GESTORA_URL = getString("item.menu.equipe.gestora.url");

    public static final String ITEM_MENU_USUARIO_ID = getString("item.menu.usuario.id");
    public static final String ITEM_MENU_USUARIO_LABEL = getString("item.menu.usuario.label");
    public static final String ITEM_MENU_USUARIO_URL = getString("item.menu.usuario.url");

    public static final String ITEM_MENU_SEQUENCE_ID = getString("item.menu.sequence.id");
    public static final String ITEM_MENU_SEQUENCE_LABEL = getString("item.menu.sequence.label");
    public static final String ITEM_MENU_SEQUENCE_URL = getString("item.menu.sequence.url");

    public static final String ITEM_MENU_SAIR_ID = getString("item.menu.sair.id");
    public static final String ITEM_MENU_SAIR_LABEL = getString("item.menu.sair.label");
    public static final String ITEM_MENU_SAIR_URL = getString("item.menu.sair.url");

    public static final String DATA_SOURCE_SISFIE = getString("datasource.sisfie");

    public static final String URL_SISTEMA = getString("url.sistema.sisfie");
}
