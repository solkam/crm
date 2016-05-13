package br.com.crm.controller.mb.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.crm.model.entity.Maturidade;
import br.com.crm.model.service.MaturidadeService;

/**
 * Converter para maturidade
 * @author Solkam
 * @since 11 mai 2016
 */
@ManagedBean
@ViewScoped
public class MaturidadeConverter implements Converter {

	@EJB MaturidadeService service;
	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent cp, String txt) {
		if (txt==null || txt.trim().isEmpty()) {
			return null;
		}
		Integer id = Integer.parseInt( txt );
		return service.buscarMaturidadePelaIdade(id);
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent cp, Object obj) {
		if (obj==null) {
			return null;
		}
		Maturidade m = (Maturidade) obj;
		return String.valueOf( m.getId() );
	}

}
