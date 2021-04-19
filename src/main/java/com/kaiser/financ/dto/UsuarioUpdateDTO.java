package com.kaiser.financ.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UsuarioUpdateDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

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
	
	public UsuarioUpdateDTO() {		
	}
	
	public UsuarioUpdateDTO(
			@NotEmpty(message = "Campo Obrigatário") @Size(min = 1, max = 80, message = "Campo deve estar entre 1 e 80 caracteres") String nome,
			@NotEmpty(message = "Campo Obrigatário") @Size(min = 1, max = 80, message = "Campo deve estar entre 1 e 80 caracteres") String sobrenome,
			Date dtNascimento, @Size(max = 80, message = "Campo deve ter no máximo 30 caracteres") String cidade,
			@Size(max = 80, message = "Campo deve ter no máximo 30 caracteres") String estado,
			@Size(max = 80, message = "Campo deve ter no máximo 80 caracteres") String descricao) {
		super();
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dtNascimento = dtNascimento;
		this.cidade = cidade;
		this.estado = estado;
		this.descricao = descricao;
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
}
