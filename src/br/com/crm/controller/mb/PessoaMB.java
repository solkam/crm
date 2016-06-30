package br.com.crm.controller.mb;

import java.io.IOException;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.ImageStreamUtil;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.AreaInteresse;
import br.com.crm.model.entity.Campanha;
import br.com.crm.model.entity.Empresa;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.PessoaCartaoNegocio;
import br.com.crm.model.entity.PessoaObservacao;
import br.com.crm.model.entity.Profissao;
import br.com.crm.model.service.AreaInteresseService;
import br.com.crm.model.service.CampanhaService;
import br.com.crm.model.service.PessoaService;
import br.com.crm.model.service.ProfissaoService;

/**
 * Controller para UC Gerenciar Pessoa
 * @author Solkam
 * @since 28 abr 2016
 */
@ManagedBean
@ViewScoped
public class PessoaMB implements Serializable {
	
	@Inject PessoaService pessoaService;
	
	@Inject AreaInteresseService areaInteresseService;
	
	@Inject ProfissaoService profissaoService;
	
	@Inject CampanhaService campanhaService;
	
	
	
	@Inject SessionHolder sessionHolder;

	
	private ImageStreamUtil imageStreamUtil = new ImageStreamUtil();
	
	private List<Pessoa> pessoas;
	
	private Pessoa pessoa;
	

	//filtros
	private String filtroNome;
	private String filtroEmail;
	private String filtroCidade;
	

	//inits..
	@PostConstruct void init() {
		pesquisar();
		initComboAreasInteresse();
		initComboProfissoes();
		initComboCampanhas();
		resetarObservacao();
	}
	

	private void initPessoas() {
		Empresa empresa = sessionHolder.getEmpresa();
		pessoas = pessoaService.pesquisarPessoaPelosFiltros(empresa, filtroNome, filtroEmail, filtroCidade);
	}
	
	private void initComboCampanhas() {
		comboCampanhas = campanhaService.pesquisarCampanhaAtivaEDentroDoPrazo(sessionHolder.getEmpresa());
	}
	
	
	
	//actions...
	public void novo() {
		pessoa = new Pessoa();
		
	}
	
	public void pesquisar() {
		initPessoas();
		JSFUtil.addMessageAboutResult(pessoas);
	}
	
	public void gerenciar(Pessoa selectedPessoa) {
		this.pessoa = selectedPessoa;
		recarregar();
	}
	
	public void salvar() {
		//validacoes
		pessoa.validarDataNascimento();
		pessoa.validarDocumentos();
		//salva
		pessoa = pessoaService.salvarPessoa(pessoa, sessionHolder.getUsuario() );
		//prepara exibicao
		initPessoas();
		recarregar();
		JSFUtil.addInfoMessage("Pessoa salvo com sucesso");
	}

	
	public void remover() {
		pessoaService.removerPessoa(pessoa);
		initPessoas();
		JSFUtil.addInfoMessage("Pessoa removida");
	}
	
	
	//areas de interesse
	private List<AreaInteresse> comboAreasInteresse;
	
	private void initComboAreasInteresse() {
		comboAreasInteresse = areaInteresseService.pesquisarAreaInteresseAtiva(sessionHolder.getEmpresa() );
	}
	
	public void salvarAreasInteresseDaPessoa() {
		pessoa = pessoaService.salvarPessoa(pessoa, sessionHolder.getUsuario() );
		recarregar();
		JSFUtil.addInfoMessage("Áreas de Interesse salvas com sucesso");
	}
	
	
	//profissoes
	private List<Profissao> comboProfissoes;

	private void initComboProfissoes() {
		comboProfissoes = profissaoService.pesquisarProfissaoAtiva( sessionHolder.getEmpresa() );
	}
	
	public void salvarProfissoesDaPessoa() {
		pessoa = pessoaService.salvarPessoa(pessoa, sessionHolder.getUsuario() );
		recarregar();
		JSFUtil.addInfoMessage("Profissões salvas com sucesso");
	}
	
	
	//observacoes
	private PessoaObservacao novaObservacao;
	
