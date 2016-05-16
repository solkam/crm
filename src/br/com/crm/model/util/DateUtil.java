package br.com.crm.model.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Utilitarios para se trabalhar com datas
 * @author Solkam
 * @since 26 abr 2016
 */
public class DateUtil {
	
	private static final SimpleDateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
	
	private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");

	private static final SimpleDateFormat DATE_INVERSE_UNDERLINE_FORMATTER = new SimpleDateFormat("yyyy_MM_dd");


	public static final String[] ARRAY_CALENDAR_MES = new String[12];

	/**
	 * Usa os indices do Calendar
	 */
	static {
		ARRAY_CALENDAR_MES[0] ="Janeiro";
		ARRAY_CALENDAR_MES[1] ="Fevereiro";
		ARRAY_CALENDAR_MES[2] ="Março";
		ARRAY_CALENDAR_MES[3] ="Abril";
		ARRAY_CALENDAR_MES[4] ="Maio";
		ARRAY_CALENDAR_MES[5] ="Junho";
		ARRAY_CALENDAR_MES[6] ="Julho";
		ARRAY_CALENDAR_MES[7] ="Agosto";
		ARRAY_CALENDAR_MES[8] ="Setembro";
		ARRAY_CALENDAR_MES[9] ="Outubro";
		ARRAY_CALENDAR_MES[10]="Novembro";
		ARRAY_CALENDAR_MES[11]="Dezembro";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * FORMATADORES
	 */
	
	public static String getDateForFilename() {
		return DATE_INVERSE_UNDERLINE_FORMATTER.format(new Date());
	}

	public static String toStringTimestamp(Date data) {
		return TIMESTAMP_FORMATTER.format(data);
	}

	public static String toStringDate(Date data) {
		return DATE_FORMATTER.format(data);
	}

	public static String toStringTime(Date data) {
		return TIME_FORMATTER.format(data);
	}

	public static Date toTimestamp(String str) throws ParseException {
		Date data = TIMESTAMP_FORMATTER.parse(str.trim());
		return data;
	}

	public static Date toDate(String str) throws ParseException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		// ex.: Use : Date dataIni = DataUtil.strToDate("04/01/2013");
		return df.parse(str);
	}

	public static Date toStartOfDay(Date date) {
		Calendar dCal = Calendar.getInstance();
		dCal.setTime(date);
		dCal.set(Calendar.HOUR_OF_DAY, 0);
		dCal.set(Calendar.MINUTE, 0);
		dCal.set(Calendar.SECOND, 0);
		dCal.set(Calendar.MILLISECOND, 0);

		return dCal.getTime();
	}

	public static Date toEndOfDay(Date date) {
		Calendar dCal = Calendar.getInstance();
		dCal.setTime(date);
		dCal.set(Calendar.HOUR_OF_DAY, 23);
		dCal.set(Calendar.MINUTE, 59);
		dCal.set(Calendar.SECOND, 59);
		dCal.set(Calendar.MILLISECOND, 999);
		return dCal.getTime();
	}
	

	/**
	 * Retorna o ano atual em inteiro
	 * @return
	 */
	public static Integer getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	/**
	 * Retorna o mês atual em inteiro
	 * @return
	 */
	public static Integer getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * Retorna o dia atual em inteiro
	 * @return
	 */
	public static Integer getCurrentDay() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	
	
	
	/*
	 * EXTRATORES
	 */
	
	/**
	 * Retorna o primeiro dia do mes com hora, minutos, segundo e milisendo tratados
	 * @param calendarMonthIndex
	 * @param calendarYearIndex
	 * @return
	 */
	public static Date getFirstDayOfMonth(int calendarMonthIndex, int calendarYearIndex) {
		GregorianCalendar primeiroDiaDoMes = new GregorianCalendar(calendarYearIndex, calendarMonthIndex, 1);
		return toStartOfDay( primeiroDiaDoMes.getTime() );
	}


	/**
	 * Retorna o ultimo dia do mes tambem com os dados de tempo tratados.
	 * Faz uma jogada para obter o ultimo dia do mes (explicada no proprio codigo)
	 * @param calendarMonthIndex
	 * @param calendarYearIndex
	 * @return
	 */
	public static Date getLastDayOfMonth(int calendarMonthIndex, int calendarYearIndex) {
		// inicialmente cria o primeiro Dia Do Mes Seguinte...
		GregorianCalendar ultimoDiaDoMes = new GregorianCalendar(calendarYearIndex, calendarMonthIndex, 1);
		// entao subtrai um dia
		ultimoDiaDoMes.add(Calendar.DAY_OF_MONTH, -1);
		
		return toEndOfDay( ultimoDiaDoMes.getTime() );
	}
	

	
	/**
	 * A partir de uma data, extrai o ANO
	 * @param data
	 * @return
	 */
	public static Integer extractYear(Date data) {
		Calendar calAux = Calendar.getInstance();
		calAux.setTime( data );
		return calAux.get( Calendar.YEAR );
	}
	
