package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.Produto;
import br.com.crm.model.entity.Venda;
import br.com.crm.model.service.PessoaService;
import br.com.crm.model.service.ProdutoService;
import br.com.crm.model.service.VendaService;


/**
 * Controller para UC Gerenciar Vendas
 * @author Solkam
 * @since 03 jun 2016
 */
@ManagedBean
@ViewScoped
public class VendaMB implements Serializable {

	@Inject VendaService service;
	
	@Inject ProdutoService produtoService;
	
	@Inject PessoaService pessoaService; 
	
	
	@Inject SessionHolder sessionHolder;
	
	
	private Venda venda;
	
	private List<Venda> vendas;
	
	
	//combos
	private List<Produto> comboProdutos;
	private List<Pessoa> comboPessoas;
	
	
	
	@PostConstruct void init() {
		initVendas();
		initComboProdutos();
		initComboPessoas();
	}


	private void initComboProdutos() {
		comboProdutos = produtoService.pesquisarProduto( sessionHolder.getEmpresa() );
	}
	
	private void initComboPessoas() {
		comboPessoas = pessoaService.pesquisarPessoaPelosFiltros(sessionHolder.getEmpresa(), null, null, null);
	}


	private void initVendas() {
		vendas = service.pesquisarVendaPelosFiltros( sessionHolder.getEmpresa() );
	}
	
	
	public void pesquisar() {
		initVendas();
		JSFUtil.addMessageAboutResult( vendas );
	}
	
	public void novo() {
		venda = new Venda();
		venda.setPessoa( new Pessoa() );
		venda.setProduto( new Produto() );
	}
	
	public void gerenciar(Venda vendaSelecionada) {
		this.venda = vendaSelecionada;
	}
	
	public void salvar() {
		venda = service.salvarVenda(venda, sessionHolder.getUsuario() );
		initVendas();
		JSFUtil.addInfoMessage("Venda salva com sucesso");
	}
	
	public void remover() {
		service.removerVenda(venda);
		initVendas();
		JSFUtil.addInfoMessage("Venda removida");
	}


	//acessores...
	private static final long serialVersionUID = -4560071859727216753L;
	public Venda getVenda() {
		return venda;
	}
	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	public List<Venda> getVendas() {
		return vendas;
	}


	public List<Produto> getComboProdutos() {
		return comboProdutos;
	}


	public List<Pessoa> getComboPessoas() {
		return comboPessoas;
	}
	
}