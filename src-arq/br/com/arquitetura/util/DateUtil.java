package br.com.arquitetura.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 * @param date
	 * @return
	 */
	public static String getDataHora(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		return formatter.format(date);
	}

	/**
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getDataHora(String date, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(date);
	}

	public static Date getDateSemHora(Date date) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dtString = format.format(date);
		return (Date) format.parse(dtString);
	}

}
