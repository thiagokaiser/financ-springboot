package com.kaiser.financ.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UsuarioUpdateAdminDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Campo Obrigatário")
	@Email(message = "Email inválido")
	private String email;
	
	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=1, max=80, message = "Campo deve estar entre 1 e 80 caracteres")
	private String nome;
	
	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=1, max=80, message = "Campo deve estar entre 1 e 80 caracteres")
	private String sobrenome;
	
	private Date dtNascimento;
	
	@Size(max=80, message = "Campo deve ter no máximo 30 caracteres")
	private String cidade;
	
	@Size(max=80, message = "Campo deve ter no máximo 30 caracteres")
	private String estado;
	
	@Size(max=80, message = "Campo deve ter no máximo 80 caracteres")
	private String descricao;
	
	private String imagemPerfil;
	
	public UsuarioUpdateAdminDTO() {		
	}
	
	public UsuarioUpdateAdminDTO(String email, String nome, String sobrenome, Date dtNascimento, String cidade, String estado, String descricao, String imagemPerfil) {
		super();
		this.email = email;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dtNascimento = dtNascimento;
		this.cidade = cidade;
		this.estado = estado;
		this.descricao = descricao;
		this.imagemPerfil = imagemPerfil;
	}	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
}
