package com.kaiser.financ.dto;

import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.domain.enums.Perfil;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UsuarioDTO implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String sobrenome;
	private Date dtNascimento;
	private String cidade;
	private String estado;	
	private String descricao;
	private String imagemPerfil;	
	private String email;
	private Set<Perfil> perfis = new HashSet<>();
	
	public UsuarioDTO(Integer id, String nome, String sobrenome, Date dtNascimento, String cidade, String estado,
			String descricao, String imagemPerfil, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dtNascimento = dtNascimento;
		this.cidade = cidade;
		this.estado = estado;
		this.descricao = descricao;
		this.imagemPerfil = imagemPerfil;
		this.email = email;
	}
	
	public UsuarioDTO(Usuario obj) {		
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.sobrenome = obj.getSobrenome();
		this.dtNascimento = obj.getDtNascimento();
		this.cidade = obj.getCidade();
		this.estado = obj.getEstado();
		this.descricao = obj.getDescricao();
		this.imagemPerfil = obj.getImagemPerfil();
		this.email = obj.getEmail();
		this.perfis = obj.getPerfis();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImagemPerfil() {
		return imagemPerfil;
	}

	public void setImagemPerfil(String imagemPerfil) {
		this.imagemPerfil = imagemPerfil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}	
}
