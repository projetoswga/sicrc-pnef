package br.com.arquitetura.util;

import javax.servlet.http.HttpServletRequest;

public class StringUtil {

	/**
	 * transforma a primeira letra de um texto em minuscula
	 * 
	 * @param texto
	 * @return
	 */
	public static String lowerCaseFirstLetter(String texto) {
		return texto.substring(0, 1).toLowerCase() + texto.substring(1);
	}

	// Coloca Mascara
	public static String format(String format, String value, char c) {
		if (format == null || format.trim().equals(""))
			return null;
		int index = 0;
		StringBuilder s = new StringBuilder();
		char charsFormat[] = format.toCharArray();
		char charsValue[] = value.toCharArray();
		char arr$[] = charsFormat;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++) {
			char element = arr$[i$];
			if (element != c)
				s.append(element);
			else
				s.append(charsValue[index++]);
		}

		return s.toString();
	}
	
	

	public static String getRequestIP(HttpServletRequest request) {

		// Recuperando o IP passando pelo proxy
		String address = request.getHeader("X-Forwarded-For");

		if (address != null) {
			return address;
		}

		// Senao, recupera o IP remoto
		return request.getRemoteAddr();
	}

	public static String format(String format, String value) {
		return format(format, value, '#');
	}

	public static String unformat(final String format, final String value) {
		return StringUtil.unformat(format, value, '#');
	}

	public static String unformat(final String format, final String value, final char c) {

		if (format == null || format.trim().equals(""))
			return null;

		if (value == null || value.trim().equals(""))
			return null;

		StringBuilder s = new StringBuilder();

		char[] charsFormat = format.toCharArray();
		char[] charsValue = value.toCharArray();

		for (int i = 0; i < charsFormat.length; i++) {
			if (charsFormat[i] == c) {
				s.append(charsValue[i]);
			}
		}

		return s.toString();
	}

    public static String primeiraLetraMaiuscula(String valor) {
        if (valor != null && !valor.isEmpty()) {
            String primeiraLetra = valor.substring(0, 1).toUpperCase();
            if (valor.length() > 1) {
                return primeiraLetra + valor.substring(1);
            } else {
                return primeiraLetra;
            }
        }
        return valor;
    }

}
