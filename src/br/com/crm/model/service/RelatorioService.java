package br.com.crm.model.service;

import static br.com.crm.model.util.QueryUtil.isNotBlank;
import static br.com.crm.model.util.QueryUtil.isNotEmpty;
import static br.com.crm.model.util.QueryUtil.isNotNegative;
import static br.com.crm.model.util.QueryUtil.isNotNull;
import static br.com.crm.model.util.QueryUtil.toLikeMatchModeANY;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.crm.model.entity.AreaInteresse;
import br.com.crm.model.entity.Campanha;
import br.com.crm.model.entity.Empresa;
import br.com.crm.model.entity.Endereco;
import br.com.crm.model.entity.Genero;
import br.com.crm.model.entity.InteracaoCampanha;
import br.com.crm.model.entity.Maturidade;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.Profissao;
import br.com.crm.model.entity.Usuario;


/**
 * Serviços de Relatorios de pessoas
 * @author Solkam
 * @since 11 mai 2016
 */
@Stateless
public class RelatorioService {
	
	@PersistenceContext EntityManager manager;

	
	/**
	 * Pesquisa contatos para o relatorio de contatos
	 * @param filtroNascimentoDia
	 * @param filtroNascimentoMes
	 * @param filtroNascimentoAno
	 * @param filtroGenero
	 * @return
	 */
	public List<Pessoa> pesquisarPessoaPelosFiltros(Integer filtroNascimentoDia
												   ,Integer filtroNascimentoMes
												   ,Integer filtroNascimentoAno
												   ,Genero filtroGenero
												   ,String filtroCidade
												   ,String filtroUF
												   ,List<AreaInteresse> filtroAreaInteresses
												   ,List<Profissao> filtroProfissoes
											       ,String filtroIndicadoPor
											       ,List<Maturidade> filtroMaturidades
					                               ) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		Predicate conjunction = builder.conjunction();
		//dia nascimento
		if (isNotNegative(filtroNascimentoDia)) {
			conjunction = builder.and(conjunction
					,builder.equal( root.<Integer>get("nascimentoDia") , filtroNascimentoDia)
					);
		}
		//mes nascimento
		if (isNotNegative(filtroNascimentoMes)) {
			conjunction = builder.and(conjunction
					,builder.equal( root.<Integer>get("nascimentoMes") , filtroNascimentoMes)
					);
		}
		//ano nascimento
		if (isNotNegative(filtroNascimentoAno)) {
			conjunction = builder.and(conjunction
					,builder.equal( root.<Integer>get("nascimentoAno") , filtroNascimentoAno)
					);
		}
		//sexo
		if (isNotNull(filtroGenero)) {
			conjunction = builder.and(conjunction
					,builder.equal( root.<Genero>get("genero") , filtroGenero)
					);
		}
		//cidade
		if (isNotBlank(filtroCidade)) {
			conjunction = builder.and(conjunction
					,builder.like( root.<Endereco>get("endereco").<String>get("enderecoCidade") , toLikeMatchModeANY(filtroCidade) )
					);
		}
		//estado
		if (isNotBlank(filtroUF)) {
			conjunction = builder.and(conjunction
					,builder.like( root.<Endereco>get("endereco").<String>get("enderecoUF") , toLikeMatchModeANY(filtroUF) )
					);
		}
		//areas de interesse
		if (isNotEmpty(filtroAreaInteresses)) {
			Join<Pessoa, AreaInteresse> joinAreaInteresse = root.<Pessoa,AreaInteresse>join("areasInteresse");
			conjunction = builder.and(conjunction
					,joinAreaInteresse.in( filtroAreaInteresses )
					);
		}
		//professions
		if (isNotEmpty(filtroProfissoes)) {
			Join<Pessoa, Profissao> joinProfissao = root.<Pessoa,Profissao>join("profissoes");
			conjunction = builder.and(conjunction
					,joinProfissao.in( filtroProfissoes )
					);
		}
		//indicado por
		if (isNotNull(filtroIndicadoPor)) {
			conjunction = builder.and(conjunction
					,builder.equal( root.<Pessoa>get("indicadoPor") , filtroIndicadoPor)
					);
		}
		//maturidades
		if (isNotEmpty(filtroMaturidades)) {
			conjunction = builder.and(conjunction
					,root.<Maturidade>get("maturidade").in( filtroMaturidades )
					);
		}

		//where e order by
		criteria.distinct( true );
		criteria.where( conjunction );
		
		criteria.orderBy( builder.asc(root.get("primeiroNome"))
				        , builder.asc(root.get("sobreNome"))
				);
		
		return manager.createQuery(criteria).getResultList();
	}
	
	
	
	/**
	 * Pesquisa pelas interações de campanhas
	 * @param filtroEmpresa
	 * @return
	 */
	public List<InteracaoCampanha> pesquisarInteracaoCampanhaPeloFiltros(Empresa filtroEmpresa
																		,List<Campanha> filtroCampanhas
																		,List<Usuario> filtroResponsaveis
																		,List<Pessoa> filtroPessoas
																		) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<InteracaoCampanha> criteria = builder.createQuery(InteracaoCampanha.class);
		Root<InteracaoCampanha> root = criteria.from(InteracaoCampanha.class);
		
		Predicate conjunction = builder.conjunction();
		
		Join<InteracaoCampanha, Campanha> joinCampanha = root.<InteracaoCampanha, Campanha>join("campanha");

		//1.empresa
		conjunction = builder.and(conjunction
				, builder.equal(joinCampanha.<Empresa>get("empresa"), filtroEmpresa) 
				);
		//2.campanhas
		if (isNotEmpty(filtroCampanhas)) {
			conjunction = builder.and(conjunction
					,joinCampanha.in( filtroCampanhas )
					);
		}
		//3.responsaveis
		if (isNotEmpty(filtroResponsaveis)) {
			conjunction = builder.and(conjunction
					,root.<Usuario>get("responsavel").in( filtroResponsaveis )
					);
		}
		//3.pessoa
		if (isNotEmpty(filtroPessoas)) {
			conjunction = builder.and(conjunction
					,root.<Pessoa>get("pessoa").in( filtroPessoas )
					);
		}
		
		//fechando...
		criteria.where( conjunction );
		criteria.orderBy( builder.desc( root.<Date>get("data") ));
		
		List<InteracaoCampanha> interacoes = manager.createQuery( criteria ).getResultList();
		return interacoes;
		
	}

}
