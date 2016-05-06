package br.com.crm.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 * Embutivel para informações de log
 * @author Solkam
 * @since 26 abr 2016
 */
@Embeddable
public class InfoLog implements Serializable {
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date criadoEm;
	
	@Size(max=100)
	private String criadoPor;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date atualizadoEm;
	
	@Size(max=100)
	private String atualizadoPor;
	
	
	
	//acessores...
	private static final long serialVersionUID = -6218003474208241439L;
	public Date getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Date criadoEm) {
		this.criadoEm = criadoEm;
	}

	public String getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	public Date getAtualizadoEm() {
		return atualizadoEm;
	}

	public void setAtualizadoEm(Date atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	public String getAtualizadoPor() {
		return atualizadoPor;
	}

	public void setAtualizadoPor(String atualizadoPor) {
		this.atualizadoPor = atualizadoPor;
	}

	
	
	//flag dinamicas...
	
	public Boolean getFlagJaFoiAtualizado() {
		return getAtualizadoEm() != null;
	}
	
   
}
