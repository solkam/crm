package br.com.crm.model.service;

import static br.com.crm.model.util.QueryUtil.isNotBlank;
import static br.com.crm.model.util.QueryUtil.toLikeMatchModeSTART;

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
	
	@PersistenceContext
	private EntityManager manager;
	
	@EJB UsuarioService userService;
	
//	@EJB MaturityService maturityService;

	/**
	 * Salva contato aplicando RN
	 * @param contact
	 * @return
	 */
	public Pessoa salvarPessoa(Pessoa contact, Usuario usuario) {
		verificarSeEmailEhUnico(contact, usuario.getEmpresa() );
//		defineMaturity( contact );
		return manager.merge( contact );
	}
	

	/**
	 * Calcula a maturidade do contato segundo sua idade
	 * @param contact
	 */
//	private void defineMaturity(Pessoa contact) {
//		Maturity maturity = maturityService.findMaturityByAge( contact.getCalculatedAge() );
//		contact.setMaturity(maturity);
//	}


	/**
	 * Salva contato sem aplicado nenhuma RN.
	 * (usado na importacao de contatos)
	 * @param c
	 * @return
	 */
	public Pessoa salvarPessoaSemVerificar(Pessoa c, Usuario usuario) {
		referenciarEmpresa(c, usuario.getEmpresa() );
		return manager.merge( c );
	}
	
	/**
	 * Resolve a referencia entre pessoa e empresa
	 * @param pessoa
	 * @param empresa
	 */
	private void referenciarEmpresa(Pessoa pessoa, Empresa empresa) {
		pessoa.setEmpresa( empresa );
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
	public Pessoa refreshPessoa(Pessoa contact) {
		contact = manager.find(Pessoa.class, contact.getId() );
		
		contact.getAreasInteresse().size();
		contact.getProfissoes().size();
		contact.getObservacoes().size();
		contact.getCartoesNegocio().size();
//		contact.getInscriptionForms().size();
		
		return contact;
	}
	

	/**
	 * Busca um contato pela PK
	 * @param contactId
	 * @return
	 */
	public Pessoa buscarPessoaPeloId(long contactId) {
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
		
		//TODO colocar empresa...
		Predicate conjuction = builder.conjunction();
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
	public List<Pessoa> pesquisarPessoaSemImagem() {
		return manager.createNamedQuery("pesquisarPessoaSemImagem", Pessoa.class)
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
	public PessoaCartaoNegocio savePessoaCartaoNegocio(PessoaCartaoNegocio card) {
		return manager.merge( card );
	}
	
	public void removePessoaCartaoNegocio(PessoaCartaoNegocio card) {
		manager.remove( manager.merge(card) );
	}
	
	public List<PessoaCartaoNegocio> searchPessoaCartaoNegocio() {
		return manager.createNamedQuery("searchPessoaCartaoNegocio", PessoaCartaoNegocio.class)
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
