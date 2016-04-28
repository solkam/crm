package br.com.crm.controller.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.crm.controller.util.JSFUtil;
import br.com.crm.model.entity.Perfil;
import br.com.crm.model.entity.Usuario;
import br.com.crm.model.exception.NegocioException;
import br.com.crm.model.service.UsuarioService;

/**
 * Controller para UC Gerenciar Usuários
 * @author Solkam
 * @since 27 abr 2016
 */
@ManagedBean
@ViewScoped
public class UsuarioMB implements Serializable {
	
	@Inject UsuarioService usuarioService;
	
	private Usuario usuario;
	
	private List<Usuario> usuarios;
	
	//filtros
	private String filtroEmail;
	private Perfil filtroPerfil = Perfil.ADM;
	
	//senhas
	private String senha1;
	private String senha2;
	
	
	@PostConstruct void init() {
		pesquisar();
	}
	
	private void initUsuarios() {
		usuarios = usuarioService.pesquisarUsuarioPelosFiltros(filtroEmail, filtroPerfil);
	}
	
	public void pesquisar() {
		initUsuarios();
		JSFUtil.addMessageAboutResult(usuarios);
	}
	
	public void novo() {
		usuario = new Usuario();
	}
	
	public void gerenciar(Usuario usuarioSelecionado) {
		this.usuario = usuarioSelecionado;
	}
	
	public void salvar() {
		usuario = usuarioService.salvarUsuario(usuario);
		initUsuarios();
		JSFUtil.addInfoMessage("Usuário salvo com sucesso");
		
	}
	
	public void remover() {
		usuarioService.removerUsuario(usuario);
		initUsuarios();
		JSFUtil.addInfoMessage("Usuário removido");
	}
	
	public void salvarSenha() {
		validarSenhas();
		usuario.setSenha( senha1 );
		usuarioService.salvarUsuario(usuario);
		JSFUtil.addInfoMessage("Senha salva com sucesso");
	}
	
	
	//util
	private void validarSenhas() {
		if (!senha1.equals(senha2)) {
			throw new NegocioException("Senhas não conferem");
		}
	}

	
	//acessores...
	private static final long serialVersionUID = 2577284491525285700L;


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getFiltroEmail() {
		return filtroEmail;
	}

	public void setFiltroEmail(String filtroEmail) {
		this.filtroEmail = filtroEmail;
	}

	public Perfil getFiltroPerfil() {
		return filtroPerfil;
	}

	public void setFiltroPerfil(Perfil filtroPerfil) {
		this.filtroPerfil = filtroPerfil;
	}

	public String getSenha1() {
		return senha1;
	}

	public void setSenha1(String senha1) {
		this.senha1 = senha1;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	
	
}
