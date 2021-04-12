package com.kaiser.financ.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.services.validation.UsuarioUpdate;

@UsuarioUpdate
public class UsuarioDTO implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=5, max=80, message = "Campo deve estar entre 5 e 80 caracteres")
	private String nome;
	
	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=5, max=80, message = "Campo deve estar entre 5 e 80 caracteres")
	private String sobrenome;
	
	@NotEmpty(message = "Campo Obrigatário")
	@Email(message = "Email inválido")
	private String email;
	
	public UsuarioDTO() {		
	}

	public UsuarioDTO(Usuario obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
