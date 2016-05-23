package br.com.crm.model.entity;

import java.io.Serializable;


import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.crm.model.exception.NegocioException;
import br.com.crm.model.util.DateUtil;


/**
 * Dados de contato de uma pessoa
 * @author Solkam
 * @since 09 mai 2016
 */
@Entity
public class Pessoa implements Serializable, Comparable<Pessoa> {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@NotNull
	private Empresa empresa;
	
	/**
	 * Primeiro nome
	 */
	@NotNull
	@Size(max=100)
	private String primeiroNome;
	
	/**
	 * Ultimo nome (ou nome de familia)
	 */
	@NotNull
	@Size(max=100)
	private String sobreNome;
	
	/**
	 * Email principal (mais usado)
	 */
	@NotNull
	@Size(max=100)
	private String emailPrincipal;
	
	/**
	 * Email secundario ou alternativo
	 */
	@Size(max=100)
	private String emailAlternativo;
	
	/**
	 * Dados completos de endereco
	 */
	@Embedded
	private Endereco endereco;
	
	/**
	 * Dados completos de documentos
	 */
	@Embedded
	private DocumentoIdentidade documento;

	/**
	 * Dados completos de telefones
	 */
	@Embedded
	private Telefone telefone;
	
	/**
	 * Dia do nascimento
	 */
	@NotNull
	private Integer nascimentoDia;
	
	/**
	 * Mes do nascimento
	 */
	@NotNull
	private Integer nascimentoMes;

	/**
	 * Ano do nascimento
	 */
	@NotNull
	private Integer nascimentoAno;
	
	/**
	 * Maturidade segundo a idade
	 */
	@ManyToOne
	private Maturidade maturidade;

	
	/**
	 * Sexo
	 */
	@Enumerated(EnumType.STRING)
	private Genero genero;
	
	
	/**
	 * Empresa em que trabalha
	 */
	@Size(max=100)
	private String empregadoEm;
	
	/**
	 * Função na empresa
	 */
	@Size(max=100)
	private String empregadoComo;
	
	
	

	/**
	 * Responsavel por indicar
	 */
	@Size(max=100)
	private String indicadoPor;
	
	/**
	 * Dados sobre as redes sociais
	 */
	@Embedded
	private RedeSocial redeSocial;
	
	
	
	
	//foto
	@Lob
	private byte[] imagemBinario;
	
	@Size(max=5)
	private String imagemExtensao;
	
	
	/**
	 * Profissoes desempenhadas
	 */
	@ManyToMany
	@JoinTable(name="Pessoa_x_Profissao"
		,joinColumns=@JoinColumn(name="pessoa_id")
		,inverseJoinColumns=@JoinColumn(name="profissao_id")
	)
	private List<Profissao> profissoes;
	
	
	/**
	 * Areas de interesse (hobbies)
	 */
	@ManyToMany
	@JoinTable(name="Pessoa_x_AreaInteresse"
		,joinColumns=@JoinColumn(name="pessoa_id")
		,inverseJoinColumns=@JoinColumn(name="areaInteresse_id")
	)
	private List<AreaInteresse> areasInteresse;
	
	
	//observacoes
	@OneToMany(mappedBy="pessoa")
	private List<PessoaObservacao> observacoes;

	
	//business card
	@OneToMany(mappedBy="pessoa")
	private List<PessoaCartaoNegocio> cartoesNegocio;
	
	
	
	
	//logs
	@Embedded
	private InfoLog infoLog;
	
	
	
	//acessores...
	private static final long serialVersionUID = -8956195215009970993L;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public Integer getNascimentoDia() {
		return nascimentoDia;
	}

	public void setNascimentoDia(Integer nascimentoDia) {
		this.nascimentoDia = nascimentoDia;
	}

	public Integer getNascimentoMes() {
		return nascimentoMes;
	}

	public void setNascimentoMes(Integer nascimentoMes) {
		this.nascimentoMes = nascimentoMes;
	}

	public Integer getNascimentoAno() {
		return nascimentoAno;
	}

	public void setNascimentoAno(Integer nascimentoAno) {
		this.nascimentoAno = nascimentoAno;
	}

	public List<AreaInteresse> getAreasInteresse() {
		return areasInteresse;
	}