	private void resetarObservacao() {
		novaObservacao = new PessoaObservacao();
		novaObservacao.setResponsavel( sessionHolder.getUsuario().getDescricaoCompleta() );
	}
	
	public void salvarObservacao() {
		novaObservacao.setPessoa(pessoa);
		novaObservacao.setDataObservacao( new Date() );
		pessoaService.salvarPessoaObservacao(novaObservacao);
		resetarObservacao();
		recarregar();
		JSFUtil.addInfoMessage("Observação salva com sucesso");
	}
	
	public void removerObservacao(PessoaObservacao obsSelecionada) {
		pessoaService.removerPessoaObservacao(obsSelecionada);
		recarregar();
		JSFUtil.addInfoMessage("Observação removida");
	}
	
	
	//campanhas
	private List<Campanha> comboCampanhas;
	
	private Campanha campanhaSelecionada = new Campanha();
	
	public void confirmarPessoasParaCampanha() {
		campanhaService.adicionarPessoasACampanha(pessoas, campanhaSelecionada);
		JSFUtil.addInfoMessage("Pessoas adicionadas com sucesso");
	}

	
	
	
	
	//util
	private void recarregar() {
		pessoa = pessoaService.recarregarPessoa(pessoa); 
		
		//area de interesse (para evitar LIE)
		List<AreaInteresse> areas = new ArrayList<>();
		areas.addAll( pessoa.getAreasInteresse() );
		pessoa.setAreasInteresse( areas );
		
		//profissoes (para evitar LIE)
		List<Profissao> profissoes = new ArrayList<>();
		profissoes.addAll( pessoa.getProfissoes() );
		pessoa.setProfissoes( profissoes );
	}

	
	//autocomplete
	public List<Pessoa> completarPessoa(String frag) {
		List<Pessoa> pessoas = pessoaService.pesquisarPessoaPeloPrimeiroNomeOrSobreNomeOrCidade(sessionHolder.getEmpresa(), frag, frag);
		return pessoas;
	}
	
	
	
	/* ****
	 * FOTO
	 ******/
	/**
	 * Orquesta o upload da imagem
	 * @param event
	 * @throws IOException
	 */
	public void onFileUpload(FileUploadEvent event) throws IOException {
		redimImage(event);
		salvarImagemNoFS();
		salvarImagemNoDB();
	}
	
	
	/**
	 * Realiza alguns ajustes na imagem apos o upload como 
	 * redimensiona-la e gravar no disco.
	 * @param event
	 * @throws IOException
	 */
	private void redimImage(FileUploadEvent event) throws IOException {
		//1.extensao da imagem
		String imageExtension = imageStreamUtil.extractExtension( event.getFile().getFileName() );
		pessoa.setImagemExtensao(imageExtension);

		//2.conteudo binario da imagem
		InputStream imageInputStream = event.getFile().getInputstream();
		byte[] imageBinary = imageStreamUtil.getBinaryDimensionated(imageInputStream, imageExtension);
		pessoa.setImagemBinario( imageBinary );
	}
	
	
	/**
	 * Grava no disco a imagem do contact.
	 * Se ele não tiver imagem, não grava nada.
	 * @param produto
	 */
	private void salvarImagemNoFS() throws IOException {
		if (pessoa.getFlagImagemOK() ) {
			byte[] imageBinary = pessoa.getImagemBinario();
			String imageName = pessoa.getImagemNome();
		
			imageStreamUtil.writeInFileSystem(imageBinary, imageName);
		}
	}
	
