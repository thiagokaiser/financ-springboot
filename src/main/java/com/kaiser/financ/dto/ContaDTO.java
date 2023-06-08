package com.kaiser.financ.dto;

import com.kaiser.financ.domain.Conta;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ContaDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
		
	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=3, max=80, message = "Campo deve estar entre 3 e 80 caracteres")
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
