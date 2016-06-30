package br.com.crm.controller.mb.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.service.AcessoService;

/**
 * Controller para Autenticar Usuário
 * @author Solkam
 * @since 26 abr 2016
 */
@ManagedBean
@RequestScoped
public class AccessMB implements Serializable {

	@Inject AcessoService acessoService;

	@Inject SessionHolder sessionHolder;
	
	
	private String login;
	private String senha;
	
	
	
	public String doLogin() {
		Usuario user = acessoService.buscarUsuarioPeloLoginESenha(login, senha);
		if (user!=null) {
			return grantAcess(user);
		} else {
			return denyAcess();
		}
	}


	private String grantAcess(Usuario user) {
		user = acessoService.autorizarUsuario(user);
		sessionHolder.initSession(user);
		return gotoHomePage();
	}

	
	private String denyAcess() {
		JSFUtil.addErroMessage("Login ou senha inválidos");
		return gotoLoginPage(false);
	}
	


	public String doLogout() {
		sessionHolder.finalizeSession();
		JSFUtil.addInfoMessage("Sua sessão foi encerrada com sucesso");
		return gotoLoginPage(true);
	}

	/**
	 * De acordo com o perfil do usuário, 
	 * direciona para uma pagina.
	 * @return
	 */
	private String gotoHomePage() {
		return "home";
	}
	
	private String gotoLoginPage(boolean flagRedirect) {
		if (flagRedirect) {
			return "/login?faces-redirect=true";
		} else {
			return "/login";
		}
	}
	
	
	
	
	
	//acessores...
	private static final long serialVersionUID = 1421116987452326810L;

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
