package br.com.crm.model.entity;

public enum InteracaoHumor {
	
	FELIZ(   "icone_humor_feliz_reduzida_64.png"   , "icone_humor_feliz_completa_32.png"   ),
	NORMAL(  "icone_humor_normal_reduzida_64.png"  , "icone_humor_normal_completa_32.png"  ),
	IRRITADO("icone_humor_irritado_reduzida_64.png", "icone_humor_irritado_completa_32.png");
	
	private final String imgReduzida;
	private final String imgCompleta;

	
	private InteracaoHumor(String imgReduzida, String imgCompleta) {
		this.imgReduzida = imgReduzida;
		this.imgCompleta = imgCompleta;
	}


	
	public String getImgReduzida() {
		return imgReduzida;
	}


	public String getImgCompleta() {
		return imgCompleta;
	}

	

	
}