	/**
	 * Salva o contact que implicitamente salvara sua imagem
	 */
	public void salvarImagemNoDB() {
		pessoa = pessoaService.salvarPessoa( pessoa, sessionHolder.getUsuario() );
		recarregar();
		initPessoas();
		JSFUtil.addInfoMessage("Foto salva com sucesso");
	}
	
	
	/**
	 * Remove as informacoes da imagem
	 * Nota: fisicamente a imagem continua no FileSystem
	 */
	public void removeImage() {
		pessoa.setImagemBinario( null );
		pessoa.setImagemExtensao( null );
		pessoa = pessoaService.salvarPessoa( pessoa, sessionHolder.getUsuario() );
		recarregar();
		initPessoas();
		JSFUtil.addInfoMessage("Foto removida");
	}
	
	
	
	/* *****************
	 * Cartão de Negocio
	 *******************/
	public void onCartaoNegocioUpload(FileUploadEvent event) throws IOException {
		PessoaCartaoNegocio newCartaoNegocio = criarCartaoNegocio();
		
		redimCartaoNegocio(event, newCartaoNegocio);
		newCartaoNegocio = salvarCartaoNegocio(newCartaoNegocio);
		escreverCartaoNegocioImagem(newCartaoNegocio);
		
		JSFUtil.addInfoMessage("Cartão de Negócio salvo com sucesso");
	}

	
	private PessoaCartaoNegocio criarCartaoNegocio() {
		PessoaCartaoNegocio cartaoNegocio = new PessoaCartaoNegocio();
		cartaoNegocio.setPessoa( pessoa );
		return cartaoNegocio;
	}

	private void redimCartaoNegocio(FileUploadEvent event, PessoaCartaoNegocio newCartaoNegocio) throws IOException {
		//1.extensao da imagem
		String imageExtension = imageStreamUtil.extractExtension( event.getFile().getFileName() );
		newCartaoNegocio.setImagemExtensao(imageExtension);
		//2.conteudo binario da imagem
		InputStream imageInputStream = event.getFile().getInputstream();
		int wDim = PessoaCartaoNegocio.W_DIM;
		int hDim = PessoaCartaoNegocio.H_DIM;
		byte[] imageBinary = imageStreamUtil.getBinaryDimensionated(imageInputStream, imageExtension, wDim, hDim);
		newCartaoNegocio.setImagemBinario(imageBinary);
	}
	
	private void escreverCartaoNegocioImagem(PessoaCartaoNegocio cartaoNegocio) throws IOException {
		byte[] imageBinary = cartaoNegocio.getImagemBinario();
		String imageName = cartaoNegocio.getImagemNome();
		imageStreamUtil.writeInFileSystem(imageBinary, imageName);
	}
	
	
	private PessoaCartaoNegocio salvarCartaoNegocio(PessoaCartaoNegocio newCartaoNegocio) {
		newCartaoNegocio = pessoaService.salvarPessoaCartaoNegocio(newCartaoNegocio, sessionHolder.getUsuario() );
		recarregar();
		return newCartaoNegocio;
	}
	
	
	public void removerCartaoNegocio(PessoaCartaoNegocio card) {
		pessoaService.removerPessoaCartaoNegocio(card);
		recarregar();
		JSFUtil.addInfoMessage("Cartão de Negócio removido");
	}
	
	


	//acessores...
	private static final long serialVersionUID = -1748543442806801025L;


	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	public String getFiltroNome() {
		return filtroNome;
	}


	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}


	public String getFiltroEmail() {
		return filtroEmail;
	}


	public void setFiltroEmail(String filtroEmail) {
		this.filtroEmail = filtroEmail;
	}


	public String getFiltroCidade() {
		return filtroCidade;
	}


	public void setFiltroCidade(String filtroCidade) {
		this.filtroCidade = filtroCidade;
	}


	public PessoaObservacao getNovaObservacao() {
		return novaObservacao;
	}


	public void setNovaObservacao(PessoaObservacao novaObservacao) {
		this.novaObservacao = novaObservacao;
	}


	public List<Pessoa> getPessoas() {
		return pessoas;
	}


	public List<AreaInteresse> getComboAreasInteresse() {
		return comboAreasInteresse;
	}


	public List<Profissao> getComboProfissoes() {
		return comboProfissoes;
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
	
}
