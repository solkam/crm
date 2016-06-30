package br.com.crm.model.service;

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
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.exception.NegocioException;
import br.com.crm.model.util.QueryUtil;

/**
 * Serviços de negocio para empresa
 * @author Solkam
 * @since 05 mai 2016
 */
@Stateless
public class EmpresaService {
	
	@PersistenceContext EntityManager manager;


	/**
	 * Salva empresa com os devidos logs
	 * @param empresa
	 * @param usuario
	 * @return
	 */
	public Empresa salvarEmpresa(Empresa empresa, Usuario usuarioSalvador) {
		//RNs
		verificarUnicidadeDoNomeDaEmpresa(empresa);
		verificarUnicidadeDoCnpjDaEmpresa(empresa);
		//log
		empresa.inserirLogInfo(usuarioSalvador);
		//salva
		return manager.merge( empresa );
	}
	

	/**
	 * RN para garantir a unicidade dos nomes de empresas
	 * @param empresa
	 */
	private void verificarUnicidadeDoNomeDaEmpresa(Empresa empresa) {
		Empresa empresaEncontrada = buscarEmpresaPeloNome(empresa.getNome());
		if (empresaEncontrada!=null && !empresaEncontrada.equals(empresa)) {
			throw new NegocioException("Já existe empresa com este nome");
		}
	}
	
	/**
	 * RN para garantir a unicidade do CNPJ de empresas
	 * @param empresa
	 */
	private void verificarUnicidadeDoCnpjDaEmpresa(Empresa empresa) {
		Empresa empresaEncontrada = buscarEmpresaPeloCnpj( empresa.getCnpj() );
		if (empresaEncontrada!=null && !empresaEncontrada.equals(empresa)) {
			throw new NegocioException("Já existe empresa com este CNPJ");
		}
	}


	/**
	 * Busca empresa pelo nome.
	 * Usando em RN.
	 * @param nome
	 * @return
	 */
	private Empresa buscarEmpresaPeloNome(String nome) {
		try {
			return manager.createNamedQuery("buscarEmpresaPeloNome", Empresa.class)
					.setParameter("pNome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	/**
	 * Busca empresa pelo CNPJ.
	 * Usado em RN.
	 * @param cnpj
	 * @return
	 */
	private Empresa buscarEmpresaPeloCnpj(String cnpj) {
		try {
			return manager.createNamedQuery("buscarEmpresaPeloCnpj", Empresa.class)
					.setParameter("pCnpj", cnpj)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	
	
	/**
	 * Remove empresa aplicando RN
	 * @param empresa
	 */
	public void removerEmpresa(Empresa empresa) {
		empresa = manager.find(Empresa.class, empresa.getId() );
		verificarSeExistemUsuariosDaEmpresa( empresa );
		manager.remove( empresa );
	}


	/**
	 * RN que verifica se existem usuários vinculados a empresa
	 * que se está removendo.
	 * @param empresa
	 */
	private void verificarSeExistemUsuariosDaEmpresa(Empresa empresa) {
		if (!empresa.getUsuarios().isEmpty()) {
			throw new NegocioException("Existem usuários vinculados a esta empresa");
		}
		
	}

	
	/**
	 * Força a carga das associacoes de Empresa:
	 * - area de interesse
	 * - profissões 
	 * - maturidades
	 * @param empresa
	 * @return
	 */
	public Empresa recarregarEmpresa(Empresa empresa) {
		empresa = manager.find(Empresa.class, empresa.getId() );
		empresa.getAreasInteresse().size();
		empresa.getProfissoes().size();
		empresa.getMaturidades().size();
		return empresa;
	}
	

	/**
	 * Pesquisa empresas pelos filtros de pesquisar usando criteria
	 * @param filtroNome
	 * @param filtroCnpj
	 * @return
	 */
	public List<Empresa> pesquisarEmpresaPorFiltro(String filtroNome, String filtroCnpj) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Empresa> criteria = builder.createQuery(Empresa.class);
		Root<Empresa> root = criteria.from(Empresa.class);
		
		Predicate conjunction = builder.conjunction();
		if (QueryUtil.isNotBlank(filtroNome) ) {
			conjunction = builder.and(conjunction, builder.equal(root.<String>get("nome"), filtroNome));
		}
		
		if (QueryUtil.isNotBlank(filtroCnpj)) {
			conjunction = builder.and(conjunction, builder.equal(root.<String>get("cnpj"), filtroCnpj));
		}
		
		criteria.where(conjunction);
		
		criteria.orderBy( builder.asc(root.<String>get("nome")) );
		
		return manager.createQuery( criteria ).getResultList();
	}
	
	
	/**
	 * Pesquisas as empresas ativas
	 * @return
	 */
	public List<Empresa> pesquisarEmpresasAtivas() {
		return manager.createNamedQuery("pesquisarEmpresaAtiva", Empresa.class)
				.getResultList();
	}


	
	

}
