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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
		result = prime * result + ((mes == null) ? 0 : mes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TotaisByMonthDTO other = (TotaisByMonthDTO) obj;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		if (mes == null) {
			if (other.mes != null)
				return false;
		} else if (!mes.equals(other.mes))
			return false;
		return true;
	}	
}
