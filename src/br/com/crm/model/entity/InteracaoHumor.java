package br.com.crm.model.entity;

public enum InteracaoHumor {
	
	FELIZ("icone_humor_feliz.png")
	,
	NORMAL("icone_humor_normal.png")
	,
	IRRITADO("icone_humor_irritado.png")
	;
	
	private final String img;

	
	private InteracaoHumor(String img) {
		this.img = img;
	}

	
	public String getImg() {
		return img;
	}

}
