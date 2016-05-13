package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Maturidade;
import br.com.crm.model.service.MaturidadeService;

/**
 * Controller para UC Gerenciar Maturidade
 * @author Solkam
 * @since
 */
@ManagedBean
@ViewScoped
public class MaturidadeMB implements Serializable {
	
	@Inject MaturidadeService service;

	@Inject SessionHolder sessionHolder;
	
	private List<Maturidade> maturidades;
	
	private Maturidade novaMaturidade;
	
	
	@PostConstruct void init() {
		initMaturidades();
		nova();
	}
	
	
	private void initMaturidades() {
		maturidades = service.pesquisarMaturidade( sessionHolder.getEmpresa() );
	}

	
	public void nova() {
		novaMaturidade = new Maturidade();
	}

	public void pesquisar() {
		initMaturidades();
		JSFUtil.addMessageAboutResult(maturidades);
	}
	
	
	public void gerenciar(Maturidade selectedMaturidade) {
		this.novaMaturidade = selectedMaturidade;
	}
	
	
	public void salvar() {
		novaMaturidade = service.salvarMaturidade(novaMaturidade, sessionHolder.getUsuario() );
		initMaturidades();
		nova();
		JSFUtil.addInfoMessage("Maturidade salva com sucesso");
	}
	
	public void salvarTudo() {
		for (Maturidade maturidadeVar : maturidades) {
			service.salvarMaturidade(maturidadeVar, sessionHolder.getUsuario() );
		}
		initMaturidades();
		JSFUtil.addInfoMessage("Todas as maturidades salvas com sucesso");
	}
	
	
	public void remover(Maturidade maturidadeSelecionada) {
		service.removerMaturidade(maturidadeSelecionada);
		initMaturidades();
		//formatando msg...
		String descricao = maturidadeSelecionada.getDescricao();
		Integer minIdade = maturidadeSelecionada.getMinIdade();
		Integer maxIdade = maturidadeSelecionada.getMaxIdade();
		String msg = String.format("Maturidade [%s: de %s a %s] removida", descricao, minIdade, maxIdade );
		JSFUtil.addInfoMessage(msg);
	}


	//acessores...
	private static final long serialVersionUID = -547089387978200127L;
	public Maturidade getNovaMaturidade() {
		return novaMaturidade;
	}
	public void setNovaMaturidade(Maturidade novaMaturidade) {
		this.novaMaturidade = novaMaturidade;
	}
	public List<Maturidade> getMaturidades() {
		return maturidades;
	}
	
}
