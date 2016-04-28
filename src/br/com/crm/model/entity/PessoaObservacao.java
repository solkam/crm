package br.com.crm.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Observação sobre o contato com data e responsável
 * @author Solkam
 * @since 26 OUT 2016
 */
@Entity
public class PessoaObservacao implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne
	@NotNull
	private Pessoa pessoa;
	

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date dataObservacao;
	
	
	@Size(max=1000)
	@NotNull
	private String textoObservacao;
	
	
	@NotNull
	private String responsavel;

	

	//acessores...
	private static final long serialVersionUID = -6612355601926202763L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Date getDataObservacao() {
		return dataObservacao;
	}

	public void setDataObservacao(Date dataObservacao) {
		this.dataObservacao = dataObservacao;
	}

	public String getTextoObservacao() {
		return textoObservacao;
	}

	public void setTextoObservacao(String textoObservacao) {
		this.textoObservacao = textoObservacao;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
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
		PessoaObservacao other = (PessoaObservacao) obj;
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
