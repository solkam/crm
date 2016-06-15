package br.com.crm.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Perfis configurável de acesso de usuarios
 * @author Solkam
 * @since 14 jun 2016
 */
@Entity
public class Perfil implements Serializable {
	
	@Id
	private String codigo;
	
	
	@NotNull
	@Size(max=50)
	private String nome;


	@ElementCollection(targetClass=Funcionalidade.class)
	@CollectionTable(name="Perfil_x_Funcionalidade", joinColumns=@JoinColumn(name="perfil_codigo"))
	@Enumerated(EnumType.STRING)
	@Column(name="funcionalidade")
	private List<Funcionalidade> funcionalidades;
	
	
	private Boolean flagVisivel=true;
	
	private InfoLog infoLog;


	//acessores...
	private static final long serialVersionUID = -5497953041455998885L;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Boolean getFlagVisivel() {
		return flagVisivel;
	}
	public void setFlagVisivel(Boolean flagVisivel) {
		this.flagVisivel = flagVisivel;
	}
	public List<Funcionalidade> getFuncionalidades() {
		return funcionalidades;
	}
	public void setFuncionalidades(List<Funcionalidade> funcionalidades) {
		this.funcionalidades = funcionalidades;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Perfil other = (Perfil) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Perfil [codigo=" + codigo + ", nome=" + nome + "]";
	}
	
	public boolean isTransient() {
		return getCodigo()==null;
	}

}
