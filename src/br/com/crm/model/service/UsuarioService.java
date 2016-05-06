package br.com.crm.model.service;

import static br.com.crm.model.util.QueryUtil.isNotBlank;
import static br.com.crm.model.util.QueryUtil.isNotNull;
import static br.com.crm.model.util.QueryUtil.toLikeMatchModeANY;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.crm.model.entity.Perfil;
import br.com.crm.model.entity.Usuario;


/**
 * Servicos de negocio para Usuario
 * @author Solkam
 * @since 26 abr 2016
 */
@Stateless
public class UsuarioService {
	
	@PersistenceContext EntityManager manager;
	
	/**
	 * Salva instancia de usuario
	 * @param usuario
	 * @return
	 */
	public Usuario salvarUsuario(Usuario usuario, Usuario usuarioSalvador) {
		inserirInfoLog(usuario, usuarioSalvador);
		return manager.merge(usuario);
	}
	
	
	private void inserirInfoLog(Usuario usuario, Usuario usuarioSalvador) {
		if (usuario.isTransient()) {
			usuario.getInfoLog().setCriadoEm( new Date() );
			usuario.getInfoLog().setCriadoPor( usuarioSalvador.getEmail() );
		} else {
			usuario.getInfoLog().setAtualizadoEm( new Date() );
			usuario.getInfoLog().setAtualizadoPor( usuarioSalvador.getEmail() );
		}
	}


	/**
	 * Remove instancia de usuario
	 * @param usuario
	 */
	public void removerUsuario(Usuario usuario) {
		manager.remove( manager.merge(usuario) );
	}
	
	
	/**
	 * Busca um usuario pelo email e senha. Usado na autentica��o
	 * @param email
	 * @param senha
	 * @return
	 */
	public Usuario buscarUsuarioPeloEmailESenha(String email, String senha) {
		try {
			return manager.createNamedQuery("buscarUsuarioPeloEmailESenha", Usuario.class)
					.setParameter("pEmail", email)
					.setParameter("pSenha", senha)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	/**
	 * Pesquisar usuario pelos filtros usando criteria
	 * @return
	 */
	public List<Usuario> pesquisarUsuarioPelosFiltros(String email, Perfil profile) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		
		Predicate conjunction = builder.conjunction();
		//1.email
		if (isNotBlank(email)) {
			conjunction = builder.and(conjunction, 
					builder.like(root.<String>get("email"), toLikeMatchModeANY(email))
				);
		}
		//2.profile
		if (isNotNull(profile)) {
			conjunction = builder.and(conjunction, 
					builder.equal(root.<Perfil>get("perfil"), profile)
				);
		}
		criteria.where( conjunction );
		criteria.orderBy( builder.asc(root.<String>get("email")) );
		return manager.createQuery(criteria).getResultList();
	}
	

}
