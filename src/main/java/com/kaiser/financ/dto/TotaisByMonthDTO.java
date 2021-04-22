package com.kaiser.financ.dto;

import java.io.Serializable;

public class TotaisByMonthDTO implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	private Integer mes;
	private Integer ano;
	private Double total;		
	
	public TotaisByMonthDTO() {		
	}

	public TotaisByMonthDTO(Integer mes, Integer ano, Double total) {
		super();
		this.mes = mes;
		this.ano = ano;
		this.total = total;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}				
}
