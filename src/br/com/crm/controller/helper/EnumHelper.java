package br.com.crm.controller.helper;

import javax.faces.bean.ApplicationScoped;

import javax.faces.bean.ManagedBean;

import br.com.crm.model.entity.Perfil;

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
	

//	public AddressType[] getAddressTypes() {
//		return AddressType.values();
//	}
//	
//	public Gender[] getGenders() {
//		return Gender.values();
//	}
//	
//	
//	public DateDay[] getDays() {
//		return DateDay.values();
//	}
//	
//	public DateMonth[] getMonths() {
//		return DateMonth.values();
//	}
//
//	public TelephoneMobileCompany[] getTelephoneMobileCompanies() {
//		return TelephoneMobileCompany.values();
//	}
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
