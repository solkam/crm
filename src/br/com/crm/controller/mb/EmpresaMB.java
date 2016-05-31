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
import br.com.crm.model.service.AreaInteresseService;
import br.com.crm.model.service.EmpresaService;
import br.com.crm.model.service.MaturidadeService;
import br.com.crm.model.service.ProfissaoService;

/**
 * Controller para UC Gerenciar Empresa
 * @author Solkam
 * @since 05 mai 2016
 */
@ManagedBean
@ViewScoped
public class EmpresaMB implements Serializable {

	@Inject EmpresaService service;
	
	@Inject AreaInteresseService areaInteressService;
	
	@Inject ProfissaoService profissaoService;
	
	@Inject MaturidadeService maturidadeService;
	
	
	@Inject SessionHolder sessionHolder;
	
	
	private Empresa empresa;
	
	private List<Empresa> empresas;
	
	
	//filtros
	private String filtroNome;
	private String filtroCnpj;
	
	//cargas iniciais automaticas
	private Boolean flagCarregarAreasInteresse = false;
	private Boolean flagCarregarProfissoes = false;
	private Boolean flagCarregarMaturidades = false;
	
	
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
		flagCarregarAreasInteresse = false;
		flagCarregarProfissoes = false;
		flagCarregarMaturidades = false;
	}
	
	public void gerenciar(Empresa empresaSelecionada) {
		this.empresa = empresaSelecionada;
		recarregar();
	}
	
	public void salvar() {
		empresa = service.salvarEmpresa(empresa, sessionHolder.getUsuario() );
		JSFUtil.addInfoMessage("Empresa salva com sucesso");
		initEmpresas();;
		manejarCargaAreasInteresse();
		manejarCargaProfissoes();
		manejarCargaMaturidades();
		recarregar();
	}
	
	
	private void manejarCargaAreasInteresse() {
		if (flagCarregarAreasInteresse) {
			areaInteressService.carregarAreasInteresseParaEmpresa(empresa, sessionHolder.getUsuario() );
			JSFUtil.addInfoMessage("Carga inicial de Áreas de interesse realizada com sucesso");
		}
		//carga é feita somente uma vez;
		flagCarregarAreasInteresse = false;
	}

	
	private void manejarCargaProfissoes() {
		if (flagCarregarProfissoes) {
			profissaoService.carregarProfissoesParaEmpresa(empresa, sessionHolder.getUsuario() );
			JSFUtil.addInfoMessage("Carga inicial de Profissões realizada com sucesso");
		}
		//carga é feita somente uma vez
		flagCarregarProfissoes = false;
	}


	private void manejarCargaMaturidades() {
		if (flagCarregarMaturidades) {
			maturidadeService.carregarMaturidadesParaEmpresa(empresa, sessionHolder.getUsuario() );
			JSFUtil.addInfoMessage("Carga inicial de Maturidades realizada com sucesso");
		}
		//carrga deve ser realizada somente uma vez
		flagCarregarMaturidades = false;
	}

	
	
	public void remover() {
		service.removerEmpresa(empresa);
		initEmpresas();
		JSFUtil.addInfoMessage("Empresa removida");
	}


	
	//util...
	private void recarregar() {
		empresa = service.recarregarEmpresa(empresa);
	}


	//acessores...
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
	public Boolean getFlagCarregarAreasInteresse() {
		return flagCarregarAreasInteresse;
	}
	public void setFlagCarregarAreasInteresse(Boolean flagCarregarAreasInteresse) {
		this.flagCarregarAreasInteresse = flagCarregarAreasInteresse;
	}
	public Boolean getFlagCarregarProfissoes() {
		return flagCarregarProfissoes;
	}
	public void setFlagCarregarProfissoes(Boolean flagCarregarProfissoes) {
		this.flagCarregarProfissoes = flagCarregarProfissoes;
	}
	public Boolean getFlagCarregarMaturidades() {
		return flagCarregarMaturidades;
	}
	public void setFlagCarregarMaturidades(Boolean flagCarregarMaturidades) {
		this.flagCarregarMaturidades = flagCarregarMaturidades;
	}
}

