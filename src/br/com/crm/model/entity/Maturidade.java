package br.com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Maturidade de um contato segundo suas faixa de idade
 * @author Solkam
 * @since 28 abr 2016
 */
@Entity
public class Maturidade implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne
	@NotNull
	private Empresa empresa;
	
	
	@Size(max=30)
	@NotNull
	private String descricao;
	
	
	@NotNull
	private Integer minIdade;
	
	
	@NotNull
	private Integer maxIdade;
	
	

	//log
	private InfoLog infoLog;

	
	//acessores...
	private static final long serialVersionUID = 7449735206279342831L;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public InfoLog getInfoLog() {
		if (infoLog==null) {
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

	public Integer getMinIdade() {
		return minIdade;
	}

	public void setMinIdade(Integer minIdade) {
		this.minIdade = minIdade;
	}

	public Integer getMaxIdade() {
		return maxIdade;
	}

	public void setMaxIdade(Integer maxIdade) {
		this.maxIdade = maxIdade;
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
		Maturidade other = (Maturidade) obj;
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

	
	/**
	 * Verifica se uma idade está dentro do min e max da maturidade
	 * @param address
	 * @return
	 */
	public boolean getFlagDentroDasIdades(Integer idade) {
		if (idade==null) return false;
		
		if (minIdade <= idade && idade <= maxIdade) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getDescricaoCompleta() {
		return String.format("%s (de %d a %d)", getDescricao(), getMinIdade(), getMaxIdade() );
	}
	
}
