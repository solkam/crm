package br.com.crm.model.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * Classe embarcavel com os contatos de redes sociais
 * @author Solkam
 * @since 28 abr 2016
 */
@Embeddable
public class RedeSocial {
	
	@Size(max=100)
	private String socialFacebook;
	
	@Size(max=100)
	private String socialTwitter;
	
	@Size(max=100)
	private String socialLinkedIn;
	
	@Size(max=100)
	private String socialGooglePlus;

	
	
	public String getSocialFacebook() {
		return socialFacebook;
	}

	public void setSocialFacebook(String socialFacebook) {
		this.socialFacebook = socialFacebook;
	}

	public String getSocialTwitter() {
		return socialTwitter;
	}

	public void setSocialTwitter(String socialTwitter) {
		this.socialTwitter = socialTwitter;
	}

	public String getSocialLinkedIn() {
		return socialLinkedIn;
	}

	public void setSocialLinkedIn(String socialLinkedIn) {
		this.socialLinkedIn = socialLinkedIn;
	}

	public String getSocialGooglePlus() {
		return socialGooglePlus;
	}

	public void setSocialGooglePlus(String socialGooglePlus) {
		this.socialGooglePlus = socialGooglePlus;
	}

}
