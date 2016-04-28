package br.com.crm.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Cartão de negocio da pessoa.
 * Uma pessoa pode ter vários
 * @author Solkam
 * @since 28 abr 2016
 */
@Entity
public class PessoaCartaoNegocio implements Serializable {
	
	public static final int W_DIM = 320;
	public static final int H_DIM = 170;
	

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	
	@ManyToOne
	@NotNull
	private Pessoa pessoa;
	
	
	@Lob
	private byte[] imagemBinario;
	
	
	@NotNull
	private String imagemExtensao;

	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUpload;


	//listener
	@PrePersist void onPersist() {
		this.dataUpload = new Date();
	}
	
	
	//acessores...
	private static final long serialVersionUID = -8031471673434804961L;
	
	
	
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
	public byte[] getImagemBinario() {
		return imagemBinario;
	}
	public void setImagemBinario(byte[] imagemBinario) {
		this.imagemBinario = imagemBinario;
	}
	public String getImagemExtensao() {
		return imagemExtensao;
	}
	public void setImagemExtensao(String imagemExtensao) {
		this.imagemExtensao = imagemExtensao;
	}
	public Date getDataUpload() {
		return dataUpload;
	}
	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
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
		PessoaCartaoNegocio other = (PessoaCartaoNegocio) obj;
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
	
	
	
	//runtime
	public String getImagemNome() {
		return String.format("businessCard_%s.%s", getId(), getImagemExtensao() );
	}
	
}
