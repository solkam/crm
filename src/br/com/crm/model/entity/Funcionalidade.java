package br.com.crm.model.entity;

/**
 * Funcionalidade do sistema acessiveis segundo o perfil do usuário
 * @author Solkam
 * @since 15 jun 2016
 */
public enum Funcionalidade {
	
	ACESSO("/paginas/acesso/usuario.xhtml", "icone_menu_acesso_96.png")
	,
	PESSOA("/paginas/pessoa/pessoa.xhtml", "icone_menu_pessoa_96.png")
	,
	EMPRESA("/paginas/empresa/empresa.xhtml", "icone_menu_empresa_96.png")
	,
	PRODUTO("/paginas/produto/produto.xhtml", "icone_menu_produto_96.png")
	,
	VENDA("/paginas/venda/venda.xhtml", "icone_menu_venda_96.png")
	,
	RELATORIO("/paginas/relatorio/relatorio-pessoa.xhtml", "icone_menu_relatorio_96.png")
	,
	CAMPANHA("/paginas/campanha/campanha.xhtml", "icone_menu_campanha_96.png")
	,
	MINHAS_CAMPANHAS("/paginas/minha-campanha/minha-campanha.xhtml", "icone_menu_minha_campanha_96.png")
	,
	AJUSTES("/paginas/area-interesse/area-interesse.xhtml", "icone_menu_ajuste_96.png")
	;
	
	
	private final String pagina;
	private final String icone;
	
	
	private Funcionalidade(String pagina, String icone) {
		this.pagina = pagina;
		this.icone = icone;
	}

	public String getPagina() {
		return pagina;
	}

	public String getIcone() {
		return icone;
	}

}
