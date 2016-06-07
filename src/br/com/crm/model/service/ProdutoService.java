package br.com.crm.model.service;

import static br.com.crm.model.util.QueryUtil.isNotNull;
import static br.com.crm.model.util.QueryUtil.isNotBlank;
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

import br.com.crm.model.entity.CategoriaProduto;
import br.com.crm.model.entity.Empresa;
import br.com.crm.model.entity.Produto;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.exception.NegocioException;


/**
 * Servicos de negocio para Produto
 * @author Solkam
 * @since 10 mai 2016
 */
@Stateless
public class ProdutoService {
	
	@PersistenceContext EntityManager manager;
	
	
	/**
	 * Salva o produto, aplicando RN
	 * @param produto
	 * @return
	 */
	public Produto salvarProduto(Produto produto, Usuario usuario) {
		verificarDescricaoDoProdutoEhUnica(produto, usuario.getEmpresa() );
		referenciarEmpresa(produto, usuario.getEmpresa() );
		inserirInfoLog(produto, usuario);
		return manager.merge( produto );
	}
	
	
	private void inserirInfoLog(Produto produto, Usuario usuario) {
		if (produto.isTransient()) {
			produto.getInfoLog().setCriadoEm( new Date() );
			produto.getInfoLog().setCriadoPor( usuario.getEmail() );
		} else {
			produto.getInfoLog().setAtualizadoEm( new Date() );
			produto.getInfoLog().setAtualizadoPor( usuario.getEmail() );
		}
	}


	/**
	 * Setar a instancia de empresa em produto
	 * @param produto
	 * @param empresa
	 */
	private void referenciarEmpresa(Produto produto, Empresa empresa) {
		produto.setEmpresa( empresa );
	}


	/**
	 * Verifica se nome do produto eh unico
	 * @param produto
	 */
	private void verificarDescricaoDoProdutoEhUnica(Produto produto, Empresa empresa) {
		Produto foundProduto = buscarProdutoPelaDescricao(empresa, produto.getDescricao() );
		if (foundProduto!=null && !produto.equals(foundProduto)) {
			throw new NegocioException("Já existe produto com esta descrição");
		}
	}


	/**
	 * Remove o produto, aplicando RN
	 * @param produto
	 */
	public void removerProduto(Produto produto) {
		produto = manager.merge( produto );
		manager.remove( produto );
	}

	
	/**
	 * Recarrega produto 
	 * @param produto
	 */
	public Produto recarregarProduto(Produto produto) {
		produto = manager.find(Produto.class, produto.getId() );
		return produto;
	}
	
	
	/**
	 * Busca produto pela PK
	 * (usado no converter)
	 * @param id
	 * @return
	 */
	public Produto buscarProdutoPeloId(Integer id) {
		return manager.find(Produto.class, id);
	}

	/**
	 * Busca um produto pelo nome
	 * @param descricao
	 * @return
	 */
	private Produto buscarProdutoPelaDescricao(Empresa empresa, String descricao) {
		try {
			return manager.createNamedQuery("buscarProdutoPelaDescricao", Produto.class)
					.setParameter("pDescricao", descricao)
					.setParameter("pEmpresa", empresa)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	/**
	 * Pesquisa todos os produtos
	 * @return
	 */
	public List<Produto> pesquisarProduto(Empresa empresa) {
		return manager.createNamedQuery("pesquisarProduto", Produto.class)
				.setParameter("pEmpresa", empresa)
				.getResultList();
	}
	
	
	/**
	 * Pesquia produto segundo filtros de pesquisa usando criteria
	 * @param statusList
	 * @param categoryList
	 * @param ano
	 * @return
	 */
	public List<Produto> pesquisarProdutoPelosFiltros(Empresa empresa
													, Boolean flagAtivo
													, String descricao
													, CategoriaProduto categoria) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);
		
		Predicate conjunction = builder.conjunction();
		//1.empresa
		conjunction = builder.and(conjunction,
				builder.equal( root.<Empresa>get("empresa"), empresa)
			);
		
		//2.flag ativo
		if (isNotNull(flagAtivo)) {
			conjunction = builder.and(conjunction
					,builder.equal( root.<Boolean>get("flagAtivo"), flagAtivo)
				);
		}
		//3.descricao
		if (isNotBlank(descricao)) {
			conjunction = builder.and(conjunction
					,builder.like( root.<String>get("descricao"), toLikeMatchModeANY(descricao) )
				);
		}
		//4.categoria
		if (isNotNull(categoria)) {
			conjunction = builder.and(conjunction
					,builder.equal(root.<CategoriaProduto>get("categoria"), categoria)
				);
		}
		
		criteria.where( conjunction );
		criteria.orderBy( builder.asc(root.<String>get("descricao")) );
		
		List<Produto> produtos = manager.createQuery(criteria).getResultList();
		return produtos;
	}
	

