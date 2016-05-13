package br.com.crm.controller.mb.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.service.PessoaService;

/**
 * Conversor para Pessoa
 * @author Solkam
 * @since 09 mai 2016
 */
@ManagedBean(name="pessoaConverter")
@ViewScoped
public class PessoaConverter implements Converter {

	@EJB
	private PessoaService service;
	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String txt) {
		if (txt==null || txt.trim().isEmpty()) {
			return null;
		}
		
		Integer id = Integer.parseInt( txt );
		Pessoa pessoaEncontrada = service.buscarPessoaPeloId( id );
		return pessoaEncontrada;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
		if (obj==null) {
			return null;
		}
		
		Pessoa p = (Pessoa) obj;
		return p.getId().toString();
	}

}
