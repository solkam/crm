package br.com.crm.model.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.crm.model.entity.Empresa;
import br.com.crm.model.entity.Maturidade;
import br.com.crm.model.entity.Pessoa;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.exception.NegocioException;

/**
 * Serviços de negocio para maturidade
 * @author Solkam
 * @since 10 mai 2016
 */
@Stateless
public class MaturidadeService {
	
	@PersistenceContext EntityManager manager;
	
	
	@Inject PessoaService pessoaService;
	
	
	
	/**
	 * Salva maturidade, realizando a referencia com empresa e log de informaçoes
	 * @param maturidade
	 * @param usuario
	 * @return
	 */
	public Maturidade salvarMaturidade(Maturidade maturidade, Usuario usuario) {
		referenciarEmpresaEMaturidade(maturidade, usuario.getEmpresa() );
		maturidade.inserirInfoLog(usuario);
		return manager.merge(maturidade);
	}
	

	/**
	 * Fecha a referencia entre maturidade e empresa
	 * @param m
	 * @param empresa
	 */
	private void referenciarEmpresaEMaturidade(Maturidade m, Empresa empresa) {
		m.setEmpresa( empresa );
	}


	/**
	 * Remove a maturidade aplicando RN
	 * @param maturidade
	 */
	public void removerMaturidade(Maturidade maturidade) {
		maturidade = manager.find(Maturidade.class, maturidade.getId() );
		verificarSeExistePessoa(maturidade);
		manager.remove( maturidade );
	}

	/**
	 * RN que verifica se existem pessoas referenciando a maturidade
	 * que vai ser excluída.
	 * @param maturidade
	 */
	private void verificarSeExistePessoa(Maturidade maturidade) {
		List<Pessoa> pessoasEncontradas = pessoaService.pesquisarPessoaPelaMaturidade(maturidade);
		if (pessoasEncontradas!=null && !pessoasEncontradas.isEmpty()) {
			throw new NegocioException("Existem pessoas relacionadas a esta Maturidade");
		}
	}

	
	
	/**
	 * Pesquisa as maturidade (de uma empresa)
	 * @param empresa
	 * @return
	 */
	public List<Maturidade> pesquisarMaturidade(Empresa empresa) {
		return manager.createNamedQuery("pesquisarMaturidade", Maturidade.class)
				.setParameter("pEmpresa", empresa)
				.getResultList();
	}
	
	
	/**
	 * Busca maturidade pela pk
	 * (usado no converter)
	 * @param id
	 * @return
	 */
	public Maturidade buscarMaturidadePeloId(Integer id) {
		return manager.find(Maturidade.class, id);
	}
	
	/**
	 * Busca uma maturidade conforme a idade
	 * @param age
	 * @return
	 */
	public Maturidade buscarMaturidadePelaIdade(Integer age) {
		List<Maturidade> maturities = manager.createNamedQuery("buscarMaturidadePelaIdade", Maturidade.class)
				.setParameter("pIdade", age)
				.getResultList();
		
		if (maturities.isEmpty() ) {
			return null; 
		} else {
			return maturities.get(0);
		}
	}

	
	/**
	 * Realiza a carga inicial de maturidades para uma empresa
	 * @param empresa
	 * @param usuarioCriador
	 */
	public void carregarMaturidadesParaEmpresa(Empresa empresa, Usuario usuarioCriador) {
		Maturidade[] maturidadeArray = new Maturidade[] {
			new Maturidade(empresa, "Criança",  0,  14 ),
			new Maturidade(empresa, "Jovem"  , 15,  24 ),
			new Maturidade(empresa, "Adulto" , 25,  60 ),
			new Maturidade(empresa, "Idoso"  , 60, 100 )
		};
		
		for (Maturidade maturidadeVar : maturidadeArray) {
			maturidadeVar.inserirInfoLog(usuarioCriador);
			manager.persist( maturidadeVar );
		}
	}
	

}
