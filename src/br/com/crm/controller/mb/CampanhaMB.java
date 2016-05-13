package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Campanha;
import br.com.crm.model.service.CampanhaService;

/**
 * Controller para UC Gerenciar Campanha
 * @author Solkam
 * @since 12 mai 2016
 */
@ManagedBean
@ViewScoped
public class CampanhaMB implements Serializable {
	
	@Inject CampanhaService service;
	
	@Inject SessionHolder sessionHolder;
	
	
	private Campanha campanha;
	
	private List<Campanha> campanhas;
	
	
	//filtros
	private Boolean filtroFlagAtivo;
	private String filtroDescricao;
	
	@PostConstruct void init() {
		pesquisar();
	}

	private void initCampanhas() {
		campanhas = service.pesquisarCampanhaPelosFiltros(filtroFlagAtivo, filtroDescricao);
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
	
	
	private void recarregar() {
		campanha = service.recarregarCampanha(campanha);
	}

	
	//pessoas
	
	
	//produtos
	
	
	//responsaveis
	
	
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
	
}
