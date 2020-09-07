package com.kaiser.financ.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kaiser.financ.domain.Conta;

public class ContaDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
		
	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=5, max=80, message = "Campo deve estar entre 5 e 80 caracteres")
	private String descricao;		
	
	public ContaDTO() {		
	}

	public ContaDTO(Conta obj) {		
		this.id = obj.getId();
		this.descricao = obj.getDescricao();		
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
