package com.kaiser.financ.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotaisDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private LocalDate dtInicial;
  private LocalDate dtFinal;
  private Double total;
  private Double totalPago;
  private Double totalPendente;

  public TotaisDTO() {}

  public TotaisDTO(
      LocalDate dtInicial, LocalDate dtFinal, Double total, Double totalPago, Double totalPendente) {
    this.dtInicial = dtInicial;
    this.dtFinal = dtFinal;
    this.total = total;
    this.totalPago = totalPago;
    this.totalPendente = totalPendente;
  }
  
}
