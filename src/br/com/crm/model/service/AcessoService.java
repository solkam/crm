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

import br.com.crm.model.entity.Empresa;
import br.com.crm.model.entity.Perfil;
import br.com.crm.model.entity.Usuario;


/**
 * Servicos de negocio para Acesso
 * @author Solkam
 * @since 26 abr 2016
 */
@Stateless
public class AcessoService {
	
	@PersistenceContext EntityManager manager;
	
	
	/* *******
	 * USUARIO
	 *********/
	
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
	 * Autorizar usuario significa carregar a lista
	 * de funcionalidades do seu perfil
	 * @param usuario
	 * @return
	 */
	public Usuario autorizarUsuario(Usuario usuario) {
		usuario = manager.find(Usuario.class, usuario.getId() );
		usuario.getPerfil().getFuncionalidades().size();
		return usuario;
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
	 * Busca usuario pela PK
	 * @param id
	 * @return
	 */
	public Usuario buscarUsuarioPeloId(Integer id) {
		return manager.find(Usuario.class, id);
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


	/**
	 * Pesquia usuario pelo empresa.
	 * (usado para atribuir responsaveis a uma campanha)
	 * @param empresa
	 * @return
	 */
	public List<Usuario> pesquisarUsuarioPeloEmpresa(Empresa empresa) {
		return manager.createNamedQuery("pesquisarUsuarioPeloEmpresa", Usuario.class)
				.setParameter("pEmpresa", empresa)
				.getResultList();
	}
	
	
	
	/* ******
	 * PERFIL
	 ********/
	
	/**
	 * Salva um perfil
	 * @param p
	 * @return
	 */
	public Perfil salvarPerfil(Perfil p) {
		return manager.merge( p );
	}
	
	
	public void removerPerfil(Perfil perfil) {
		perfil = manager.find(Perfil.class, perfil.getCodigo() );
		manager.remove( perfil );
	}
	
	
	public Perfil recarregarPerfil(Perfil perfil) {
		perfil = manager.find(Perfil.class, perfil.getCodigo() );
		perfil.getFuncionalidades().size();
		return perfil;
	}
	
	/**
	 * Pesquisa perfils usando criteria
	 * @param flagVisivel
	 * @return
	 */
	public List<Perfil> pesquisarPerfilPeloFiltro(Boolean flagVisivel) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Perfil> criteria = builder.createQuery(Perfil.class);
		Root<Perfil> root = criteria.from(Perfil.class);
		
		Predicate conjunction = builder.conjunction();
		//1.flag visivel
		if (isNotNull(flagVisivel)) {
			conjunction = builder.and(conjunction
					, builder.equal(root.<Boolean>get("flagVisivel"), flagVisivel)
				);
		}
		//where
		criteria.where(conjunction);
		
		return manager.createQuery(criteria).getResultList();
	}


	

}
