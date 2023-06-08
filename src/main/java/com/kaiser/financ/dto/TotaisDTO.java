package com.kaiser.financ.dto;

import java.io.Serializable;
import java.util.Date;

public class TotaisDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private Date dtInicial;
  private Date dtFinal;
  private Double total;
  private Double totalPago;
  private Double totalPendente;

  public TotaisDTO() {}

  public TotaisDTO(
      Date dtInicial, Date dtFinal, Double total, Double totalPago, Double totalPendente) {
    this.dtInicial = dtInicial;
    this.dtFinal = dtFinal;
    this.total = total;
    this.totalPago = totalPago;
    this.totalPendente = totalPendente;
  }

  public Date getDtInicial() {
    return dtInicial;
  }

  public void setDtInicial(Date dtInicial) {
    this.dtInicial = dtInicial;
  }

  public Date getDtFinal() {
    return dtFinal;
  }

  public void setDtFinal(Date dtFinal) {
    this.dtFinal = dtFinal;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public Double getTotalPago() {
    return totalPago;
  }

  public void setTotalPago(Double totalPago) {
    this.totalPago = totalPago;
  }

  public Double getTotalPendente() {
    return totalPendente;
  }

  public void setTotalPendente(Double totalPendente) {
    this.totalPendente = totalPendente;
  }
}
