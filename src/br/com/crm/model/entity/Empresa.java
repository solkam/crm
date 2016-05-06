package br.com.crm.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Empresa onde estão vinculado todos as entidades do sistema.
 * Representa o cliente do CRM
 * @author Solkam
 * @since 03 mai 2016
 */
@Entity
public class Empresa implements Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	
	@Size(max=100)
	@NotNull
	private String nome;

	
	@Size(max=20)
	@NotNull
	private String cnpj;
	
	@NotNull
	private Boolean flagAtivo = true;
	
	
	@OneToMany(mappedBy="empresa")
	private List<Usuario> usuarios;
	
	
	
	
	@Embedded
	private InfoLog infoLog;
	
	
	
	//acessores...
	private static final long serialVersionUID = -3739150312983609101L;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public InfoLog getInfoLog() {
		if (infoLog==null) {
			infoLog = new InfoLog();
		}
		return infoLog;
	}

	public void setInfoLog(InfoLog infoLog) {
		this.infoLog = infoLog;
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCnpj() {
		return cnpj;
	}


	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
		Empresa other = (Empresa) obj;
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
