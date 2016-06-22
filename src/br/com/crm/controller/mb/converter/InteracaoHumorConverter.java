package br.com.crm.controller.mb.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.crm.model.entity.InteracaoHumor;

/**
 * Converter para o enum Humor
 * @author Solkam
 * @since 22 jun 2016
 */
@ManagedBean
@ViewScoped
public class InteracaoHumorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String txt) {
		if (txt==null || txt.trim().isEmpty()) {
			return null;
		}
		return InteracaoHumor.valueOf(txt);
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
		if (obj==null) {
			return null;
		}
		InteracaoHumor humor = (InteracaoHumor) obj;
		return humor.name();
	}

}
