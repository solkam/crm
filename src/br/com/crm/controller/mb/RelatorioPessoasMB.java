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
import br.com.crm.model.entity.Genero;
import br.com.crm.model.entity.Maturidade;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.Profissao;
import br.com.crm.model.service.AreaInteresseService;
import br.com.crm.model.service.MaturidadeService;
import br.com.crm.model.service.ProfissaoService;
import br.com.crm.model.service.RelatorioService;

/**
 * Controller para UC Consultar Relatório de Pessoas
 * @author Solkam
 * @since 11 mai 2016
 */
@ManagedBean(name="rPessoaMB")
@ViewScoped
public class RelatorioPessoasMB implements Serializable {
	
	@Inject RelatorioService service;
	
	@Inject AreaInteresseService areaInteresseService;
	@Inject ProfissaoService profissaoService;
	@Inject MaturidadeService maturidadeService;
	
	@Inject SessionHolder sessionHolder;
	
	//combo
	private List<AreaInteresse> comboAreasInteresse;
	private List<Profissao> comboProfissoes;
	private List<Maturidade> comboMaturidades;
	
	//filtros
	private Integer filtroNascimentoDia;//nao será usado a principio
	private Integer filtroNascimentoMes;
	private Integer filtroNascimentoAno;
	private Genero filtroGenero;
	private String filtroCidade;
	private String filtroUF;
	private List<AreaInteresse> filtroAreasInteresse;
	private List<Profissao> filtroProfissoes;
	private String filtroIndicadoPor;
	private List<Maturidade> filtroMaturidades;
	
	
	//resultado
	private List<Pessoa> pessoas;
	
	@PostConstruct
	void init() {
		populateComboAreasInteresse();
		populateComboProfissoes();
		populateComboMaturidades();
	}
	

	private void populateComboAreasInteresse() {
		comboAreasInteresse = areaInteresseService.pesquisarAreaInteresseAtiva(sessionHolder.getEmpresa());
	}
	
	private void populateComboProfissoes() {
		comboProfissoes = profissaoService.pesquisarProfissaoAtiva( sessionHolder.getEmpresa() );
	}

	
	private void populateComboMaturidades() {
		comboMaturidades = maturidadeService.pesquisarMaturidade( sessionHolder.getEmpresa() );
	}
	

	//actions...
	public void search() {
		pessoas = service.pesquisarPessoaPelosFiltros(filtroNascimentoDia
													, filtroNascimentoMes
													, filtroNascimentoAno
													, filtroGenero
													, filtroCidade
													, filtroUF
													, filtroAreasInteresse
													, filtroProfissoes
													, filtroIndicadoPor
													, filtroMaturidades
													);
		JSFUtil.addMessageAboutResult(pessoas);
	}


	//acessores...
	private static final long serialVersionUID = -4564454271538398097L;

	public Integer getFiltroNascimentoDia() {
		return filtroNascimentoDia;
	}


	public void setFiltroNascimentoDia(Integer filtroNascimentoDia) {
		this.filtroNascimentoDia = filtroNascimentoDia;
	}


	public Integer getFiltroNascimentoMes() {
		return filtroNascimentoMes;
	}


	public void setFiltroNascimentoMes(Integer filtroNascimentoMes) {
		this.filtroNascimentoMes = filtroNascimentoMes;
	}


	public Integer getFiltroNascimentoAno() {
		return filtroNascimentoAno;
	}


	public void setFiltroNascimentoAno(Integer filtroNascimentoAno) {
		this.filtroNascimentoAno = filtroNascimentoAno;
	}


	public Genero getFiltroGenero() {
		return filtroGenero;
	}


	public void setFiltroGenero(Genero filtroGenero) {
		this.filtroGenero = filtroGenero;
	}


	public String getFiltroCidade() {
		return filtroCidade;
	}


	public void setFiltroCidade(String filtroCidade) {
		this.filtroCidade = filtroCidade;
	}


	public String getFiltroUF() {
		return filtroUF;
	}


	public void setFiltroUF(String filtroUF) {
		this.filtroUF = filtroUF;
	}


	public List<AreaInteresse> getFiltroAreasInteresse() {
		return filtroAreasInteresse;
	}


	public void setFiltroAreasInteresse(List<AreaInteresse> filtroAreasInteresse) {
		this.filtroAreasInteresse = filtroAreasInteresse;
	}


	public List<Profissao> getFiltroProfissoes() {
		return filtroProfissoes;
	}


	public void setFiltroProfissoes(List<Profissao> filtroProfissoes) {
		this.filtroProfissoes = filtroProfissoes;
	}


	public String getFiltroIndicadoPor() {
		return filtroIndicadoPor;
	}


	public void setFiltroIndicadoPor(String filtroIndicadoPor) {
		this.filtroIndicadoPor = filtroIndicadoPor;
	}


	public List<Maturidade> getFiltroMaturidades() {
		return filtroMaturidades;
	}


	public void setFiltroMaturidades(List<Maturidade> filtroMaturidades) {
		this.filtroMaturidades = filtroMaturidades;
	}


	public List<AreaInteresse> getComboAreasInteresse() {
		return comboAreasInteresse;
	}


	public List<Profissao> getComboProfissoes() {
		return comboProfissoes;
	}


	public List<Maturidade> getComboMaturidades() {
		return comboMaturidades;
	}


	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
	
	
}
