package com.kaiser.financ.dto;

import com.kaiser.financ.services.validation.UsuarioInsert;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@UsuarioInsert
public class UsuarioNewDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=1, max=80, message = "Campo deve estar entre 1 e 80 caracteres")
	private String nome;
	
	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=1, max=80, message = "Campo deve estar entre 1 e 80 caracteres")
	private String sobrenome;
	
	@NotEmpty(message = "Campo Obrigatário")
	@Email(message = "Email inválido")
	private String email;		
		
	@NotEmpty(message = "Campo Obrigatário")
	@Size(min=6, max=20, message = "Campo deve estar entre 6 e 20 caracteres")
	private String senha;			
	
	private String telefone1;	
	private String telefone2;
	private String telefone3;	
	
	public UsuarioNewDTO() {		
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

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}	

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}	
}
