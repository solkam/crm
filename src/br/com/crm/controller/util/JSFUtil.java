package br.com.crm.controller.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utilit�rio para JSF como faces messages.
 * @author Solkam
 * @since 25 JAN 2015
 */
public class JSFUtil {
	
	/**
	 * Adiciona mensagem informativa ao contexto face
	 * @param txt
	 */
	public static void addInfoMessage(String txt) {
		FacesMessage fm = new FacesMessage( txt );
		fm.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	
	/**
	 * Adiciona messagem de erro ao contexto faces
	 * @param txt
	 */
	public static void addWarnMessage(String txt) {
		FacesMessage fm = new FacesMessage( txt );
		fm.setSeverity(FacesMessage.SEVERITY_WARN);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	
	/**
	 * Adiciona messagem de erro ao contexto faces
	 * @param txt
	 */
	public static void addErroMessage(String txt) {
		FacesMessage fm = new FacesMessage( txt );
		fm.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	
	public static void addErrorMessage(Throwable throwable) {
		JSFUtil.addErroMessage( throwable.getMessage() );
	}


	public static void addFatalMessage(String msg1, Throwable fatalException) {
		String msg = msg1 + ": " + fatalException.getMessage();
		JSFUtil.addErroMessage( msg );
	}
	


	public static void addMessageAboutResult(List<?> results) {
		if (results!=null) {
			if (results.isEmpty()) {
				JSFUtil.addWarnMessage("Nenhum resultado foi encontrado");
				
			} else {
				String msg = "";
				if (results.size()==1) {
					msg = "Foi encontrado apenas %d resultado";
				} else {
					msg = "Foram encontrados %d resultados";
				}
				JSFUtil.addInfoMessage( String.format(msg, results.size()) );
			}
		}
	}
	
	
	

	
	
	/**
	 * Recupera o request a partir do contexto do faces
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	
	/**
	 * Recupera o session a partir do contexto do faces
	 * @return
	 */
	public static HttpSession getHttpSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

}