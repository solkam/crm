package br.com.crm.controller.mb.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import br.com.crm.model.entity.Usuario;
import br.com.crm.model.service.AcessoService;

/**
 * Converter JSF para usuario
 * @author Solkam
 * @since 20 mai 2016
 */
@ManagedBean
@ViewScoped
public class UsuarioConverter implements Converter {

	@Inject AcessoService service;
	
	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String txt) {
		if (txt==null || txt.trim().isEmpty()) {
			return null;
		}
		
		Integer id = Integer.parseInt( txt );
		return service.buscarUsuarioPeloId( id );
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
		if (obj==null) {
			return null;
		}
		Usuario usuario = (Usuario) obj;
		return String.valueOf( usuario.getId() );
	}

}
