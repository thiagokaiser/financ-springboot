package com.kaiser.financ.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotaisByCategDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private String descricao;
  private String cor;
  private Double total;

  public TotaisByCategDTO(String descricao, String cor, Double total) {
    this.descricao = descricao;
    this.cor = cor;
    this.setTotal(total);
  }

  public void setTotal(Double total) {
    BigDecimal bd = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
    this.total = bd.doubleValue();
  }
}
