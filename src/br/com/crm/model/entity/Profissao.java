package br.com.crm.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Profissao de uma pessos
 * @author Solkam
 * @since 28 abr 2016
 */
@Entity
public class Profissao implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@NotNull
	private Empresa empresa;
	
	@NotNull
	@Size(max=100)
	private String descricao;
	
	@NotNull
	private Boolean flagAtivo=true;
	
	
	@Embedded
	private InfoLog infoLog;


	//construtores...
	public Profissao() {
	}
	
	public Profissao(Empresa empresa, String descricao) {
		this.empresa = empresa;
		this.descricao = descricao;
	}
	
	
	//acessores...
	private static final long serialVersionUID = -6321700869069046254L;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
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
		Profissao other = (Profissao) obj;
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
	

	public void inserirInfoLog(Usuario usuario) {
		if (isTransient()) {
			getInfoLog().setCriadoEm( new Date() );
			getInfoLog().setCriadoPor( usuario.getDescricaoCompleta() );
		} else {
			getInfoLog().setAtualizadoEm( new Date() );
			getInfoLog().setAtualizadoPor( usuario.getDescricaoCompleta() );
		}
	}
	
   
}
