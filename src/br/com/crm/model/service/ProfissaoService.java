package br.com.crm.model.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.com.crm.model.entity.Empresa;
import br.com.crm.model.entity.Profissao;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.exception.NegocioException;

/**
 * Serviços para Profissões
 * @author Solkam
 * @since 02 mai 2015
 */
@Stateless
public class ProfissaoService {
	
	@PersistenceContext EntityManager manager;
	
	
	/**
	 * Salva uma profissoa aplicando RN
	 * @param profissao
	 * @return
	 */
	public Profissao salvarProfissao(Profissao profissao, Usuario usuario) {
		verifcarSeProfissaoDescricaoJaExiste( usuario.getEmpresa(), profissao );
		inserirLog(profissao, usuario);
		referenciarEmpresaEProfissao(profissao, usuario.getEmpresa() );
		return manager.merge( profissao );
	}
	
	private void referenciarEmpresaEProfissao(Profissao profissao, Empresa empresa) {
		profissao.setEmpresa( empresa );
	}

	/**
	 * RN que verifica se nome da profissao é unico
	 * @param profissao
	 */
	private void verifcarSeProfissaoDescricaoJaExiste(Empresa empresa, Profissao profissao) {
		Profissao ProfissaoFound = buscarProfissaoPelaDescricao( empresa, profissao.getDescricao() );
		if (ProfissaoFound!=null && !ProfissaoFound.equals(profissao)) {
			throw new NegocioException("Já existe Profissão com este nome");
		}
	}
	
	private void inserirLog(Profissao p, Usuario u) {
		if (p.isTransient()) {
			p.getInfoLog().setCriadoEm( new Date() );
			p.getInfoLog().setCriadoPor( u.getEmail() );
		} else {
			p.getInfoLog().setAtualizadoEm( new Date() );
			p.getInfoLog().setAtualizadoPor( u.getEmail() );
		}
	}

	
	/**
	 * Remove uma profissao aplicando RN
	 * @param p
	 */
	public void removerProfissao(Profissao p) {
		manager.remove( manager.merge(p) );
	}
	

	/**
	 * Busca profissao pela PK
	 * @param id
	 * @return
	 */
	public Profissao buscarProfissaoPorId(Integer id) {
		return manager.find(Profissao.class, id);
	}
	
	
	/**
	 * Busca profissao pelo nome
	 * @param descricao
	 * @return null se nao encontrar
	 */
	public Profissao buscarProfissaoPelaDescricao(Empresa empresa, String descricao) {
		try {
			return manager.createNamedQuery("buscarProfissaoPelaDescricao", Profissao.class)
					.setParameter("pDescricao", descricao)
					.setParameter("pEmpresa", empresa )
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Pesquisar todas as profissoes
	 * @return
	 */
	public List<Profissao> pesquisarProfissao(Empresa empresa) {
		return manager.createNamedQuery("pesquisarProfissao", Profissao.class)
				.setParameter("pEmpresa", empresa)
				.getResultList();
	}
	
	/**
	 * Pesquisa professoes ativas (para serem usadas em combo)
	 * @return
	 */
	public List<Profissao> pesquisarProfissaoAtiva(Empresa empresa) {
		List<Profissao> Profissaos = manager.createNamedQuery("pesquisarProfissaoAtiva", Profissao.class)
				.setParameter("pEmpresa", empresa)
				.getResultList();
		
		return Profissaos;
	}
	

}
