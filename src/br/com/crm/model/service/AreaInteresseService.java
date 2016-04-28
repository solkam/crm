package br.com.crm.model.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.com.crm.model.entity.AreaInteresse;
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
		verificaSeAreaInteresseDescricaoJaExiste(area);
		inserirInfoLog( area, usuario );
		return manager.merge( area );
	}
	
	


	/**
	 * RN para garantir a unicidade da descrição de areas de interesse
	 * @param area
	 */
	private void verificaSeAreaInteresseDescricaoJaExiste(AreaInteresse area) {
		AreaInteresse foundArea = buscarAreaInteressePorDescricao(area.getDescricao() );
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
	public AreaInteresse buscarAreaInteressePorId(Long id) {
		return manager.find(AreaInteresse.class, id);
	}
	

	/**
	 * Busca uma area de interesse pelo nome
	 * @param description
	 * @return
	 */
	private AreaInteresse buscarAreaInteressePorDescricao(String description) {
		try {
			return manager.createNamedQuery("buscarAreaInteressePorDescricao", AreaInteresse.class)
					.setParameter("pDescricao", description)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	/**
	 * Pesquisa por todas as areas de interesses
	 * @return
	 */
	public List<AreaInteresse> pesquisarAreaInteresse(){
		 return	 manager.createNamedQuery("pesquisarAreaInteresse", AreaInteresse.class)
					.getResultList();			
	}
	
	/**
	 * Pesquisa pelas areas ativos somente.
	 * @return
	 */
	public List<AreaInteresse> pesquisarAreaInteresseAtiva() {
		List<AreaInteresse> areas = manager.createNamedQuery("pesquisarAreaInteresseAtiva", AreaInteresse.class)
				.getResultList();
		
		return areas;
	}
	
	
	
}
