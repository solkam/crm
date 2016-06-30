package br.com.crm.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Area de interesse (hobby ou trabalho)
 * @author Solkam
 * @since 28 abr 2016
 */
@Entity
public class AreaInteresse implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne
	@NotNull
	private Empresa empresa;
	
	
	@NotNull
	@Size(max=100)
	private String descricao;
	
	
    private Boolean flagAtivo=true;

    
    @Embedded
    private InfoLog infoLog;
    


    //construtores...
    public AreaInteresse() {
    }
    
    public AreaInteresse(Empresa empresa, String descricao) {
    	this.empresa = empresa;
    	this.descricao = descricao;
    }
    
    
    
	
	//accesores
    private static final long serialVersionUID = 525956140929273067L;

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
		AreaInteresse other = (AreaInteresse) obj;
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
			getInfoLog().setCriadoPor( usuario.getDescricaoCompleta() );
			getInfoLog().setCriadoEm( new Date() );
		} else {
			getInfoLog().setAtualizadoPor( usuario.getDescricaoCompleta() );
			getInfoLog().setAtualizadoEm( new Date() );
		}
	}
	
	
	
}
