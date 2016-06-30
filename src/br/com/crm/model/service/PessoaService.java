package br.com.crm.model.service;

import static br.com.crm.model.util.QueryUtil.isNotBlank;
import static br.com.crm.model.util.QueryUtil.toLikeMatchModeSTART;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.crm.model.entity.AreaInteresse;
import br.com.crm.model.entity.Empresa;
import br.com.crm.model.entity.Endereco;
import br.com.crm.model.entity.Maturidade;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.PessoaCartaoNegocio;
import br.com.crm.model.entity.PessoaObservacao;
import br.com.crm.model.entity.Profissao;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.exception.NegocioException;

/**
 * Servicos de negocio para Pessoa
 * @author Solkam
 * @since 28 abr 2016
 */
@Stateless
public class PessoaService {
	
	@PersistenceContext EntityManager manager;
	
	@EJB AcessoService userService;
	
	@EJB MaturidadeService maturidadeService;

	/**
	 * Salva contato aplicando RN
	 * @param pessoa
	 * @return
	 */
	public Pessoa salvarPessoa(Pessoa pessoa, Usuario usuarioSalvador) {
		//RNs
		verificarSeEmailEhUnico(pessoa, usuarioSalvador.getEmpresa() );
		referenciarEmpresaEPessoa(pessoa, usuarioSalvador.getEmpresa() );
		definirMaturidade( pessoa );
		//log
		pessoa.inserirInfoLog(usuarioSalvador);
		//salva
		return manager.merge( pessoa );
	}
	

	/**
	 * Calcula a maturidade da pessoa segundo sua idade
	 * @param pessoa
	 */
	private void definirMaturidade(Pessoa pessoa) {
		Maturidade maturidade = maturidadeService.buscarMaturidadePelaIdade( pessoa.getIdadeCalculada() );
		pessoa.setMaturidade( maturidade );
	}


	/**
	 * Seta a referencia de empresa na instancia de pessoa.
	 * @param pessoa
	 * @param empresa
	 */
	private void referenciarEmpresaEPessoa(Pessoa pessoa, Empresa empresa) {
		pessoa.setEmpresa(empresa);
	}


	/**
	 * Salva contato sem aplicado nenhuma RN.
	 * (usado na importacao de contatos)
	 * @param c
	 * @return
	 */
	public Pessoa salvarPessoaSemVerificar(Pessoa c, Usuario usuario) {
		referenciarEmpresaEPessoa(c, usuario.getEmpresa() );
		return manager.merge( c );
	}
	


	/**
	 * RN para garantir unicidade do email em contato
	 * @param pessoa
	 */
	private void verificarSeEmailEhUnico(Pessoa pessoa, Empresa empresa) {
		Pessoa foundPessoa = buscarPessoaPeloEmailPrincipal(pessoa.getEmailPrincipal(), empresa );
		if (foundPessoa!=null && !foundPessoa.equals(pessoa)) {
			throw new NegocioException("Email já usado por outro contato");
		}
	}

	/**
	 * Remove contact aplicando RN
	 * @param c
	 */
	public void removerPessoa(Pessoa c) {
		verificarAssociacoesDePessoa(c);
		manager.remove( manager.merge(c) );
	}
	
	
	/**
	 * RN que verifica se existem associados ao contact
	 * que ser� removido
	 * @param c
	 */
	private void verificarAssociacoesDePessoa(Pessoa c) {
		
	}

	/**
	 * Refresca contato com todas suas associacoes
	 * @param contact
	 * @return
	 */
	public Pessoa recarregarPessoa(Pessoa contact) {
		contact = manager.find(Pessoa.class, contact.getId() );
		
		contact.getAreasInteresse().size();
		contact.getProfissoes().size();
		contact.getObservacoes().size();
		contact.getCartoesNegocio().size();
		
		return contact;
	}
	

	/**
	 * Busca um contato pela PK
	 * @param contactId
	 * @return
	 */
	public Pessoa buscarPessoaPeloId(Integer contactId) {
		return manager.find(Pessoa.class, contactId);
	}
	
	/**
	 * Encontra contato pelo email
	 * (usado na RN para salvar contato)
	 * @param email
	 * @param empresa 
	 * @return
	 */
	private Pessoa buscarPessoaPeloEmailPrincipal(String email, Empresa empresa) {
		try {
			return manager.createNamedQuery("buscarPessoaPeloEmailPrincipal", Pessoa.class)
					.setParameter("pEmailPrincipal", email)
					.setParameter("pEmpresa", empresa)
					.getSingleResult()
					;
		} catch (NoResultException e) {
			return null;
		}
	}

	
	/**
	 * Pesquisa contact segundo filtros de pesquisa (usando criteria)
	 * @param nome
	 * @param email
	 * @param cidade
	 * @return
	 */
	public List<Pessoa> pesquisarPessoaPelosFiltros(Empresa empresa, String nome, String email, String cidade) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		Predicate conjuction = builder.conjunction();
		//empresa
		conjuction = builder.and( conjuction, 
				builder.equal( root.<Empresa>get("empresa"), empresa )
			);
		//1.name
		if (isNotBlank(nome)) {
			Predicate disjunction = builder.disjunction();
			disjunction = builder.or( disjunction, 
					builder.like(root.<String>get("primeiroNome"), toLikeMatchModeSTART(nome))
				);
			disjunction = builder.or( disjunction, 
					builder.like(root.<String>get("sobreNome"), toLikeMatchModeSTART(nome))
				);
			conjuction = builder.and( disjunction );
		}
		//2.email
		if (isNotBlank(email)) {
			Predicate disjunction = builder.disjunction();
			disjunction = builder.or( disjunction, 
					builder.equal(root.<String>get("emailPrincipal"), email)
				);
			disjunction = builder.or( disjunction, 
					builder.equal(root.<String>get("emailAlternativo"), email)
				);
			conjuction = builder.and( disjunction );
		}
		//2.email
		if (isNotBlank(cidade)) {
			conjuction = builder.and( conjuction, 
					builder.equal( root.<Endereco>get("endereco").<String>get("enderecoCidade"), cidade )
				);
		}
		//where e orderBy
		criteria.distinct(true);
		criteria.where(conjuction);
		criteria.orderBy( builder.asc( root.<String>get("primeiroNome")), builder.asc( root.<String>get("sobreNome")) );
		
