package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Produto;
import br.com.crm.model.service.ProdutoService;


/**
 * Controller para UC Gerenciar Produtos
 * @author Solkam
 * @since 10 mai 2016
 */
@ManagedBean
@ViewScoped
public class ProdutoMB implements Serializable {
	
	@Inject ProdutoService service;
	
	@Inject SessionHolder sessionHolder;
	
	
	private List<Produto> produtos;
	
	private Produto produto;

	
	//filtros
	private String filtroDescricao;
	private Boolean filtroFlagAtivo = true;
	
	
	//inits
	
	@PostConstruct void init() {
		pesquisar();
	}
	
	private void initProdutos() {
		produtos = service.pesquisarProdutoPelosFiltros(sessionHolder.getEmpresa(), filtroFlagAtivo, filtroDescricao);
	}
	
	
	//actions...
	
	public void pesquisar() {
		initProdutos();
		JSFUtil.addMessageAboutResult(produtos);
	}
	
	
	public void novo() {
		produto = new Produto();
	}
	
	
	
	public void gerenciar(Produto produtoSelecionado) {
		this.produto = produtoSelecionado;
		recarregar();
	}
	
	
	public void salvar() {
		produto = service.salvarProduto(produto, sessionHolder.getUsuario() );
		recarregar();
		initProdutos();
		JSFUtil.addInfoMessage("Produto salvo com sucesso");
	}
	
	
	public void remover() {
		service.removerProduto(produto);
		initProdutos();
		JSFUtil.addInfoMessage("Produto removido");
	}


	
	//util
	private void recarregar() {
		produto = service.recarregarProduto(produto);
	}



	
	//acessores
	private static final long serialVersionUID = -3582748635073006379L;


	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Boolean getFiltroFlagAtivo() {
		return filtroFlagAtivo;
	}

	public void setFiltroFlagAtivo(Boolean filtroFlagAtivo) {
		this.filtroFlagAtivo = filtroFlagAtivo;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public String getFiltroDescricao() {
		return filtroDescricao;
	}

	public void setFiltroDescricao(String filtroDescricao) {
		this.filtroDescricao = filtroDescricao;
	}

	
}
