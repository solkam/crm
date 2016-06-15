package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Funcionalidade;
import br.com.crm.model.entity.Perfil;
import br.com.crm.model.service.AcessoService;

/**
 * Controller para UC Gerenciar Perfil
 * @author Solkam
 * @since 14 jun 2016
 */
@ManagedBean
@ViewScoped
public class PerfilMB implements Serializable {

	@Inject AcessoService service;
	
	@Inject SessionHolder sessionHolder;

	
	private Perfil perfil;
	
	private List<Perfil> perfils;
	
	
	@PostConstruct void init() {
		initPerfils();
	}


	private void initPerfils() {
		perfils = service.pesquisarPerfilPeloFiltro(null);
	}
	
	
	public void pesquisar() {
		initPerfils();
		JSFUtil.addMessageAboutResult(perfils);
	}
	
	public void novo() {
		perfil = new Perfil();
	}
	
	public void salvar() {
		service.salvarPerfil( perfil );
		recarregar();
		initPerfils();
		JSFUtil.addInfoMessage("Perfil salvo com sucesso");
	}
	
	public void gerenciar(Perfil perfilSelecionado) {
		this.perfil = perfilSelecionado;
		recarregar();
	}

	public void remover() {
		service.removerPerfil(perfil);
		initPerfils();
		JSFUtil.addInfoMessage("Perfil removido");
	}
	
	
	private void recarregar() {
		perfil = service.recarregarPerfil(perfil);
		//para evitar LIE
		List<Funcionalidade> funcionalidades = new ArrayList<>();
		funcionalidades.addAll( perfil.getFuncionalidades() );
		perfil.setFuncionalidades( funcionalidades );
	}


	//acessores...
	private static final long serialVersionUID = 3996190110031042300L;
	public Perfil getPerfil() {
		return perfil;
	}


	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}


	public List<Perfil> getPerfils() {
		return perfils;
	}

}
