package br.com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

/**
 * Classe embutivel que representa todos os campos de endereco
 * @author Solkam
 * @since 28 abr 2016
 */
@Embeddable
public class Endereco implements Serializable {
	
	@Enumerated(EnumType.STRING)
	private EnderecoTipo enderecoTipo;

	@Size(max=100)
	private String enderecoLogradouro;
	
	@Size(max=30)
	private String enderecoNumero;
	
	@Size(max=50)
	private String enderecoComplemento;
	
	@Size(max=100)
	private String enderecoBairro;
	
	@Size(max=100)
	private String enderecoCidade;
	
	@Size(max=50)
	private String enderecoUF;
	
	@Size(max=20)
	private String enderedoCep;


	//acessores...
	private static final long serialVersionUID = 8445223631108980610L;


	public EnderecoTipo getEnderecoTipo() {
		return enderecoTipo;
	}


	public void setEnderecoTipo(EnderecoTipo enderecoTipo) {
		this.enderecoTipo = enderecoTipo;
	}


	public String getEnderecoLogradouro() {
		return enderecoLogradouro;
	}


	public void setEnderecoLogradouro(String enderecoLogradouro) {
		this.enderecoLogradouro = enderecoLogradouro;
	}


	public String getEnderecoNumero() {
		return enderecoNumero;
	}


	public void setEnderecoNumero(String enderecoNumero) {
		this.enderecoNumero = enderecoNumero;
	}


	public String getEnderecoComplemento() {
		return enderecoComplemento;
	}


	public void setEnderecoComplemento(String enderecoComplemento) {
		this.enderecoComplemento = enderecoComplemento;
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


	public String getEnderedoCep() {
		return enderedoCep;
	}


	public void setEnderedoCep(String enderedoCep) {
		this.enderedoCep = enderedoCep;
	}
	
	
	
}
