package br.com.crm.model.entity;

/**
 * Enum para os tipos de logradouros
 * @author Solkam
 * @since 28 abr 2016
 */
public enum EnderecoTipo {
	
	RUA("Rua")
	,
	AVENIDA("Avenida")
	,
	ALAMEDA("Alameda")
	,
	BOULEVARD("Boulevard")
	,
	TRAVESSA("Travessa")
	,
	BECO("Beco")
	;
	
	
	private final String descricao;
	
	
	private EnderecoTipo(String d) {
		this.descricao = d;
	}


	public String getDescricao() {
		return descricao;
	}

}
