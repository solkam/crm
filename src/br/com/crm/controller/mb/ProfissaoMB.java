package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.mb.security.SessionHolder;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Profissao;
import br.com.crm.model.service.ProfissaoService;

/**
 * Controller para UC Gerenciar Profissões
 * @author Solkam
 * @since 28 abr 2016
 */
@ManagedBean
@ViewScoped
public class ProfissaoMB implements Serializable {

	@Inject ProfissaoService service;
	
	@Inject SessionHolder sessionHolder;
	
	
	private List<Profissao> profissoes;
	
	private Profissao novaProfissao;
	
	
	@PostConstruct void init() {
		novo();
		pesquisar();
	}
	
	
	private void initProfissoes() {
		profissoes = service.pesquisarProfissao( sessionHolder.getEmpresa() );
	}
	
	
	
	//actions
	
	public void pesquisar() {
		initProfissoes();
		JSFUtil.addMessageAboutResult(profissoes);
	}
	
	private void novo() {
		novaProfissao = new Profissao();
	}
	
	public void salvar() {
		service.salvarProfissao(novaProfissao, sessionHolder.getUsuario() );
		initProfissoes();
		novo();
		JSFUtil.addInfoMessage("Profissão salva com sucesso");
	}
	
	public void salvarTudo() {
		for (Profissao profissaoVar : profissoes) {
			service.salvarProfissao(profissaoVar, sessionHolder.getUsuario() );
		}
		initProfissoes();
		JSFUtil.addInfoMessage("Todas as profissões salvas com sucesso");
	}
	
	public void remover(Profissao profissaoSelecionada) {
		service.removerProfissao(profissaoSelecionada);
		initProfissoes();
		JSFUtil.addInfoMessage(String.format("Profissão [%s] removida", profissaoSelecionada.getDescricao() ));
	}




	//acessores...
	private static final long serialVersionUID = -347132769129023056L;


	public Profissao getNovaProfissao() {
		return novaProfissao;
	}


	public void setNovaProfissao(Profissao novaProfissao) {
		this.novaProfissao = novaProfissao;
	}


	public List<Profissao> getProfissoes() {
		return profissoes;
	}

}
