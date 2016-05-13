package br.com.crm.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.crm.model.util.DateUtil;

/**
 * Campanha de tele-marketing ativa para intereções com pessoas
 * de uma empresa realizadas por um responsavel (usuario) e com 
 * observação
 * @author Solkam
 * @since 11 mai 2016
 */
@Entity
public class Campanha implements Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	/**
	 * Empresa da campanha
	 */
	@ManyToOne
	@NotNull
	private Empresa empresa;
	
	
	/**
	 * Pessoas que serão interagidas na campanha
	 */
	@ManyToMany
	@JoinTable(name="Campanha_x_pessoa"
		,joinColumns=@JoinColumn(name="campanha_id")
		,inverseJoinColumns=@JoinColumn(name="pessoa_id") )
	private List<Pessoa> pessoas;
	
	
	/**
	 * Produtos que podem fazer parte da campanha 
	 */
	@ManyToMany
	@JoinTable(name="Campanha_x_produto"
	,joinColumns=@JoinColumn(name="campanha_id")
	,inverseJoinColumns=@JoinColumn(name="produto_id") )
	private List<Produto> produtos;
	
	
	/**
	 * Usuários responsaveis pelas interações com as pessoas
	 */
	@ManyToMany
	@JoinTable(name="Campanha_x_usuario"
	,joinColumns=@JoinColumn(name="campanha_id")
	,inverseJoinColumns=@JoinColumn(name="usuario_id") )
	private List<Usuario> responsaveis;
	
	
	/**
	 * Interações já realizadas na campanha
	 */
	@OneToMany(mappedBy="campanha")
	private List<InteracaoCampanhaPessoa> interacoes;
	
	
	
	/**
	 * Breve descrição sobre a campanha
	 */
	@Size(max=100)
	@NotNull
	private String descricao;
	
	
	/**
	 * Possivel data de início
	 */
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	
	/**
	 * Possivel data fim da campanha
	 */
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
	
	/**
	 * Observação detalhada sobre a campanha 
	 */
	@Size(max=1000)
	private String observacao;
	
	
	/**
	 * Indicativo se a campanha está no ar
	 */
	@NotNull
	private Boolean flagAtivo=true;
	
	
	//logs
	@Embedded
	private InfoLog infoLog;

	
	//acessores...
	private static final long serialVersionUID = 4100416371602529409L;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<InteracaoCampanhaPessoa> getInteracoes() {
		return interacoes;
	}

	public void setInteracoes(List<InteracaoCampanhaPessoa> interacoes) {
		this.interacoes = interacoes;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Usuario> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(List<Usuario> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public InfoLog getInfoLog() {
		if (infoLog==null) {
			infoLog = new InfoLog();
		}
		return infoLog;
	}

	public void setInfoLog(InfoLog infoLog) {
		this.infoLog = infoLog;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campanha other = (Campanha) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	public boolean isTransient() {
		return getId()==null;
	}
	
	
}