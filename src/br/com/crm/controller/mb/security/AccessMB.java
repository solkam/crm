package br.com.crm.controller.mb.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.service.UsuarioService;

/**
 * Controller para Autenticar
 * @author Solkam
 * @since 26 abr 2016
 */
@Named
@RequestScoped
public class AccessMB implements Serializable {

	@Inject UsuarioService usuarioService;

	@Inject SessionHolder sessionHolder;
	
	
	private String email;
	private String senha;
	
	
	
	public String doLogin() {
		Usuario user = usuarioService.buscarUsuarioPeloEmailESenha(email, senha);
		if (user!=null) {
			return grantAcess(user);
		} else {
			return denyAcess();
		}
	}


	private String grantAcess(Usuario user) {
		sessionHolder.initSession(user);
		return gotoHomePage();
	}

	
	private String denyAcess() {
		JSFUtil.addErroMessage("Email ou senha inválidos");
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

	public void setSessionHolder(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
