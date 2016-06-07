package br.com.crm.controller.mb.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import br.com.crm.model.entity.CategoriaProduto;
import br.com.crm.model.service.ProdutoService;

/**
 * Converter para converter para categoria de produto
 * @author Solkam
 * @since 07 jun 2016
 */
@ManagedBean
@ViewScoped
public class CategoriaProdutoConverter implements Converter {

	@Inject ProdutoService service;
	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String txt) {
		if (txt==null || txt.isEmpty()) {
			return null;
		}
		Integer id = Integer.parseInt( txt );
		CategoriaProduto categoria = service.buscarCategoriaProdutoPeloId(id);
		return categoria;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
		if (obj==null) {
			return null;
		}
		CategoriaProduto categoria = (CategoriaProduto) obj;
		return String.valueOf( categoria.getId() );
	}

}
