package br.com.crm.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Formulario de Inscrição do contato.
 * Um contato pode ter vários.
 * @author Solkam
 * @since 30 OUT 2015
 */
@Entity
public class ContactInscriptionForm implements Serializable {

	public static final int W_DIM = 794;
	public static final int H_DIM = 1115;

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne
	@NotNull
	private Pessoa contact;
	
	
	@Lob
	private byte[] imageBinary;
	
	
	@NotNull
	private String imageExtension;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;

	

	//listener
	@PrePersist void onPersist() {
		this.uploadDate = new Date();
	}
	

	//acessores...
	private static final long serialVersionUID = 4200502331182092778L;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Pessoa getContact() {
		return contact;
	}
	public void setContact(Pessoa contact) {
		this.contact = contact;
	}
	public byte[] getImageBinary() {
		return imageBinary;
	}
	public void setImageBinary(byte[] imageBinary) {
		this.imageBinary = imageBinary;
	}
	public String getImageExtension() {
		return imageExtension;
	}
	public void setImageExtension(String imageExtension) {
		this.imageExtension = imageExtension;
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
		ContactInscriptionForm other = (ContactInscriptionForm) obj;
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
	 * Monta o nome da imagem
	 * @return
	 */
	public String getImageName() {
		return String.format("inscriptionForm_%s.%s", getId(), getImageExtension() );
	}
	
	
}
