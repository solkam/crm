package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Campanha;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.Produto;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.service.CampanhaService;
import br.com.crm.model.service.AcessoService;

/**
 * Controller para UC Gerenciar Campanha
 * @author Solkam
 * @since 12 mai 2016
 */
@ManagedBean
@ViewScoped
public class CampanhaMB implements Serializable {
	
	@Inject CampanhaService service;
	
	@Inject AcessoService usuarioService;
	
	@Inject SessionHolder sessionHolder;
	
	
	private Campanha campanha;
	
	private List<Campanha> campanhas;
	
	
	//filtros
	private Boolean filtroFlagAtivo=true;
	private String filtroDescricao;
	
	//combo
	private List<Usuario> comboResponsaveis;
	
	
	
	
	@PostConstruct void init() {
		pesquisar();
		initComboResponsaveis();
	}

	private void initComboResponsaveis() {
		comboResponsaveis = usuarioService.pesquisarUsuarioPeloEmpresa( sessionHolder.getEmpresa() );
	}

	private void initCampanhas() {
		campanhas = service.pesquisarCampanhaPelosFiltros(sessionHolder.getEmpresa()
				 										, filtroFlagAtivo
				 										, filtroDescricao
				 										);
	}
	
	
	public void pesquisar() {
		initCampanhas();
		JSFUtil.addMessageAboutResult(campanhas);
	}
	
	public void novo() {
		campanha = new Campanha();
		campanha.setDataInicio( new Date() );
	}
	
	public void salvar() {
		campanha = service.salvarCampanha(campanha, sessionHolder.getUsuario() );
		recarregar();
		initCampanhas();
		JSFUtil.addInfoMessage("Campanha salva com sucesso");
	}
	

	public void gerenciar(Campanha campanhaSelecionada) {
		this.campanha = campanhaSelecionada;
		recarregar();
	}
	
	
	public void remover() {
		service.removerCampanha(campanha);
		initCampanhas();
	}
	
	

	
	//responsaveis
	public void salvarResponsaveis() {
		campanha = service.salvarCampanha(campanha, sessionHolder.getUsuario() );
		recarregar();
		JSFUtil.addInfoMessage("Responsáveis salvos com sucesso");
	}

	
	//pessoas
	public void removerPessoa(Pessoa pessoaSelecionada) {
		service.removerPessoaDaCampanha(campanha, pessoaSelecionada);
		recarregar();
		JSFUtil.addInfoMessage("Pessoa removida da campanha");
	}
	
	
	
	//produtos
	public void removerProduto(Produto produtoSelecionado) {
		service.removerProdutoDaCampanha(campanha, produtoSelecionado);
		recarregar();
		JSFUtil.addInfoMessage("Produto removido da campanha");
	}
	
	

	//util...
	private void recarregar() {
		campanha = service.recarregarCampanha(campanha);
		//1.responsaveis
		Set<Usuario> responsaveisAux = new TreeSet<>();
		responsaveisAux.addAll( campanha.getResponsaveis() );
		campanha.setResponsaveis( responsaveisAux );
		//2.pessoas
		Set<Pessoa> pessoasAux = new TreeSet<>();
		pessoasAux.addAll( campanha.getPessoas() );
		campanha.setPessoas( pessoasAux );
		//3.produtos
		Set<Produto> produtosAux = new TreeSet<>();
		produtosAux.addAll( campanha.getProdutos() );
		campanha.setProdutos( produtosAux );
	}
	
	
	
	//acesoress...
	private static final long serialVersionUID = 778432619395246842L;
	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Boolean getFiltroFlagAtivo() {
		return filtroFlagAtivo;
	}

	public void setFiltroFlagAtivo(Boolean filtroFlagAtivo) {
		this.filtroFlagAtivo = filtroFlagAtivo;
	}

	public String getFiltroDescricao() {
		return filtroDescricao;
	}

	public void setFiltroDescricao(String filtroDescricao) {
		this.filtroDescricao = filtroDescricao;
	}

	public List<Campanha> getCampanhas() {
		return campanhas;
	}

	public List<Usuario> getComboResponsaveis() {
		return comboResponsaveis;
	}
	
	
}
