package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.chart.PieChartModel;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Campanha;
import br.com.crm.model.entity.InteracaoCampanha;
import br.com.crm.model.entity.InteracaoHumor;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.service.AcessoService;
import br.com.crm.model.service.CampanhaService;
import br.com.crm.model.service.PessoaService;
import br.com.crm.model.service.RelatorioService;

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
	private List<InteracaoHumor> filtroHumores;
	
	
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
																			,filtroHumores
																			);
		JSFUtil.addMessageAboutResult( interacoes );
		construirGraficos();
	}

	
	//graficos
	private PieChartModel chartModelCampanha;
	private PieChartModel chartModelResponsavel;
	private PieChartModel chartModelPessoa;
	private PieChartModel chartModelHumor;
	
	
	private void construirGraficos() {
		Map<String, Number> mapaCampanha = new TreeMap<>();
		Map<String, Number> mapaResponsavel = new TreeMap<>();
		Map<String, Number> mapaPessoa = new TreeMap<>();
		Map<String, Number> mapaHumor = new TreeMap<>();
		
		Number total = null;
		for (InteracaoCampanha interacaoVar : interacoes) {
			//campanha
			String nomeCampanha = extrairNomeCampanha(interacaoVar);
			total = mapaCampanha.get(nomeCampanha);
			mapaCampanha.put(nomeCampanha, increaseTotal(total));

			//responsavel
			String nomeResponsavel = extrairNomeResponsavel( interacaoVar );
			total = mapaResponsavel.get(nomeResponsavel);
			mapaResponsavel.put(nomeResponsavel, increaseTotal(total) );
			
			//pessoa
			String nomePessoa = extrairNomePessoa(interacaoVar);
			total = mapaPessoa.get(nomePessoa);
			mapaPessoa.put(nomePessoa, increaseTotal(total));
			
			//humor
			String descHumor = extrairDescHumor(interacaoVar);
			total = mapaHumor.get(descHumor);
			mapaHumor.put(descHumor, increaseTotal(total));
		}

		chartModelResponsavel = construirPieCharModelDefault(mapaResponsavel);
		chartModelPessoa = construirPieCharModelDefault(mapaPessoa);
		chartModelCampanha = construirPieCharModelDefault(mapaCampanha);
		chartModelHumor = construirPieCharModelDefault(mapaHumor);
	}
	
	
	private String extrairDescHumor(InteracaoCampanha interacaoVar) {
		return interacaoVar.getHumor().name();
	}


	private String extrairNomeCampanha(InteracaoCampanha interacaoVar) {
		return interacaoVar.getCampanha().getDescricao();
	}


	private String extrairNomePessoa(InteracaoCampanha interacaoVar) {
		return interacaoVar.getPessoa().getNomeCompleto();
	}


	private String extrairNomeResponsavel(InteracaoCampanha interacaoVar) {
		return interacaoVar.getResponsavel().getDescricaoCompleta();
	}


	private Number increaseTotal(Number total) {
		if (total==null) {
			return 1;
		} else {
			return total.longValue() + 1;
		}
	}
	
	
	private PieChartModel construirPieCharModelDefault(Map<String, Number> mapa) {
		PieChartModel pieChartModel = new PieChartModel(mapa);
//		pieChartModel.setLegendPosition("s");
		pieChartModel.setShowDataLabels(true);
		pieChartModel.setSliceMargin(3);
		pieChartModel.setShadow(false);
		pieChartModel.setDiameter(200);
		return pieChartModel;
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
	public List<InteracaoHumor> getFiltroHumores() {
		return filtroHumores;
	}
	public void setFiltroHumores(List<InteracaoHumor> filtroHumores) {
		this.filtroHumores = filtroHumores;
	}


	public PieChartModel getChartModelResponsavel() {
		return chartModelResponsavel;
	}
	public PieChartModel getChartModelPessoa() {
		return chartModelPessoa;
	}
	public PieChartModel getChartModelCampanha() {
		return chartModelCampanha;
	}
	public PieChartModel getChartModelHumor() {
		return chartModelHumor;
	}
	
}
