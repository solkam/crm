package br.com.crm.model.service;

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
		profissao.inserirInfoLog(usuario);
		referenciarEmpresaEProfissao(profissao, usuario.getEmpresa() );
		return manager.merge( profissao );
	}

	
	/**
	 * Fecha a relação de profissão e empresa
	 * @param profissao
	 * @param empresa
	 */
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
			throw new NegocioException("Já existe Profissão com esta descrição");
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

	
	/**
	 * Realiza a carga inicial de profissões para a empresa
	 * @param empresa
	 */
	public void carregarProfissoesParaEmpresa(Empresa empresa, Usuario usuarioCriador) {
		String[] descricaoArray = new String[] {
				 "Administrador"
				,"Advogado"
				,"Aeronauta"
				,"Arquivista / Técnico de Arquivo"
				,"Artista/Técnico em espetáculos de diversões"
				,"Assistente Social"
				,"Atleta Profissional de Futebol"
				,"Atuário"
				,"Bibliotecário"
				,"Biomédico"
				,"Biólogo"
				,"Bombeiro Civil"
				,"Comerciário"
				,"Contabilista"
				,"Corretor de Imóveis"
				,"Corretor de Seguros"
				,"Despachante Aduaneiro"
				,"Engenheiro/ Arquiteto/ Agrônomo"
				,"Economista Doméstico"
				,"Economista"
				,"Educação Física"
				,"Empregado Doméstico"
				,"Enfermagem"
				,"Enélogo"
				,"Engenharia de Segurança"
				,"Estatástico"
				,"Fisioterapeuta e Terapeuta Ocupacional"
				,"Farmacêutico"
				,"Fonoaudiálogo"
				,"Garimpeiro"
				,"Geógrafo"
				,"Geólogo"
				,"Guardador e Lavador de Veículos"
				,"Instrutor de Trânsito"
				,"Jornalista"
				,"Leiloeiro"
				,"Leiloeiro Rural"
				,"Mãe Social"
				,"Massagista"
				,"Médico"
				,"Medicina Veterinária"
				,"Mototaxista e Motoboy"
				,"Museólogo"
				,"Músico"
				,"Nutricionista"
				,"Oceanógrafo"
				,"Odontologia"
				,"Orientador Educacional"
				,"Peão de Rodeio"
				,"Pescador Profissional"
				,"Psicologia"
				,"Publicitário/Agenciador de Propaganda"
				,"Químico"
				,"Radialista"
				,"Relações Públicas"
				,"Representantes Comerciais Autônomos"
				,"Repentista"
				,"Secretário - Secretário Executivo e Técnico em Secretariado"
				,"Sociólogo"
				,"Sommelier"
				,"Taxista"
				,"Tradutor e Intérprete da Língua Brasileira de Sinais - LIBRAS"
				,"Técnico em Administração"
				,"Técnico Industrial"
				,"Técnico em Prótese Dentária"
				,"Técnico em Radiologia"
				,"Turismólogo"
				,"Zootecnista"
		};
		
		for (String descricaoVar : descricaoArray) {
			Profissao profissaoVar = new Profissao(empresa, descricaoVar);
			profissaoVar.inserirInfoLog(usuarioCriador);
			manager.persist( profissaoVar );
		}
	}
	

}
