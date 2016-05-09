package br.com.crm.model.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.com.crm.model.entity.AreaInteresse;
import br.com.crm.model.entity.Empresa;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.exception.NegocioException;

/**
 * Serviços para Areas de interesse
 * @author Solkam
 * @since 28 abr 2016
 */
@Stateless
public class AreaInteresseService {

	@PersistenceContext
	private EntityManager manager;
	
	/**
	 * Salva uma area de interesse aplicando RNs
	 * @param ia
	 * @return
	 */
	public AreaInteresse salvarAreaInteresse(AreaInteresse area, Usuario usuario) {
		verificaSeAreaInteresseDescricaoJaExiste(area, usuario.getEmpresa() );
		inserirInfoLog( area, usuario );
		referenciarEmpresa(area, usuario);
		return manager.merge( area );
	}
	
	
	private void referenciarEmpresa(AreaInteresse area, Usuario usuario) {
		area.setEmpresa( usuario.getEmpresa() );
	}




	/**
	 * RN para garantir a unicidade da descrição de areas de interesse
	 * @param area
	 */
	private void verificaSeAreaInteresseDescricaoJaExiste(AreaInteresse area, Empresa empresa) {
		AreaInteresse foundArea = buscarAreaInteressePorDescricao(area.getDescricao(), empresa );
		if (foundArea!=null && !foundArea.equals(area)) {
			throw new NegocioException("Já existe área de interesse com esta descrição");
		}
	}


	/**
	 * Conforme esteja criando ou atualizando, set o criadoPor ou atuaalizadoPor.
	 * @param area
	 * @param usuario
	 */
	private void inserirInfoLog(AreaInteresse area, Usuario usuario) {
		if (area.isTransient()) {
			area.getInfoLog().setCriadoPor( usuario.getEmail() );
		} else {
			area.getInfoLog().setAtualizadoPor( usuario.getEmail() );
		}
	}
	
	
	/**
	 * Remove uma area de interesse aplicando RNs
	 * @param area
	 */
	public void removerAreaInteresse(AreaInteresse area) {
		manager.remove( manager.merge(area) );
	}



	/**
	 * Busca uma area de interese pela PK
	 * @param id
	 * @return
	 */
	public AreaInteresse buscarAreaInteressePorId(Integer id) {
		return manager.find(AreaInteresse.class, id);
	}
	

	/**
	 * Busca uma area de interesse pelo nome
	 * @param description
	 * @return
	 */
	private AreaInteresse buscarAreaInteressePorDescricao(String description, Empresa empresa) {
		try {
			return manager.createNamedQuery("buscarAreaInteressePorDescricao", AreaInteresse.class)
					.setParameter("pDescricao", description)
					.setParameter("pEmpresa", empresa)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	/**
	 * Pesquisa por todas as areas de interesses
	 * @param empresa 
	 * @return
	 */
	public List<AreaInteresse> pesquisarAreaInteresse(Empresa empresa){
		 return	 manager.createNamedQuery("pesquisarAreaInteresse", AreaInteresse.class)
				 	.setParameter("pEmpresa", empresa)
					.getResultList();			
	}
	
	/**
	 * Pesquisa pelas areas ativos somente.
	 * @param empresa 
	 * @return
	 */
	public List<AreaInteresse> pesquisarAreaInteresseAtiva(Empresa empresa) {
		List<AreaInteresse> areas = manager.createNamedQuery("pesquisarAreaInteresseAtiva", AreaInteresse.class)
				.setParameter("pEmpresa", empresa)
				.getResultList();
		
		return areas;
	}
	
	
	
}
