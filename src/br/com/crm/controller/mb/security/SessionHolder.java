package br.com.crm.controller.mb.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Empresa;
import br.com.crm.model.entity.Usuario;

/**
 * Objeto que guarda infos relevantes na sessao
 * @author Solkam
 * @since 26 abr 2016
 */
@Named
@SessionScoped
public class SessionHolder implements Serializable {
	
	private static final String USER_KEY = "USER";

	private Usuario usuario;
	
	
	
	public void initSession(Usuario usuario) {
		this.usuario = usuario;
		putUserOnSession(usuario);
	}
	

	public void finalizeSession() {
		invalidateSession();
		removeUserInSession();
	}




	
	
	//util
	private void putUserOnSession(Usuario usuario) {
		JSFUtil.getHttpSession().setAttribute(USER_KEY, usuario);
	}
	
	private void invalidateSession() {
		JSFUtil.getHttpSession().invalidate();
	}

	private void removeUserInSession() {
		JSFUtil.getHttpSession().removeAttribute(USER_KEY);
	}
	
	
	//acessores...
	private static final long serialVersionUID = 317919622145173395L;

	public Usuario getUsuario() {
		return usuario;
	}
	
	public Empresa getEmpresa() {
		return usuario.getEmpresa();
	}


	public Boolean getFlagLogged() {
		return usuario!=null;
	}
	
	
	public Boolean getFlagTecnologia() {
		if (usuario==null) {
			return false;
		}
		if (usuario.getFlagTecnologia()==null) {
			return false;
		}
		
		return usuario.getFlagTecnologia();
	}
	

	
}
