package br.com.crm.controller.mb.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.crm.model.entity.Funcionalidade;

/**
 * Converter para enum funcionalidades
 * @author Solkam
 * @since 14 jun 2016
 */
@ManagedBean
@ViewScoped
public class FuncionalidadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent c, String txt) {
		if (txt==null || txt.isEmpty()) {
			return null;
		}
		return Funcionalidade.valueOf( txt );
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent c, Object obj) {
		if (obj==null) {
			return null;
		}
		Funcionalidade funcionalidade = (Funcionalidade) obj;
		return funcionalidade.name();
	}

}
