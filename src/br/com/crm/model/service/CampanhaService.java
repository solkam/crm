package br.com.crm.model.service;

import static br.com.crm.model.util.QueryUtil.isNotBlank;
import static br.com.crm.model.util.QueryUtil.isNotNull;
import static br.com.crm.model.util.QueryUtil.toLikeMatchModeANY;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.crm.model.entity.Campanha;
import br.com.crm.model.entity.Empresa;
import br.com.crm.model.entity.InteracaoCampanha;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.Produto;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.exception.NegocioException;

/**
 * Serviços de negócio para Camapanha
 * @author Solkam
 * @since 12 mai 2016
 */
@Stateless
public class CampanhaService {
	
	@PersistenceContext EntityManager manager;
	
	/**
	 * Salva campanha aplicando RN e inserindo log 
	 * @param campanha
	 * @param usuario
	 * @return
	 */
	public Campanha salvarCampanha(Campanha campanha, Usuario usuario) {
		referenciarEmpresaECampanha(campanha, usuario.getEmpresa() );
		verificarUnicidadeDaDescricaoDaCampanha(campanha);
		inserirLog(campanha, usuario);
		return manager.merge(campanha);
	}


	/**
	 * RN para garantir unicidade da descrição da campanha dentro da empresa
	 * @param campanha
	 */
	private void verificarUnicidadeDaDescricaoDaCampanha(Campanha campanha) {
		Campanha campanhaEncontrada = buscarCampanhaPelaDescricao(campanha);
		if (campanhaEncontrada!=null && !campanhaEncontrada.equals(campanha)) {
			throw new NegocioException("Já existe campanha com esta descrição");
		}
	}


	/**
	 * Fecha a referencia entre campanha e empresa
	 * @param campanha
	 * @param empresa
	 */
	private void referenciarEmpresaECampanha(Campanha campanha, Empresa empresa) {
		campanha.setEmpresa( empresa );
	}


	/**
	 * Insere logs de criacao ou atualização
	 * @param campanha
	 * @param usuario
	 */
	private void inserirLog(Campanha campanha, Usuario usuario) {
		if (campanha.isTransient()) {
			campanha.getInfoLog().setCriadoEm( new Date() );
			campanha.getInfoLog().setCriadoPor( usuario.getEmail() );
		} else {
			campanha.getInfoLog().setAtualizadoEm( new Date() );
			campanha.getInfoLog().setAtualizadoPor( usuario.getEmail() );
		}
	}
	
	
	/**
	 * Remove campanha aplicando RN
	 * @param campanha
	 */
	public void removerCampanha(Campanha campanha) {
		campanha = manager.find(Campanha.class, campanha.getId() );
		verificarSeCampanhaTemInteracoes(campanha);
		manager.remove( campanha );
	}
	
	/**
	 * Remover uma pessoa da campanha
	 * @param campanha
	 * @param pessoa
	 */
	public void removerPessoaDaCampanha(Campanha campanha, Pessoa pessoa) {
		campanha = manager.merge(campanha);
		campanha.getPessoas().remove( pessoa );
	}
	
	/**
	 * Remove um produto da campanha
	 * @param campanha
	 * @param usuario
	 */
	public void removerProdutoDaCampanha(Campanha campanha, Produto produto) {
		campanha = manager.merge( campanha );
		campanha.getProdutos().remove( produto );
	}
	
	
	/**
	 * Verifica se campanha já possui intereção
	 * @param campanha
	 */
	private void verificarSeCampanhaTemInteracoes(Campanha campanha) {
		if (!campanha.getInteracoes().isEmpty()) {
			throw new NegocioException("Campanha possui interações");
		}
	}

	
	/**
	 * Recarrega campanha com seus relacionamentos
	 * @param campanha
	 * @return
	 */
	public Campanha recarregarCampanha(Campanha campanha) {
		//traz para gerenciado
		campanha = manager.find(Campanha.class, campanha.getId() );
		//força a carga dos relacionamentos
		campanha.getResponsaveis().size();
		campanha.getPessoas().size();
		campanha.getProdutos().size();
		campanha.getInteracoes().size();

		return campanha;
	}
	


