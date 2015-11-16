package br.com.sicrc.util;

import java.io.File;
import java.util.Date;

import br.com.sicrc.entidade.Representante;

public class ImagemUtil {

    public static String criarNomeArquivo(String passa, Representante representante) {
        passa = passa.replaceAll("[ ]", "");
        passa = passa.replaceAll(" ", "");
        passa = passa.replaceAll("[ÂÀÁÄÃ]", "A");
        passa = passa.replaceAll("[âãàáä]", "a");
        passa = passa.replaceAll("[ÊÈÉË]", "E");
        passa = passa.replaceAll("[êèéë]", "e");
        passa = passa.replaceAll("[ÎÍÌÏ]", "I");
        passa = passa.replaceAll("[îíìï]", "i");
        passa = passa.replaceAll("[ÔÕÒÓÖ]", "O");
        passa = passa.replaceAll("[ôõòóö]", "o");
        passa = passa.replaceAll("[ÛÙÚÜ]", "U");
        passa = passa.replaceAll("[ûúùü]", "u");
        passa = passa.replaceAll("[Ç]", "C");
        passa = passa.replaceAll("[ç]", "c");
        passa = passa.replaceAll("[ýÿ]", "y");
        passa = passa.replaceAll("[Ý]", "Y");
        passa = passa.replaceAll("[ñ]", "n");
        passa = passa.replaceAll("[Ñ]", "N");
        passa = passa.replaceAll("['<>\\|/]", "");

        String idUsuario = "";
        if (representante != null && representante.getId() != null) {
            idUsuario = representante.getId().toString();
        }
        String nomeArquivo = "P" + idUsuario + "_T" + new Date().getTime() + "_" + passa;

        return nomeArquivo;
    }

    /**
     * Definir 100 caracteres com maximo
     * 
     * @param fileName
     * @return
     */
    public static String verificarTamanhoNomeArquivo(String fileName) {

        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        fileName = fileName.trim();
        if (fileName.length() > 100) {
            fileName = fileName.substring(0, 10);
        }

        return fileName;
    }

    public static void apagarFiles(File diretorio) throws Exception {

        if (diretorio == null) {
            return;
        }

        File[] arquivos = diretorio.listFiles();
        for (File arquivo : arquivos) {
            arquivo.delete();
        }

    }
}