	/**
	 * Pesquisa somente produtos ativo
	 * @param b
	 * @return
	 */
	public List<Produto> pesquisarProdutoAtivo(Empresa empresa) {
		return manager.createNamedQuery("pesquisarProdutoAtivo", Produto.class)
				.setParameter("pEmpresa", empresa)
				.getResultList();
	}
	
	
	
	/* *********
	 * CATEGORIA
	 ***********/
	
	public CategoriaProduto salvarCategoriaProduto(CategoriaProduto categoria, Usuario usuario) {
		verificarSeDescricaoDaCategoriaEhUnica(categoria, usuario.getEmpresa() );
		categoria.inserirInfoLog(usuario);
		categoria.setEmpresa( usuario.getEmpresa() );
		return manager.merge( categoria );
	}
	
	private void verificarSeDescricaoDaCategoriaEhUnica(CategoriaProduto categoria, Empresa empresa) {
		CategoriaProduto categoriaEncontrada = buscarCategoriaProdutoPelaDescricao(empresa, categoria.getDescricao());
		if (categoriaEncontrada!=null && !categoriaEncontrada.equals(categoria)) {
			throw new NegocioException("Descrição da categoria já existe");
		}
	}




	public void removerCategoriaProduto(CategoriaProduto categoria) {
		categoria = manager.find(CategoriaProduto.class, categoria.getId() );
		verificarSeExisteProdutoDaCategoria( categoria );
		manager.remove( categoria );
	}


	private void verificarSeExisteProdutoDaCategoria(CategoriaProduto categoria) {
		List<Produto> produtosEncontrados = pesquisarProdutoPelaCategoria(categoria.getEmpresa(), categoria);
		if (!produtosEncontrados.isEmpty()) {
			throw new NegocioException("Categoria possui produtos associados");
		}
	}

	
	public CategoriaProduto buscarCategoriaProdutoPeloId(Integer id) {
		return manager.find(CategoriaProduto.class, id);
	}
	

	private CategoriaProduto buscarCategoriaProdutoPelaDescricao(Empresa empresa, String descricao) {
		try {
			return manager.createNamedQuery("buscarCategoriaProdutoPelaDescricao", CategoriaProduto.class)
					.setParameter("pEmpresa", empresa)
					.setParameter("pDescricao", descricao)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	
	private List<Produto> pesquisarProdutoPelaCategoria(Empresa empresa, CategoriaProduto categoria) {
		return manager.createNamedQuery("pesquisarProdutoPelaCategoria", Produto.class)
				.setParameter("pEmpresa", empresa)
				.setParameter("pCategoria", categoria)
				.getResultList();
	}
	
	
	public List<CategoriaProduto> pesquisarCategoriaProdutoPelosFiltros(Empresa empresa, Boolean flagAtivo) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<CategoriaProduto> criteria = builder.createQuery(CategoriaProduto.class);
		Root<CategoriaProduto> root = criteria.from( CategoriaProduto.class );
		
		Predicate conjunction = builder.conjunction();
		//1.empresa
		conjunction = builder.and(conjunction
				,builder.equal(root.<Empresa>get("empresa"), empresa)
			);
		//2.flag ativo
		if (flagAtivo!=null) {
			conjunction = builder.and(conjunction
				,builder.equal(root.<Boolean>get("flagAtivo"), flagAtivo) 
			);
		}
		
		//where e order by
		criteria.where( conjunction);
		criteria.orderBy( builder.asc(root.<String>get("descricao")) );

		return manager.createQuery(criteria).getResultList();
	}

}