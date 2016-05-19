package br.com.crm.controller.mb.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import br.com.crm.model.entity.Produto;
import br.com.crm.model.service.ProdutoService;

/**
 * Converter para Produto
 * @author Solkam
 * @since 11 mai 2016
 */
@ManagedBean
@ViewScoped
public class ProdutoConverter implements Converter {

	@Inject ProdutoService service;
	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String txt) {
		if (txt==null || txt.trim().isEmpty()) return null;
		
		Integer id = Integer.parseInt( txt );
		Produto produtoEncontrado = service.buscarProdutoPeloId(id);
		return produtoEncontrado;
	}

	
	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
		if (obj==null) return null;
		
		Produto prod = (Produto) obj;
		return String.valueOf( prod.getId() );
	}

}
