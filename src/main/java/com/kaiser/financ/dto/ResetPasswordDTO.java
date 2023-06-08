package com.kaiser.financ.dto;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ResetPasswordDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Campo Obrigatário")
	@Size(min=6, max=20, message = "Campo deve estar entre 6 e 20 caracteres")
	private String newPassword;
	
	private String token;

	public ResetPasswordDTO() {		
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
