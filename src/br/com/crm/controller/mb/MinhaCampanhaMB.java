package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.dto.PessoaInteracoesDTO;
import br.com.crm.model.entity.Campanha;
import br.com.crm.model.entity.InteracaoCampanha;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.service.CampanhaService;

/**
 * Controller para UC Gerenciar Minha Campanha
 * @author Solkam
 * @since 20 mai 2016
 */
@ManagedBean
@SessionScoped
public class MinhaCampanhaMB implements Serializable {
	
	private static final String PAGINA_MINHA_CAMPANHA_INTERACAO = "/paginas/minha-campanha/minha-campanha-interacao.xhtml";
	
	@Inject CampanhaService service;
	
	@Inject SessionHolder sessionHolder;
	
	
	private List<Campanha> minhasCampanhas;
	
	private Campanha minhaCampanha;
	
	
	
	
	@PostConstruct void init() {
		initMinhasCampanhas();
	}


	private void initMinhasCampanhas() {
		this.minhasCampanhas = service.pesquisarCampanhaAtivaPeloResponsavel( sessionHolder.getUsuario() );
	}


	
	private List<PessoaInteracoesDTO> listaPessoaInteracoesDTO;
	
	
	public String interagir(Campanha campanhaSelecionada) {
		this.minhaCampanha = campanhaSelecionada;
		recarregar();
		return PAGINA_MINHA_CAMPANHA_INTERACAO;
	}
	
	
	//nova interação
	private InteracaoCampanha novaInteracao;
	
	public void criarInteracao(Pessoa pessoaSelecionada) {
		novaInteracao = new InteracaoCampanha();
		novaInteracao.setCampanha(minhaCampanha);
		novaInteracao.setResponsavel( sessionHolder.getUsuario() );
		novaInteracao.setPessoa( pessoaSelecionada );
		novaInteracao.setData( new Date() );
	}
	

	public void salvarNovaInteracao() {
		novaInteracao = service.salvarInteracaoCampanha( novaInteracao );
		criarInteracao( novaInteracao.getPessoa() );
		recarregar();
		JSFUtil.addInfoMessage("Interação salva com sucesso");
	}
	

	//util
	private void recarregar() {
		minhaCampanha = service.recarregarCampanha(minhaCampanha);
		listaPessoaInteracoesDTO = service.pesquisarPessoaInteracoesDTOPorCampanha(minhaCampanha);
	}
	
	
	
	//acessores...
	private static final long serialVersionUID = 3333072305800737491L;
	public List<Campanha> getMinhasCampanhas() {
		return minhasCampanhas;
	}

	public void setMinhasCampanhas(List<Campanha> minhasCampanhas) {
		this.minhasCampanhas = minhasCampanhas;
	}

	public Campanha getMinhaCampanha() {
		return minhaCampanha;
	}

	public void setMinhaCampanha(Campanha minhaCampanha) {
		this.minhaCampanha = minhaCampanha;
	}

	public InteracaoCampanha getNovaInteracao() {
		return novaInteracao;
	}

	public void setNovaInteracao(InteracaoCampanha novaInteracao) {
		this.novaInteracao = novaInteracao;
	}

	public List<PessoaInteracoesDTO> getListaPessoaInteracoesDTO() {
		return listaPessoaInteracoesDTO;
	}
	
	
}