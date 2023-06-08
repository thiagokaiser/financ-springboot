package com.kaiser.financ.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TotaisByCategDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private String descricao;
  private String cor;
  private Double total;

  public TotaisByCategDTO() {}

  public TotaisByCategDTO(String descricao, String cor, Double total) {
    this.descricao = descricao;
    this.cor = cor;
    this.setTotal(total);
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

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    BigDecimal bd = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
    this.total = bd.doubleValue();
  }
}
