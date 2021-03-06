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
 * Usuario para acesso ao sistema com email, senha, perfil e empresa
 * @author Solkam
 * @since 26 abr 2016
 */
@Entity
public class Usuario implements Serializable, Comparable<Usuario> {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@NotNull
	private Empresa empresa;
	
	
	@NotNull
	@Size(max=50)
	private String login;
	
	
	@NotNull
	@Size(max=100)
	private String email;


	@Size(max=100)
	private String senha;


	@ManyToOne
	@NotNull
	private Perfil perfil;
	
	
	@NotNull
	private Boolean flagTecnologia = false;
	
	
	@Embedded
	private InfoLog infoLog;
	
	
	
	//acessores...
	private static final long serialVersionUID = 380973725983130162L;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Boolean getFlagTecnologia() {
		return flagTecnologia;
	}

	public void setFlagTecnologia(Boolean flagTecnologia) {
		this.flagTecnologia = flagTecnologia;
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
		Usuario other = (Usuario) obj;
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

	@Override
	public int compareTo(Usuario that) {
		if (this.id==null) {
			return 0;
		} else {
			return this.id.compareTo( that.id );
		}
	}
	
	
	public String getDescricaoCompleta() {
		return this.login;
	}


	public void inserirInfoLog(Usuario usuarioSalvador) {
		if (isTransient()) {
			getInfoLog().setCriadoEm( new Date() );
			getInfoLog().setCriadoPor( usuarioSalvador.getDescricaoCompleta() );
		} else {
			getInfoLog().setAtualizadoEm( new Date() );
			getInfoLog().setAtualizadoPor( usuarioSalvador.getDescricaoCompleta() );
		}
	}
	
	
	
}
