package br.com.crm.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Venda de um produto para uma pessa relacionanada 
 * a uma empresa.
 * @author Solkam
 * @since 01 jun 2016
 */
@Entity
public class Venda implements Serializable {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * Codigo da venda no sistema de origem
	 */
	private String codVenda;
	
	
	@ManyToOne
	@NotNull
	private Empresa empresa;

	
	@ManyToOne
	@NotNull
	private Produto produto;
	
	
	@ManyToOne
	@NotNull
	private Pessoa pessoa; 
	
	
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date data;
	
	
	private BigDecimal precoFinal;

	
	//acessores...
	private static final long serialVersionUID = 6985373890067533902L;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodVenda() {
		return codVenda;
	}

	public void setCodVenda(String codVenda) {
		this.codVenda = codVenda;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getPrecoFinal() {
		return precoFinal;
	}

	public void setPrecoFinal(BigDecimal precoFinal) {
		this.precoFinal = precoFinal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public boolean isTransient() {
		return getId()==null;
	}
	
}
