package br.com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * Classe embutivel com os possiveis documentos de
 * um contato.
 * @author Solkam
 * @since 01 FEV 2015
 */
@Embeddable
public class DocumentoIdentidade implements Serializable {

	@Size(max=30)
	private String documentoCPF;
	
	@Size(max=30)
	private String documentoPassporte;
	
	@Size(max=30)
	private String documentoRG;


	//acessores...
	private static final long serialVersionUID = -274097638030534880L;


	public String getDocumentoCPF() {
		return documentoCPF;
	}


	public void setDocumentoCPF(String documentoCPF) {
		this.documentoCPF = documentoCPF;
	}


	public String getDocumentoPassporte() {
		return documentoPassporte;
	}


	public void setDocumentoPassporte(String documentoPassporte) {
		this.documentoPassporte = documentoPassporte;
	}


	public String getDocumentoRG() {
		return documentoRG;
	}


	public void setDocumentoRG(String documentoRG) {
		this.documentoRG = documentoRG;
	}
	
	
}
