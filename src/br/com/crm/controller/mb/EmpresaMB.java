package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;


import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Empresa;
import br.com.crm.model.service.EmpresaService;

/**
 * Controller para UC Gerenciar Empresa
 * @author Solkam
 * @since 05 mai 2016
 */
@ManagedBean
@ViewScoped
public class EmpresaMB implements Serializable {

	@Inject EmpresaService service;
	
	@Inject SessionHolder sessionHolder;
	
	
	private Empresa empresa;
	
	private List<Empresa> empresas;
	
	
	//filtros
	private String filtroNome;
	private String filtroCnpj;
	
	
	@PostConstruct void init() {
		pesquisar();
	}
	
	
	private void initEmpresas() {
		empresas = service.pesquisarEmpresaPorFiltro(filtroNome, filtroCnpj);
	}
	

	public void pesquisar() {
		initEmpresas();
		JSFUtil.addMessageAboutResult( empresas );
	}
	
	public void novo() {
		empresa = new Empresa();
	}
	
	public void gerenciar(Empresa empresaSelecionada) {
		this.empresa = empresaSelecionada;
	}
	
	public void salvar() {
		service.salvarEmpresa(empresa, sessionHolder.getUsuario() );
		initEmpresas();;
		JSFUtil.addInfoMessage("Empresa salva com sucesso");
		
	}
	
	public void remover() {
		service.removerEmpresa(empresa);
		initEmpresas();
		JSFUtil.addInfoMessage("Empresa removida");
	}




	private static final long serialVersionUID = -1427342134463678828L;


	public Empresa getEmpresa() {
		return empresa;
	}




	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}




	public String getFiltroNome() {
		return filtroNome;
	}




	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}




	public String getFiltroCnpj() {
		return filtroCnpj;
	}




	public void setFiltroCnpj(String filtroCnpj) {
		this.filtroCnpj = filtroCnpj;
	}




	public List<Empresa> getEmpresas() {
		return empresas;
	}
	

}
