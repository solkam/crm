package br.com.crm.controller.mb.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import br.com.crm.model.entity.Profissao;
import br.com.crm.model.service.ProfissaoService;

/**
 * Converter para profissoes
 * @author Solkam
 * @since 09 mai 2016
 */
@ManagedBean
@ViewScoped
public class ProfissaoConverter implements Converter {

	@Inject ProfissaoService service;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String txt) {
		if (txt==null || txt.trim().isEmpty()) {
			return null;
		}
		
		Integer id = Integer.parseInt( txt );
		Profissao p = service.buscarProfissaoPorId(id);
		return p;
	}

	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if (object == null) {
			return null;
		}
		Profissao p = (Profissao) object;
		return String.valueOf( p.getId() ); 
	}

}
