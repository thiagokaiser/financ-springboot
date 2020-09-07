package com.kaiser.financ.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CategoriaDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
		
	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=5, max=80, message = "Campo deve estar entre 5 e 80 caracteres")
	private String descricao;
	private String cor;	
	
	public CategoriaDTO() {		
	}

	public CategoriaDTO(Integer id,
			@NotEmpty(message = "Campo Obrigatário") @Size(min = 5, max = 80, message = "Campo deve estar entre 5 e 80 caracteres") String descricao,
			String cor) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.cor = cor;
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

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}		
}
