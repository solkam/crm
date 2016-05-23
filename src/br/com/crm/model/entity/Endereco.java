package br.com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * Classe embutivel que representa todos os campos de endereco
 * @author Solkam
 * @since 28 abr 2016
 */
@Embeddable
public class Endereco implements Serializable {
	
	@Size(max=200)
	private String enderecoCompleto;
	
	@Size(max=100)
	private String enderecoBairro;
	
	@Size(max=100)
	private String enderecoCidade;
	
	@Size(max=50)
	private String enderecoUF;
	
	@Size(max=20)
	private String enderecoCep;


	//acessores...
	private static final long serialVersionUID = 8445223631108980610L;


	public String getEnderecoCompleto() {
		return enderecoCompleto;
	}


	public void setEnderecoCompleto(String enderecoCompleto) {
		this.enderecoCompleto = enderecoCompleto;
	}


	public String getEnderecoBairro() {
		return enderecoBairro;
	}


	public void setEnderecoBairro(String enderecoBairro) {
		this.enderecoBairro = enderecoBairro;
	}


	public String getEnderecoCidade() {
		return enderecoCidade;
	}


	public void setEnderecoCidade(String enderecoCidade) {
		this.enderecoCidade = enderecoCidade;
	}


	public String getEnderecoUF() {
		return enderecoUF;
	}


	public void setEnderecoUF(String enderecoUF) {
		this.enderecoUF = enderecoUF;
	}


	public String getEnderecoCep() {
		return enderecoCep;
	}


	public void setEnderecoCep(String enderecoCep) {
		this.enderecoCep = enderecoCep;
	}
	
	
	
}