	public void setAreasInteresse(List<AreaInteresse> areasInteresse) {
		this.areasInteresse = areasInteresse;
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

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public String getEmailPrincipal() {
		return emailPrincipal;
	}

	public void setEmailPrincipal(String emailPrincipal) {
		this.emailPrincipal = emailPrincipal;
	}

	public String getEmailAlternativo() {
		return emailAlternativo;
	}

	public void setEmailAlternativo(String emailAlternativo) {
		this.emailAlternativo = emailAlternativo;
	}

	public Endereco getEndereco() {
		if (endereco==null) {
			endereco = new Endereco();
		}
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public DocumentoIdentidade getDocumento() {
		if (documento==null) {
			documento = new DocumentoIdentidade();
		}
		return documento;
	}

	public void setDocumento(DocumentoIdentidade documento) {
		this.documento = documento;
	}

	public Telefone getTelefone() {
		if (telefone==null) {
			telefone = new Telefone();
		}
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Maturidade getMaturidade() {
		return maturidade;
	}

	public void setMaturidade(Maturidade maturidade) {
		this.maturidade = maturidade;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public List<Profissao> getProfissoes() {
		return profissoes;
	}

	public void setProfissoes(List<Profissao> profissoes) {
		this.profissoes = profissoes;
	}

	public String getEmpregadoEm() {
		return empregadoEm;
	}

	public void setEmpregadoEm(String empregadoEm) {
		this.empregadoEm = empregadoEm;
	}

	public String getEmpregadoComo() {
		return empregadoComo;
	}

	public void setEmpregadoComo(String empregadoComo) {
		this.empregadoComo = empregadoComo;
	}

	public String getIndicadoPor() {
		return indicadoPor;
	}

	public void setIndicadoPor(String indicadoPor) {
		this.indicadoPor = indicadoPor;
	}

	public RedeSocial getRedeSocial() {
		if (redeSocial==null) {
			redeSocial=new RedeSocial();
		}
		return redeSocial;
	}

	public void setRedeSocial(RedeSocial redeSocial) {
		this.redeSocial = redeSocial;
	}

	public byte[] getImagemBinario() {
		return imagemBinario;
	}

	public void setImagemBinario(byte[] imagemBinario) {
		this.imagemBinario = imagemBinario;
	}

	public String getImagemExtensao() {
		return imagemExtensao;
	}

	public void setImagemExtensao(String imagemExtensao) {
		this.imagemExtensao = imagemExtensao;
	}

	public List<PessoaObservacao> getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(List<PessoaObservacao> observacoes) {
		this.observacoes = observacoes;
	}

	public List<PessoaCartaoNegocio> getCartoesNegocio() {
		return cartoesNegocio;
	}

	public void setCartoesNegocio(List<PessoaCartaoNegocio> cartoesNegocio) {
		this.cartoesNegocio = cartoesNegocio;
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
		Pessoa other = (Pessoa) obj;
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
	
	
	
	//runtime

	/**
	 * Monta um Date a partir dos campos individuais de ano, mes e dia
	 * @return
	 */
	public Date getDataNascimentoCompleta() {
		return DateUtil.buildDate(getNascimentoAno(), getNascimentoMes(), getNascimentoDia() );
	}
	
	/**
	 * Calcula a idade atraves da data de nascimento
	 * @return
	 */
	public Integer getIdadeCalculada() {
		if (getDataNascimentoCompleta()!=null) {
			return DateUtil.calculateAge( getDataNascimentoCompleta() );
		}
		return null;
	}
	
	/**
	 * Monta o nome completo juntando o primeiro e ultimo nomes.
	 * @return
	 */
	public String getNomeCompleto() {
		return String.format("%s %s", getPrimeiroNome(), getSobreNome() );
	}

	
	/**
	 * Flag que valida que tanto extensao e binario da imagem estao preenchido
	 * @return
	 */
	public Boolean getFlagImagemOK() {
		return getImagemExtensao()!=null 
			&& !getImagemExtensao().trim().isEmpty() 
			&& getImagemBinario()!=null;
	}
	
	
	/**
	 * Monta o nome da imagem usando o ID e a extensao
	 * @return
	 */
	public String getImagemNome() {
		return String.format("contact_%s.%s", getId(), getImagemExtensao() );
	}

	/**
	 * Os campos individuais de ano, mes e dia deve formar uma data valida
	 */
	public void validarDataNascimento() {
		if (!DateUtil.isAValidDate(getNascimentoAno(), getNascimentoMes(), getNascimentoDia() ) ) {
			throw new NegocioException("Data de Nascimento invalida");
		}
	}
	
	/**
	 * Pelo menos um documento tem que ser fornecido
	 */
	public void validarDocumentos() {
		String cpf = getDocumento().getDocumentoCPF();
		String passport = getDocumento().getDocumentoPassporte();
		String rg = getDocumento().getDocumentoRG();
		
		boolean isCpfNull = cpf==null || cpf.trim().isEmpty();
		boolean isPassportNull = passport==null || passport.trim().isEmpty();
		boolean isRg = rg==null || rg.trim().isEmpty();
		
		if (isCpfNull  && isPassportNull && isRg) {
			throw new NegocioException("Pelo menos um documento deve ser informado");
		}
	}

	@Override
	public int compareTo(Pessoa that) {
		if (this.id!=null) {
			return this.id.compareTo( that.id );
		} else {
			return 0;
		}
	}
	
	
}
