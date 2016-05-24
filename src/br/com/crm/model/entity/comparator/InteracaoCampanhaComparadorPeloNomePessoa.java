package br.com.crm.model.entity.comparator;

import java.util.Comparator;

import br.com.crm.model.entity.InteracaoCampanha;

/**
 * Comparador para InteracaoCampanha usando nome da pessoa
 * @author Solkam
 * @since 24 mai 2016
 */
public class InteracaoCampanhaComparadorPeloNomePessoa implements Comparator<InteracaoCampanha> {

	@Override
	public int compare(InteracaoCampanha interacao1, InteracaoCampanha interacao2) {
		String nome1 = interacao1.getPessoa().getNomeCompleto();
		String nome2 = interacao2.getPessoa().getNomeCompleto();
		return nome1.compareTo( nome2 );
	}

}
