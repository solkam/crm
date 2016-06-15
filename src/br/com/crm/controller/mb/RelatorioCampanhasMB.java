package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Campanha;
import br.com.crm.model.entity.InteracaoCampanha;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.service.CampanhaService;
import br.com.crm.model.service.PessoaService;
import br.com.crm.model.service.RelatorioService;
import br.com.crm.model.service.AcessoService;

/**
 * Controller para UC Consultar Relatório de Campanhas
 * @author Solkam
 * @since 09 jun 2016
 */
@ManagedBean(name="rCampanhasMB")
@ViewScoped
public class RelatorioCampanhasMB implements Serializable {
	
	@Inject RelatorioService relatorioService;
	
	@Inject SessionHolder sessionHolder;

	
	@Inject CampanhaService campanhaService;
	@Inject AcessoService usuarioService;
	@Inject PessoaService pessoaService;
	
	
	//combos
	private List<Campanha> comboCampanhas;
	private List<Usuario> comboResponsaveis;
	private List<Pessoa> comboPessoas;
	
	//filtros
	private List<Campanha> filtroCampanhas;
	private List<Usuario> filtroResponsaveis;
	private List<Pessoa> filtroPessoas;
	
	
	//resultado
	private List<InteracaoCampanha> interacoes;
	
	
	//init
	@PostConstruct void init() {
		initComboCampanhas();
		initComboResponsaveis();
		initComboPessoas();
	}


	private void initComboCampanhas() {
		comboCampanhas = campanhaService.pesquisarCampanhaPelosFiltros(sessionHolder.getEmpresa(), null, null);
	}
	
	private void initComboResponsaveis() {
		comboResponsaveis = usuarioService.pesquisarUsuarioPeloEmpresa(sessionHolder.getEmpresa());
	}
	
	private void initComboPessoas() {
		comboPessoas = pessoaService.pesquisarPessoaPelosFiltros(sessionHolder.getEmpresa(), null, null, null);
	}
	
	
	//actions...
	public void pesquisar() {
		interacoes = relatorioService.pesquisarInteracaoCampanhaPeloFiltros( sessionHolder.getEmpresa()
																			,filtroCampanhas
																			,filtroResponsaveis
																			,filtroPessoas
																			);
		JSFUtil.addMessageAboutResult( interacoes );
	}

	
	//acessores...
	private static final long serialVersionUID = 6659472966851920991L;
	public List<Campanha> getFiltroCampanhas() {
		return filtroCampanhas;
	}
	public void setFiltroCampanhas(List<Campanha> filtroCampanhas) {
		this.filtroCampanhas = filtroCampanhas;
	}
	public List<Campanha> getComboCampanhas() {
		return comboCampanhas;
	}
	public List<InteracaoCampanha> getInteracoes() {
		return interacoes;
	}
	public List<Usuario> getFiltroResponsaveis() {
		return filtroResponsaveis;
	}
	public void setFiltroResponsaveis(List<Usuario> filtroResponsaveis) {
		this.filtroResponsaveis = filtroResponsaveis;
	}
	public List<Pessoa> getFiltroPessoas() {
		return filtroPessoas;
	}
	public void setFiltroPessoas(List<Pessoa> filtroPessoas) {
		this.filtroPessoas = filtroPessoas;
	}
	public List<Usuario> getComboResponsaveis() {
		return comboResponsaveis;
	}
	public List<Pessoa> getComboPessoas() {
		return comboPessoas;
	}

}
