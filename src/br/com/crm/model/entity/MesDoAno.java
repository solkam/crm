package br.com.crm.model.entity;

/**
 * Enum que representa os meses do anos
 * @author Solkam
 * @since 09 mai 2016
 */
public enum MesDoAno {
	
	JAN(1, "Janeiro")
	,
	FEV(2, "Fevereiro")
	,
	MAR(3, "Março")
	,
	ABR(4, "Abril")
	,
	MAI(5, "Maio")
	,
	JUN(6, "Junho")
	,
	JUL(7, "Julho")
	,
	AGO(8, "Agosto")
	,
	SET(9, "Setembro")
	,
	OUT(10, "Outubro")
	,
	NOV(11, "Novembro")
	,
	DEZ(12, "Dezembro")
	;

	private final Integer indice;
	private final String descricao;
	
	private MesDoAno(Integer i, String d) {
		this.indice = i;
		this.descricao = d;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getIndice() {
		return indice;
	}
	

}
