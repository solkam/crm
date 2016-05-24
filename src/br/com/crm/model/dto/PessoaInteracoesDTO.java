package br.com.crm.model.dto;

import java.io.Serializable;
import java.util.List;

import br.com.crm.model.entity.Campanha;
import br.com.crm.model.entity.InteracaoCampanha;
import br.com.crm.model.entity.Pessoa;

/**
 * Relaciona uma pessoa e suas interações numa campanha
 * @author Solkam
 * @since 24 mai 2016
 */
public class PessoaInteracoesDTO implements Serializable {
	
	private Pessoa pessoa;
	
	private Campanha campanha;
	
	private List<InteracaoCampanha> interacoes;


	//construtores...
	public PessoaInteracoesDTO() {
	}
	
	public PessoaInteracoesDTO(Pessoa pessoa, Campanha campanha, List<InteracaoCampanha> interacoes) {
		this.pessoa = pessoa;
		this.campanha = campanha;
		this.interacoes = interacoes;
	}

	
	//acessores...
	private static final long serialVersionUID = 81733348211325679L;
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public List<InteracaoCampanha> getInteracoes() {
		return interacoes;
	}

	public void setInteracoes(List<InteracaoCampanha> interacoes) {
		this.interacoes = interacoes;
	}
	
	//metodos especiais
	public Integer getQuantidadeInteracoes() {
		return this.interacoes==null ? 0 : this.interacoes.size();
	}
	
	
	
}
