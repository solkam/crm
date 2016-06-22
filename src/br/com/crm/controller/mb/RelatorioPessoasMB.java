package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.exportador.ExcelExporter;
import br.com.crm.controller.exportador.RelatorioPessoasExcelExporter;
import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.AreaInteresse;
import br.com.crm.model.entity.Campanha;
import br.com.crm.model.entity.CategoriaProduto;
import br.com.crm.model.entity.Genero;
import br.com.crm.model.entity.Maturidade;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.Produto;
import br.com.crm.model.entity.Profissao;
import br.com.crm.model.service.AreaInteresseService;
import br.com.crm.model.service.CampanhaService;
import br.com.crm.model.service.MaturidadeService;
import br.com.crm.model.service.ProdutoService;
import br.com.crm.model.service.ProfissaoService;
import br.com.crm.model.service.RelatorioService;
import br.com.crm.model.util.DateUtil;

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
	@Inject CampanhaService campanhaService;
	@Inject ProdutoService produtoService;
	
	@Inject SessionHolder sessionHolder;
	
	//combo
	private List<AreaInteresse> comboAreasInteresse;
	private List<Profissao> comboProfissoes;
	private List<Maturidade> comboMaturidades;
	private List<Campanha> comboCampanhas;//ativas e dentro do prazo
	private List<CategoriaProduto> comboCategorias;
	private List<Produto> comboProdutos;
	
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
	private List<CategoriaProduto> filtroCategorias;
	private List<CategoriaProduto> filtroNotCategorias;//categorias não adiquiridas
	private List<Produto> filtroProdutos;
	private List<Produto> filtroNotProdutos;//produtos não adquiridos em vendas
	
	
	//resultado
	private List<Pessoa> pessoas;
	
	
	private List<Pessoa> pessoasSelecionadas;
	
	private Campanha campanhaSelecionada = new Campanha();
	
	
	@PostConstruct
	void init() {
		initComboAreasInteresse();
		initComboProfissoes();
		initComboMaturidades();
		initComboCampanhas();
		initComboCategorias();
		initComboProdutos();
	}
	



	private void initComboAreasInteresse() {
		comboAreasInteresse = areaInteresseService.pesquisarAreaInteresseAtiva(sessionHolder.getEmpresa());
	}
	
	private void initComboProfissoes() {
		comboProfissoes = profissaoService.pesquisarProfissaoAtiva( sessionHolder.getEmpresa() );
	}

	private void initComboMaturidades() {
		comboMaturidades = maturidadeService.pesquisarMaturidade( sessionHolder.getEmpresa() );
	}
	
	private void initComboCampanhas() {
		comboCampanhas = campanhaService.pesquisarCampanhaAtivaEDentroDoPrazo(sessionHolder.getEmpresa());
	}
	
	private void initComboCategorias() {
		comboCategorias = produtoService.pesquisarCategoriaProdutoPelosFiltros(sessionHolder.getEmpresa(), true);
	}
	
	private void initComboProdutos() {
		comboProdutos = produtoService.pesquisarProdutoAtivo(sessionHolder.getEmpresa() );
	}
	
	

	//actions...
	public void search() {
		pessoas = service.pesquisarPessoaPelosFiltros(sessionHolder.getEmpresa()
				                                    , filtroNascimentoDia
													, filtroNascimentoMes
													, filtroNascimentoAno
													, filtroGenero
													, filtroCidade
													, filtroUF
													, filtroAreasInteresse
													, filtroProfissoes
													, filtroIndicadoPor
													, filtroMaturidades
													, filtroCategorias
													, filtroNotCategorias
													, filtroProdutos
													, filtroNotProdutos
													);
		JSFUtil.addMessageAboutResult(pessoas);
	}
	
	
	//campanhas...
	public void confirmarPessoasACampanha() {
		campanhaService.adicionarPessoasACampanha(pessoasSelecionadas, campanhaSelecionada);
		JSFUtil.addInfoMessage("Pessoas adicionadas com sucesso");
	}
	
	
	//exportador
	public void exportarParaExcel() {
		ExcelExporter exportador = new RelatorioPessoasExcelExporter(pessoas);
		exportador.export(getTitulosRelatorio(), getNomeArquivo() );
	}
	
	private String[] getTitulosRelatorio() {
		String t1 = "Relatório de Pessoas";
		return new String[] {t1};
	}

	private String getNomeArquivo() {
		return String.format("Relatório_Pessoas_%s", DateUtil.getDateForFilename() );
	}



	//acessores...
	private static final long serialVersionUID = -4564454371538398097L;

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
	public List<Pessoa> getPessoasSelecionadas() {
		return pessoasSelecionadas;
	}
	public void setPessoasSelecionadas(List<Pessoa> pessoasSelecionadas) {
		this.pessoasSelecionadas = pessoasSelecionadas;
	}
	public List<Campanha> getComboCampanhas() {
		return comboCampanhas;
	}
	public Campanha getCampanhaSelecionada() {
		return campanhaSelecionada;
	}
	public void setCampanhaSelecionada(Campanha campanhaSelecionada) {
		this.campanhaSelecionada = campanhaSelecionada;
	}
	public List<CategoriaProduto> getFiltroCategorias() {
		return filtroCategorias;
	}
	public void setFiltroCategorias(List<CategoriaProduto> filtroCategorias) {
		this.filtroCategorias = filtroCategorias;
	}
	public List<CategoriaProduto> getFiltroNotCategorias() {
		return filtroNotCategorias;
	}
	public void setFiltroNotCategorias(List<CategoriaProduto> filtroNotCategorias) {
		this.filtroNotCategorias = filtroNotCategorias;
	}
	public List<CategoriaProduto> getComboCategorias() {
		return comboCategorias;
	}
	public List<Produto> getFiltroProdutos() {
		return filtroProdutos;
	}
	public void setFiltroProdutos(List<Produto> filtroProdutos) {
		this.filtroProdutos = filtroProdutos;
	}
	public List<Produto> getFiltroNotProdutos() {
		return filtroNotProdutos;
	}
	public void setFiltroNotProdutos(List<Produto> filtroNotProdutos) {
		this.filtroNotProdutos = filtroNotProdutos;
	}
	public List<Produto> getComboProdutos() {
		return comboProdutos;
	}
	
}
