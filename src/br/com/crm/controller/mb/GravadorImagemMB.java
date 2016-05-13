package br.com.crm.controller.mb;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.com.crm.controller.util.ImageStreamUtil;
import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.PessoaCartaoNegocio;
import br.com.crm.model.service.PessoaService;

/**
 * Grava imagens de pessoas e cartao de negocio em 
 * disco para serem acessadas via navegador.
 * @author Solkam
 * @since 09 mai 2016
 */
@ManagedBean
public class GravadorImagemMB {
	
	@EJB PessoaService contactService;
	
	public String savePessoaImagesOnFS() throws IOException {
		ImageStreamUtil util = new ImageStreamUtil();
		
		//1.pessoas
		List<Pessoa> contacts = contactService.pesquisarPessoaComImagem();
		for (Pessoa contact : contacts) {
			util.writeInFileSystem(contact.getImagemBinario(), contact.getImagemNome() );
		}
		JSFUtil.addInfoMessage("Imagens das Pessoa salvas em disco");
		
		
		//2.cartoes de negocio
		List<PessoaCartaoNegocio> cards = contactService.pesquisarPessoaCartaoNegocio();
		for (PessoaCartaoNegocio cardVar : cards) {
			util.writeInFileSystem(cardVar.getImagemBinario(), cardVar.getImagemNome() );
		}
		JSFUtil.addInfoMessage("Imagens dos Cartões de Negócio salvos em disco");
		
		
		return "home";
	}
	

}