	/**
	 * A partir de uma data, extrai o MES
	 * @param data
	 * @return
	 */
	public static Integer extractMonth(Date data) {
		Calendar calAux = Calendar.getInstance();
		calAux.setTime( data );
		return calAux.get( Calendar.MONTH );
	}

	/**
	 * A partir de uma data, extrai o DIA
	 * @param data
	 * @return
	 */
	public static Integer extractDay(Date data) {
		Calendar calAux = Calendar.getInstance();
		calAux.setTime( data );
		return calAux.get( Calendar.DAY_OF_MONTH );
	}

	/**
	 * A partir do indice do mes (calendar), retorna o descritivo do mês
	 * @param idxMesCalendar
	 * @return
	 */
	public static String describeMonth(int idxMesCalendar) {
		return ARRAY_CALENDAR_MES[idxMesCalendar];
	}
	
	
	/*
	 * CALCULADORES
	 */
	
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
	 * Calcula a diferenca em minutos entre duas datas.
	 * @param inicio
	 * @param fim
	 * @return diferenca em minutos
	 */
	public static Integer calculateDiffInMinutes(Date inicio, Date fim) {
		long inicioMilis = inicio.getTime();
		long fimMilis = fim.getTime();
		long diferencaMilis = fimMilis - inicioMilis;

		int diferencaEmMin = (int) diferencaMilis / (1000 * 60);
		return diferencaEmMin;
	}

	/**
	 * Calcula a diferenca em dias entre duas datas
	 * @param inicio
	 * @param fim
	 * @return diferenca em dias
	 */
	public static int calculateDiffInDays(Date inicio, Date fim) {
		// milisegundos
		long diff = fim.getTime() - inicio.getTime();
		// dias
		long diffDias = diff / (1000 * 60 * 60 * 24);		
		return (int)diffDias;
	}
	
	
	/**
	 * Calcula a diferença entre duas datas de maneira descritiva, ou seja, no
	 * formato 1d 13h 45m
	 * @param inicio
	 * @param fim
	 * @return
	 */
	public static String calculateDescribeDiff(Date inicio, Date fim, boolean flagPrecisaoEmSegundos) {
		if (inicio == null || fim == null)
			return "?";

		StringBuilder descritivo = new StringBuilder();

		// milisegundos
		long diff = fim.getTime() - inicio.getTime();

		// dias
		long diffDias = diff / (1000 * 60 * 60 * 24);
		if (diffDias > 0) {
			descritivo.append(diffDias + "d ");
		}

		// horas
		long diffHoras = diff / (60 * 60 * 1000) % 24;
		if (diffHoras > 0) {
			descritivo.append(diffHoras + "h ");
		}

		// minutos
		long diffMinutos = diff / (60 * 1000) % 60;
		if (diffMinutos > 0) {
			descritivo.append(diffMinutos + "m ");
		}

		if (flagPrecisaoEmSegundos) {
			// segundos
			long diffSegundos = diff / 1000 % 60;
			if (diffSegundos > 0) {
				descritivo.append(diffSegundos + "s ");
			}
		}

		return descritivo.toString();
	}
	
	
	
	/* ********
	 * BUILDERS
	 * ********/
	
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
	 * A partir de uma data inicial e outra final, criar uma lista de datas
	 * intermediárias
	 * @param initDate
	 * @param finalDate
	 * @return lista de datas intermediárias inclusivas
	 */
	public static List<Date> buildDateList(Date initDate, Date finalDate) {
		//1.Verifica datas
		if (initDate == null)
			throw new IllegalArgumentException("Initial Date is null");
		
		if (finalDate == null)
			throw new IllegalArgumentException("Final Date is null");
		
		if (initDate.after(finalDate))
			throw new IllegalArgumentException(	"Initial Date is after Final Date");
		
		//2.constrói
		List<Date> dateList = new ArrayList<Date>();

		Calendar initCal = Calendar.getInstance();
		initCal.setTime(initDate);

		Calendar finalCal = Calendar.getInstance();
		finalCal.setTime(finalDate);

		Calendar indexCal = initCal;
		while (indexCal.before(finalCal) || indexCal.equals(finalCal)) {
			dateList.add(indexCal.getTime());

			indexCal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return dateList;
	}
	
	
	

	
	
	/* VERIFICADORES INTERNOS
	 ************************/
	
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
	

}
