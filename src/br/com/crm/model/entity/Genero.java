package br.com.crm.model.entity;

/**
 * Representa os generos
 * @author Solkam
 * @since 28 abr 2016
 */
public enum Genero {
	
	M("Masculino")
	,
	F("Feminino")
	;
	
	private final String descricao;

	
	private Genero(String d) {
		this.descricao = d;
	}


	public String getDescricao() {
		return descricao;
	}


	
}
