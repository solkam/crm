package br.com.crm.controller.mb.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.crm.model.entity.AreaInteresse;
import br.com.crm.model.service.AreaInteresseService;


/**
 * Converter para Areas de Interesse
 * @author Solkam
 * @since 09 mai 2016
 */
@ManagedBean(name="areaInteresseConverter")
@ViewScoped
public class AreaInteresseConverter implements Converter {

	@EJB AreaInteresseService service;
	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String txt) {
		if (txt==null || txt.trim().isEmpty()) {
			return null;
		}
		Integer areaId = Integer.parseInt(txt);
		AreaInteresse area = service.buscarAreaInteressePorId(areaId);
		return area;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
		if (obj==null) {
			return null;
		}
		AreaInteresse area = (AreaInteresse) obj;
		return String.valueOf( area.getId() );
	}

}
