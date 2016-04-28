package br.com.crm.model.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.com.crm.model.entity.Profissao;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.exception.NegocioException;

/**
 * Serviços para Profissões
 * @author Solkam
 * @since 02 AGO 2015
 */
@Stateless
public class ProfissaoService {
	
	@PersistenceContext EntityManager manager;
	
//	@EJB ContactService contactService;
	
	/**
	 * Salva uma profissoa aplicando RN
	 * @param p
	 * @return
	 */
	public Profissao salvarProfissao(Profissao p, Usuario usuario) {
		verifcarSeProfissaoDescricaoJaExiste(p);
		inserirLog(p, usuario);
		return manager.merge( p );
	}
	
	/**
	 * RN que verifica se nome da profissao é unico
	 * @param p
	 */
	private void verifcarSeProfissaoDescricaoJaExiste(Profissao p) {
		Profissao ProfissaoFound = buscarProfissaoPelaDescricao( p.getDescricao() );
		if (ProfissaoFound!=null && !ProfissaoFound.equals(p)) {
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
		verificarSeExistePessoaDaProfissao(p);
		manager.remove( manager.merge(p) );
	}
	
	/**
	 * Verifica se algum contato está associado a esta profissão
	 * @param p
	 */
	private void verificarSeExistePessoaDaProfissao(Profissao p) {
//		List<Contact> contatsFound = contactService.pesquisarContactByProfissao(p);
//		if (!contatsFound.isEmpty()) {
//			throw new NegocioException("Existem Contatos associados a esta Profissão");
//		}
	}

	/**
	 * Busca profissao pela PK
	 * @param id
	 * @return
	 */
	public Profissao buscarProfissaoPorId(Long id) {
		return manager.find(Profissao.class, id);
	}
	
	
	/**
	 * Busca profissao pelo nome
	 * @param descricao
	 * @return null se nao encontrar
	 */
	public Profissao buscarProfissaoPelaDescricao(String descricao) {
		try {
			return manager.createNamedQuery("buscarProfissaoPelaDescricao", Profissao.class)
					.setParameter("pDescricao", descricao)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Pesquisar todas as profissoes
	 * @return
	 */
	public List<Profissao> pesquisarProfissao() {
		return manager.createNamedQuery("pesquisarProfissao", Profissao.class)
				.getResultList();
	}
	
	/**
	 * Pesquisa professoes ativas (para serem usadas em combo)
	 * @return
	 */
	public List<Profissao> pesquisarProfissaoAtiva() {
		List<Profissao> Profissaos = manager.createNamedQuery("pesquisarProfissaoAtiva", Profissao.class)
				.getResultList();
		
		return Profissaos;
	}
	

}