	/**
	 * Busca a campanha pela descrição dentra da empresa
	 * (usando em RN)
	 * @param campanha
	 * @return
	 */
	private Campanha buscarCampanhaPelaDescricao(Campanha campanha) {
		try {
			return manager.createNamedQuery("buscarCampanhaPelaDescricao", Campanha.class)
					.setParameter("pEmpresa", campanha.getEmpresa() )
					.setParameter("pDescricao", campanha.getDescricao() )
					.getSingleResult()
					;
		} catch (NoResultException e) {
			return null;
		}
	}

	 /**
	  * Pesquisa campanhas pelos filtros
	  * @param filtroFlagAtivo
	  * @param filtroDescricao
	  * @return
	  */
	public List<Campanha> pesquisarCampanhaPelosFiltros(Empresa empresa, Boolean filtroFlagAtivo, String filtroDescricao) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Campanha> criteria = builder.createQuery(Campanha.class);
		Root<Campanha> root = criteria.from(Campanha.class);
		
		Predicate conjunction = builder.conjunction();
		//empresa
		conjunction = builder.and(conjunction
				, builder.equal(root.<Empresa>get("empresa"), empresa)
				);
		
		
		//1.flag ativo
		if (isNotNull(filtroFlagAtivo)) {
			conjunction = builder.and(conjunction
					, builder.equal(root.<Boolean>get("flagAtivo"), filtroFlagAtivo)
					);
		}
		//2.descricao
		if (isNotBlank(filtroDescricao)) {
			conjunction = builder.and(conjunction
					, builder.like(root.<String>get("descricao"), toLikeMatchModeANY(filtroDescricao))
					);
		}
		
		criteria.where(conjunction);
		criteria.orderBy( builder.asc( root.<Date>get("dataInicio") ) );

		return manager.createQuery(criteria).getResultList();
	}
	
	
	/**
	 * Pesquisa somente as campanhas ativas e dentro do prazo
	 * @return
	 */
	public List<Campanha> pesquisarCampanhaAtivaEDentroDoPrazo(Empresa empresa) {
		return manager.createNamedQuery("pesquisarCampanhaAtivaEDentroDoPrazo", Campanha.class)
				.setParameter("pEmpresa", empresa)
				.getResultList();
	}


	/**
	 * Adiciona pessoas a uma campanha
	 * @param pessoasSelecionadas
	 * @param campanhaSelecionada
	 */
	public void adicionarPessoasACampanha(List<Pessoa> pessoasSelecionadas, Campanha campanhaSelecionada) {
		//traz para gerenciado
		campanhaSelecionada = manager.find(Campanha.class, campanhaSelecionada.getId() );
		//acrescenta as existentes
		Set<Pessoa> pessoas = campanhaSelecionada.getPessoas();
		pessoas.addAll( pessoasSelecionadas );
	}


	/**
	 * Adiciona uma lista de produtos a uma campanha
	 * @param produtos
	 * @param campanha
	 */
	public void adicionarProdutosACampanha(List<Produto> produtosSelecionados, Campanha campanha) {
		//traz para gerenciado
		campanha = manager.find(Campanha.class, campanha.getId() );
		//acrescenta aos existentes
		Set<Produto> produtos = campanha.getProdutos();
		produtos.addAll( produtosSelecionados );
	}
	
	
	/**
	 * Pesquisa campanha do responsavel
	 * @param usuario
	 * @return
	 */
	public List<Campanha> pesquisarCampanhaAtivaPeloResponsavel(Usuario usuario) {
		return manager.createNamedQuery("pesquisarCampanhaAtivaPeloResponsavel", Campanha.class)
				.setParameter("pResponsavel", usuario)
				.getResultList();
	}


	
	
	/* **********
	 * Interações
	 ************/
	
	/**
	 * Salva interação da campanha
	 * @param interacao
	 * @return
	 */
	public InteracaoCampanha salvarInteracaoCampanha(InteracaoCampanha interacao) {
		return manager.merge( interacao );
	}


	
	
	

}
