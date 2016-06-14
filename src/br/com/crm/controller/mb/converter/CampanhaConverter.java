package br.com.crm.controller.mb.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import br.com.crm.model.entity.Campanha;
import br.com.crm.model.service.CampanhaService;

/**
 * Converter para campanha
 * @author Solkam
 * @since 11 jun 2016
 */
@ManagedBean
@ViewScoped
public class CampanhaConverter implements Converter {

	@Inject CampanhaService service;
	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String txt) {
		if (txt==null || txt.isEmpty()) {
			return null;
		}
		Integer id = Integer.parseInt( txt );
		Campanha campanha = service.buscarCampanhaPeloId( id );
		return campanha;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
		if (obj==null) {
			return null;
		}
		Campanha campanha = (Campanha) obj;
		return String.valueOf( campanha.getId() );
	}

}
