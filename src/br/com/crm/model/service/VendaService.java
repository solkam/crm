package br.com.crm.model.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.crm.model.entity.Empresa;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.entity.Venda;

/**
 * Serviços de negocio para vendas
 * @author Solkam
 * @since 03 jun 2016
 */
@Stateless
public class VendaService {
	
	@PersistenceContext EntityManager manager;
	
	
	public Venda salvarVenda(Venda venda, Usuario usuario) {
		//pre-salvar:
		venda.setEmpresa( usuario.getEmpresa() );
		venda.inserirInfoLog( usuario );
		//salvar...
		return manager.merge(venda);
	}
	
	
	public void removerVenda(Venda venda) {
		venda = manager.merge(venda);
		manager.remove( venda );
	}
	
	
	public List<Venda> pesquisarVendaPelosFiltros(Empresa empresa) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Venda> criteria = builder.createQuery(Venda.class);
		Root<Venda> root = criteria.from(Venda.class);
		
		Predicate conjunction = builder.conjunction();
		//empresa
		conjunction = builder.and(conjunction
				,builder.equal(root.<Empresa>get("empresa"), empresa)
				);
		
		criteria.where(conjunction);
		
		criteria.orderBy( builder.desc(root.<Date>get("data") ));
		
		return manager.createQuery(criteria).getResultList();
	}
	
	
	

}
