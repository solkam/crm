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
import br.com.crm.model.exception.NegocioException;


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
	 * Salva instância de usuario
	 * @param usuario usuario em si propriamente dito
	 * @param usuarioSalvador aquele que está criando ou atualizado
	 * @return usuario salvo
	 */
	public Usuario salvarUsuario(Usuario usuario, Usuario usuarioSalvador) {
		usuario.inserirInfoLog( usuarioSalvador );
		return manager.merge(usuario);
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
	 * @param login
	 * @param senha
	 * @return
	 */
	public Usuario buscarUsuarioPeloLoginESenha(String login, String senha) {
		try {
			return manager.createNamedQuery("buscarUsuarioPeloLoginESenha", Usuario.class)
					.setParameter("pLogin", login)
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
	 * @param perfil
	 * @return
	 */
	public Perfil salvarPerfil(Perfil perfil, Usuario usuarioCriadorOrAtualizador) {
		verificarSeCodigoPerfilJaExiste(perfil);
		verificarSeNomePerfilJaExiste(perfil);
		inserirInfoLog(perfil, usuarioCriadorOrAtualizador);
		return manager.merge( perfil );
	}
	
	
	private void inserirInfoLog(Perfil perfil, Usuario usuarioCriadorOrAtualizador) {
		if (perfil.isTransient()) {
			perfil.getInfoLog().setCriadoEm( new Date() );
			perfil.getInfoLog().setCriadoPor( usuarioCriadorOrAtualizador.getDescricaoCompleta() );
		}else {
			perfil.getInfoLog().setAtualizadoEm( new Date() );
			perfil.getInfoLog().setAtualizadoPor( usuarioCriadorOrAtualizador.getDescricaoCompleta() );
		}
	}


	/**
	 * RN que verifica se código do perfil já existe.
	 * (aplicada apenas para objetos transientes).
	 * @param perfil
	 */
	private void verificarSeCodigoPerfilJaExiste(Perfil perfil) {
		if (perfil.isTransient()) {
			Perfil perfilEncontrado = buscarPerfilPeloCodigo(perfil.getCodigo());
			if (perfilEncontrado!=null) {
				throw new NegocioException("Código do perfil já existe");
			}
		}
	}
			
	
	private void verificarSeNomePerfilJaExiste(Perfil perfil) {
		Perfil perfilEncontrado = buscarPerfilPeloNome( perfil.getNome() );
		if (perfilEncontrado!=null && !perfilEncontrado.equals(perfil)) {
			throw new NegocioException("Nome do perfil já existe");
		}
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
	
	
	private Perfil buscarPerfilPeloCodigo(String codigo) {
		try {
			return manager.createNamedQuery("buscarPerfilPeloCodigo", Perfil.class)
					.setParameter("pCodigo", codigo)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	private Perfil buscarPerfilPeloNome(String nome) {
		try {
			return manager.createNamedQuery("buscarPerfilPeloNome", Perfil.class)
					.setParameter("pNome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
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
