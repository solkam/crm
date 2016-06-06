package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.CategoriaProduto;
import br.com.crm.model.service.ProdutoService;

/**
 * Controller para UC Gerenciar Categorias de Produtos
 * @author Solkam
 * @since 06 jun 2016
 */
@ManagedBean
@ViewScoped
public class CategoriaProdutoMB implements Serializable {

	@Inject ProdutoService service;
	
	@Inject SessionHolder sessionHolder;
	
	private CategoriaProduto categoria;
	
	private List<CategoriaProduto> categorias;
	
	
	@PostConstruct void init() {
		pesquisar();
	}

	private void initCategorias() {
		categorias = service.pesquisarCategoriaProdutoPelosFiltros( sessionHolder.getEmpresa() );
	}

	
	public void pesquisar() {
		initCategorias();
		JSFUtil.addMessageAboutResult(categorias);
	}
	
	public void novo() {
		categoria = new CategoriaProduto();
	}
	
	public void gerenciar(CategoriaProduto categoriaSelecionada) {
		this.categoria = categoriaSelecionada;
	}
	
	public void salvar() {
		categoria = service.salvarCategoriaProduto(categoria, sessionHolder.getUsuario());
		initCategorias();
		JSFUtil.addInfoMessage("Categoria de produto salva com sucesso");
	}
	
	public void remover() {
		service.removerCategoriaProduto(categoria);
		initCategorias();
		JSFUtil.addInfoMessage("Categoria removida");
	}


	
	//acessores..
	private static final long serialVersionUID = 1924464657740133911L;
	public CategoriaProduto getCategoria() {
		return categoria;
	}


	public void setCategoria(CategoriaProduto categoria) {
		this.categoria = categoria;
	}


	public List<CategoriaProduto> getCategorias() {
		return categorias;
	}
	
	

}
