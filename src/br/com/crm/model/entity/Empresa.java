package br.com.crm.model.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * Classe embarcavel que representa a empresa
 * onde o contato trabalha
 * @author Solkam
 * @since 28 abr 2016
 */
@Embeddable
public class Empresa  {

	/**
	 * Empresa em que trabalha
	 */
	@Size(max=100)
	private String empresaNome;
	
	/**
	 * Cargo na empresa
	 */
	@Size(max=100)
	private String empresaFuncao;

	public String getEmpresaNome() {
		return empresaNome;
	}

	public void setEmpresaNome(String empresaNome) {
		this.empresaNome = empresaNome;
	}

	public String getEmpresaFuncao() {
		return empresaFuncao;
	}

	public void setEmpresaFuncao(String empresaFuncao) {
		this.empresaFuncao = empresaFuncao;
	}



	
}