		List<Pessoa> contacts = manager.createQuery(criteria).getResultList();
		return contacts;
	}
	
	/**
	 * Pesquisa contact pelo primeiro nome ou ultimo ou cidade
	 * (usando no autocomplete de contatos)
	 * @param nome
	 * @param cidadeNome
	 * @return
	 */
	public List<Pessoa> pesquisarPessoaPeloPrimeiroNomeOrSobreNomeOrCidade(Empresa empresa, String nome, String cidadeNome) {
		List<Pessoa> contacts = manager.createNamedQuery("pesquisarPessoaPeloPrimeiroNomeOrSobreNomeOrCidade", Pessoa.class)
				.setParameter("pEmpresa", empresa)
				.setParameter("pNome", toLikeMatchModeSTART(nome))
				.setParameter("pCidadeNome", toLikeMatchModeSTART(cidadeNome))
				.getResultList();
		return contacts;
	}
	
	
	/**
	 * Pesquisa todos os contatos que tem imagem
	 * @return
	 */
	public List<Pessoa> pesquisarPessoaComImagem() {
		return manager.createNamedQuery("pesquisarPessoaComImagem", Pessoa.class)
				.getResultList();
	}
	
	
	/**
	 * Pesquisa pelo contatos associados a uma area de interesse
	 * @param area
	 * @return
	 */
	public List<Pessoa> pesquisarPessoaPelaAreaInteresse(AreaInteresse area) {
		return manager.createNamedQuery("pesquisarPessoaPelaAreaInteresse", Pessoa.class)
				.setParameter("pAreaInteresse", area)
				.getResultList();
	}
	
	
	/**
	 * Pesquisa pelos contatos associados a uma profession
	 * @param p
	 * @return
	 */
	public List<Pessoa> pesquisarPessoaPelaProfissao(Profissao p) {
		return manager.createNamedQuery("pesquisarPessoaPelaProfissao", Pessoa.class)
				.setParameter("pProfissao", p)
				.getResultList();
	}

	/**
	 * Pesquisa as pessoas de uma maturidade
	 * @param maturidade
	 * @return
	 */
	public List<Pessoa> pesquisarPessoaPelaMaturidade(Maturidade maturidade) {
		return manager.createNamedQuery("pesquisarPessoaPelaMaturidade", Pessoa.class)
				.setParameter("pMaturidade", maturidade)
				.getResultList();
	}
	
	
	
	/* ***********
	 * Observacoes
	 *************/
	public PessoaObservacao salvarPessoaObservacao(PessoaObservacao obs) {
		return manager.merge( obs );
	}
	
	public void removerPessoaObservacao(PessoaObservacao obs) {
		manager.remove( manager.merge(obs) );
	}
	


	/* *************
	 * Business Card
	 ***************/
	public PessoaCartaoNegocio salvarPessoaCartaoNegocio(PessoaCartaoNegocio cartao, Usuario usuario) {
		inserirInfoUpload(cartao, usuario);
		return manager.merge( cartao );
	}
	
	private void inserirInfoUpload(PessoaCartaoNegocio cartao, Usuario usuario) {
		cartao.setSubidoEm( new Date() );
		cartao.setSubidoPor( usuario.getDescricaoCompleta() );
	}


	public void removerPessoaCartaoNegocio(PessoaCartaoNegocio card) {
		manager.remove( manager.merge(card) );
	}
	
	
	/**
	 * Pesquisar todos os cartoes de negocio.
	 * Usado para grava as imagens em disco.
	 * @return
	 */
	public List<PessoaCartaoNegocio> pesquisarPessoaCartaoNegocio() {
		return manager.createNamedQuery("pesquisarPessoaCartaoNegocio", PessoaCartaoNegocio.class)
				.getResultList();
	}
	
	
	
	
	/* ***************
	 * Ficha Inscrição
	 *****************/
//	public PessoaInscriptionForm savePessoaInscriptionForm(PessoaInscriptionForm form) {
//		return manager.merge( form );
//	}
//	
//	public void removePessoaInscriptionForm(PessoaInscriptionForm form) {
//		manager.remove( manager.merge(form) );
//	}
//	
//	public List<PessoaInscriptionForm> searchPessoaInscriptionForm() {
//		return manager.createNamedQuery("searchPessoaInscriptionForm", PessoaInscriptionForm.class)
//				.getResultList();
//	}
	
	
}
