package br.com.crm.model.exception;

import javax.ejb.ApplicationException;

/**
 * Hierarquia de exception de negocio
 * @author Solkam
 * @since 26 abr 2016
 */
@ApplicationException(rollback=false)
public class NegocioException extends RuntimeException {

	public NegocioException(String msg) {
		super(msg);
	}
	
	

	private static final long serialVersionUID = -737466541972315213L;
}
