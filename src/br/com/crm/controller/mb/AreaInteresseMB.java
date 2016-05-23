package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.AreaInteresse;
import br.com.crm.model.service.AreaInteresseService;

/**
 * Controller para UC Gerenciar Área de Interesse
 * @author Solkam
 * @since 26 JUN 2015
 */
@ManagedBean
@ViewScoped
public class AreaInteresseMB implements Serializable {

	@Inject AreaInteresseService service;
	
	@Inject SessionHolder sessionHolder;
	
	
	private AreaInteresse novaAreaInteresse;
	
	private List<AreaInteresse> areasInteresse;

	
	@PostConstruct void init() {
		novo();
		pesquisar();
	}
	
	private void initAreasInteresse() {
		areasInteresse = service.pesquisarAreaInteresse(sessionHolder.getEmpresa() );
	}
	
	
	private void novo() {
		novaAreaInteresse = new AreaInteresse();
	}
	
	
	public void pesquisar() {
		initAreasInteresse();
		JSFUtil.addMessageAboutResult(areasInteresse);
	}
	
	public void salvar() {
		service.salvarAreaInteresse(novaAreaInteresse, sessionHolder.getUsuario() );
		novo();
		initAreasInteresse();
		JSFUtil.addInfoMessage("Área de Interesse salva com sucesso");
	}
	
	public void salvarTudo() {
		for (AreaInteresse areaVar : areasInteresse) {
			service.salvarAreaInteresse(areaVar, sessionHolder.getUsuario() );
		}
		initAreasInteresse();
		JSFUtil.addInfoMessage("Todas as áreas de interesse salvas com sucesso");
	}
	
	public void remover(AreaInteresse areaSelecionada) {
		service.removerAreaInteresse(areaSelecionada);
		initAreasInteresse();
		String descricao = areaSelecionada.getDescricao();
		JSFUtil.addInfoMessage(String.format("Área de Interesse [%s] removida", descricao ));
	}
	
	
	//acessores...
	private static final long serialVersionUID = -4405135060999549248L;


	public AreaInteresse getNovaAreaInteresse() {
		return novaAreaInteresse;
	}

	public void setNovaAreaInteresse(AreaInteresse novaAreaInteresse) {
		this.novaAreaInteresse = novaAreaInteresse;
	}

	public List<AreaInteresse> getAreasInteresse() {
		return areasInteresse;
	}

	
}
