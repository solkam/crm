package br.com.crm.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Interação com uma pessoas sobre uma campanha 
 * realizada por um responsavel. 
 * @author Solkam
 * @since 11 mai 2016
 */
@Entity
public class InteracaoCampanha implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne
	@NotNull
	private Campanha campanha;
	
	
	@ManyToOne
	@NotNull
	private Pessoa pessoa;

	
	@ManyToOne
	@NotNull
	private Usuario responsavel;
	
	
	@Size(max=1000)
	@NotNull
	private String nota;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date data;

	
	//construtores
	public InteracaoCampanha() {
	}
	
	public InteracaoCampanha(Pessoa pessoa) {
		this.pessoa = pessoa;
	}





	//acessores...
	private static final long serialVersionUID = 6743515337943198533L;
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Campanha getCampanha() {
		return campanha;
	}


	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}


	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	public Usuario getResponsavel() {
		return responsavel;
	}


	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}


	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
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
		InteracaoCampanha other = (InteracaoCampanha) obj;
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
