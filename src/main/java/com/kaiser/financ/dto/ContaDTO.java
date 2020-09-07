package com.kaiser.financ.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ContaDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
		
	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=5, max=80, message = "Campo deve estar entre 5 e 80 caracteres")
	private String descricao;		
	
	public ContaDTO() {		
	}

	public ContaDTO(Integer id,
			@NotEmpty(message = "Campo Obrigatário") @Size(min = 5, max = 80, message = "Campo deve estar entre 5 e 80 caracteres") String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}			
}
