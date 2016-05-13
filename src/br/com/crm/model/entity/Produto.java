package br.com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Produto ou serviço oferecido pela empresa
 * e comprado ou contratada pela pessoa
 * @author Solkam
 * @since 10 mai 2016
 */
@Entity
public class Produto implements Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne
	@NotNull
	private Empresa empresa;
	
	
	@Size(max=100)
	@NotNull
	private String descricao;
	
	
	@Size(max=1000)
	private String observacao;
	
	
	@NotNull
	private Boolean flagAtivo=true;
	
	
	//log
	@Embedded
	private InfoLog infoLog;

	

	//acessores...
	private static final long serialVersionUID = -8485301523476681968L;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public InfoLog getInfoLog() {
		if (infoLog==null){
			infoLog=new InfoLog();
		}
		return infoLog;
	}
	public void setInfoLog(InfoLog infoLog) {
		this.infoLog = infoLog;
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
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Boolean getFlagAtivo() {
		return flagAtivo;
	}
	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
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
		Produto other = (Produto) obj;
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
