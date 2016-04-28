package br.com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

/**
 * Classe embutivel que representa todos os 
 * telefone possiveis de um contact
 * @author Solkam
 * @since 28 abr 2016
 */
@Embeddable
public class Telefone implements Serializable {
	
	private static final String DEFAULT_NUMBER = "+55 (49)";

	@Size(max=20)
	private String telefoneCelular1 = DEFAULT_NUMBER;

	@Enumerated(EnumType.STRING)
	private TelefoneOperadora telefoneCelular1Operadora;

	
	@Size(max=20)
	private String telefoneCelular2 = DEFAULT_NUMBER;

	@Enumerated(EnumType.STRING)
	private TelefoneOperadora telefoneCelular2Operadora;

	
	@Size(max=20)
	private String telefoneComercial = DEFAULT_NUMBER;
	
	
	@Size(max=20)
	private String telefoneResidencial = DEFAULT_NUMBER;


	//acessores...
	private static final long serialVersionUID = -39202776833901706L;


	public String getTelefoneCelular1() {
		return telefoneCelular1;
	}


	public void setTelefoneCelular1(String telefoneCelular1) {
		this.telefoneCelular1 = telefoneCelular1;
	}


	public TelefoneOperadora getTelefoneCelular1Operadora() {
		return telefoneCelular1Operadora;
	}


	public void setTelefoneCelular1Operadora(TelefoneOperadora telefoneCelular1Operadora) {
		this.telefoneCelular1Operadora = telefoneCelular1Operadora;
	}


	public String getTelefoneCelular2() {
		return telefoneCelular2;
	}


	public void setTelefoneCelular2(String telefoneCelular2) {
		this.telefoneCelular2 = telefoneCelular2;
	}


	public String getTelefoneComercial() {
		return telefoneComercial;
	}


	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}


	public TelefoneOperadora getTelefoneCelular2Operadora() {
		return telefoneCelular2Operadora;
	}


	public void setTelefoneCelular2Operadora(TelefoneOperadora telefoneCelular2Operadora) {
		this.telefoneCelular2Operadora = telefoneCelular2Operadora;
	}


	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}


	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}


	
}
