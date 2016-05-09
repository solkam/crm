package br.com.crm.controller.helper;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.com.crm.model.entity.DiaDoMes;
import br.com.crm.model.entity.EnderecoTipo;
import br.com.crm.model.entity.Genero;
import br.com.crm.model.entity.MesDoAno;
import br.com.crm.model.entity.Perfil;
import br.com.crm.model.entity.TelefoneOperadora;

/**
 * Helper para enum serem visualizados em componentes Select
 * @author Solkam
 * @since 27 abr 2016
 */
@ManagedBean(name="enumHelper")
@ApplicationScoped
public class EnumHelper {
	
	public Perfil[] getPerfils() {
		return Perfil.values();
	}
	
	public Genero[] getGeneros() {
		return Genero.values();
	}

	public EnderecoTipo[] getEnderecoTipos() {
		return EnderecoTipo.values();
	}
	
	public TelefoneOperadora[] getTelefoneOperadoras() {
		return TelefoneOperadora.values();
	}

	
	public DiaDoMes[] getDiasDoMes() {
		return DiaDoMes.values();
	}

	public MesDoAno[] getMesesDoAno() {
		return MesDoAno.values();
	}
	
	
	//	
//	public ParticipationCategory[] getParticipationCategories() {
//		return ParticipationCategory.values();
//	}
//	
//	public EventStatus[] getEventStatuses() {
//		return EventStatus.values();
//	}
//	
//	public ProductCategory[] getProductCategories() {
//		return ProductCategory.values();
//	}
//	
//	public EventType[] getEventTypes() {
//		return EventType.values();
//	}
//	
//	public VinculoType[] getVinculoTypes() {
//		return VinculoType.values();
//	}
	
}