package com.kaiser.financ.dto;

import com.kaiser.financ.domain.Despesa;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DespesaUpdateDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
		
	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=3, max=80, message = "Campo deve estar entre 3 e 80 caracteres")
	private String descricao;
	
	private Double valor;
	private Date dtVencimento;
	private Boolean pago;
	private Integer numParcelas;
	private Integer parcelaAtual;
	private Integer idParcela;
	private Integer categoriaId;	
	private Integer contaId;		
	
	public DespesaUpdateDTO() {		
	}	

	public DespesaUpdateDTO(Despesa obj) {		
		this.id = obj.getId();
		this.descricao = obj.getDescricao();
		this.valor = obj.getValor();
		this.dtVencimento = obj.getDtVencimento();
		this.pago = obj.getPago();
		this.numParcelas = obj.getNumParcelas();
		this.parcelaAtual = obj.getParcelaAtual();
		this.idParcela = obj.getIdParcela();
		this.categoriaId = obj.getCategoria().getId();
		this.contaId = obj.getConta().getId();
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public Boolean getPago() {
		return pago;
	}

	public void setPago(Boolean pago) {
		this.pago = pago;
	}

	public Integer getNumParcelas() {
		return numParcelas;
	}

	public void setNumParcelas(Integer numParcelas) {
		this.numParcelas = numParcelas;
	}

	public Integer getParcelaAtual() {
		return parcelaAtual;
	}

	public void setParcelaAtual(Integer parcelaAtual) {
		this.parcelaAtual = parcelaAtual;
	}

	public Integer getIdParcela() {
		return idParcela;
	}

	public void setIdParcela(Integer idParcela) {
		this.idParcela = idParcela;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoria(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Integer getContaId() {
		return contaId;
	}

	public void setContaId(Integer contaId) {
		this.contaId = contaId;
	}			
}
