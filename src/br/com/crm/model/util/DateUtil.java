package br.com.crm.model.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Utilitarios para se trabalhar com datas
 * @author Solkam
 * @since 26 abr 2016
 */
public class DateUtil {
	
	/**
	 * Retorno o ano atual em inteiro
	 * @return
	 */
	public static Integer getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	
	/**
	 * Calcula a idade a partir da data de nascimento
	 * @param birthday
	 * @return idade
	 */
	public static Integer calculateAge(Date birthDate) {
		throwIfNull(birthDate);
		
		Calendar birthCalendar = Calendar.getInstance();
		birthCalendar.setTime( birthDate );
		
		Calendar today = Calendar.getInstance();
		
		int age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}
		return age;
	}
	
	
	/**
	 * Validate se os parametros individuais para ano, mes e dia 
	 * formam uma data valida 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static boolean isAValidDate(Integer year, Integer month, Integer day) {
		if (hasNull(year, month, day)) return false;

		try {
			GregorianCalendar calendarAux = new GregorianCalendar(year, month-1, day);
			if (day == calendarAux.get(Calendar.DAY_OF_MONTH)) {
				return true;
			} else {
				return false;
			}
			
		} catch(Exception e) {
			return false;
		}
	}
	
	
	/**
	 * A partir dos parametros individuais de ano, mes e dia
	 * construe um Date valido
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date buildDate(Integer year, Integer month, Integer day) {
		if (hasNull(year, month, day)) return null;
		
		if (isAValidDate(year, month, day)) {
			return new GregorianCalendar(year, month-1, day).getTime();
		} else {
			return null; 
		}
	}
	
	
	
	/**
	 * Verifica se algum parametro eh null.
	 * (para validacoes short-circuit)
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private static boolean hasNull(Integer year, Integer month, Integer day) {
		if (year==null || month==null || day==null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Lanca exception se parametro vier null
	 * (para validacoes short-circuit)
	 * @param param
	 */
	private static void throwIfNull(Object param) {
		if (param==null) {
			throw new IllegalArgumentException("Parametro null passado para metodo em CalendarUtil");
		}
	}

}
