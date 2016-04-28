package br.com.crm.model.entity;

/**
 * Perfis de acesso de usuarios
 * @author Solkam
 * @since 26 abr 2016
 */
public enum Perfil {
	
	ADM("Administrador")
	,
	OPE("Operador")
	;

	
	private final String descricao;
	
	private Perfil(String d) {
		this.descricao = d;
	}

	public String getDescricao() {
		return descricao;
	}
	

}
