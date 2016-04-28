package br.com.crm.model.util;

import java.util.List;

/**
 * Utilitarios para Query usando Criteria
 * @author Solkam
 * @since 26 abr 2016
 */
public class QueryUtil {

	private static final String LIKE_SIMBOL = "%";
	
	
	/**
	 * Retorna se uma string estah em branco
	 * testando se estah null e se tem espacos 
	 * em branco.
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return str!=null && !str.trim().isEmpty();
	}
	
	/**
	 * Verifica se um inteiro eh positivo ou zero.
	 * @param num
	 * @return
	 */
	public static boolean isNotNegative(Integer num) {
		return num!=null && num>0;
	}
	
	/**
	 * Verifica se um objeto eh null
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		return obj!=null;
	}
	
	/**
	 * Verifica se lista esta preenchida
	 * @param lista
	 * @return
	 */
	public static boolean isNotEmpty(List<?> lista) {
		return lista!=null && !lista.isEmpty();
	}
	
	
	private static String nvl(String text) {
		return text==null ? "" : text;
	}
	
	/**
	 * Retorna a expressao de like com modo de comparacao INICIAL
	 * @param value
	 * @return expressao like
	 */
	public static String toLikeMatchModeSTART(String value) {
		return nvl(value) + LIKE_SIMBOL;
	}
	
	/**
	 * Retorna a expressao de like com modelo de comparacao INICIAL e FINAL
	 * @param value
	 * @return expressao like
	 */
	public static String toLikeMatchModeANY(String value) {
		return LIKE_SIMBOL + nvl(value) + LIKE_SIMBOL;
	}
	
	
}
