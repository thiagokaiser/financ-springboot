package com.kaiser.financ.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Despesa extends Domain {
  @NotBlank(message = "Campo Obrigatário")
  private String descricao;

  private Double valor;

  @NotNull(message = "Campo Obrigatário")
  private Date dtVencimento;

  private Boolean pago;

  private Integer numParcelas;

  private Integer parcelaAtual;

  private Integer idParcela;

  @ManyToOne
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  @ManyToOne(optional = true)
  @JoinColumn(name = "conta_id")
  private Conta conta;

  public Despesa() {}

  public Despesa(
      Integer id,
      String descricao,
      Double valor,
      Date dtVencimento,
      Boolean pago,
      Integer numParcelas,
      Integer parcelaAtual,
      Integer idParcela,
      Usuario usuario,
      Categoria categoria,
      Conta conta) {
    super(id, usuario);
    this.descricao = descricao;
    this.valor = valor;
    this.dtVencimento = dtVencimento;
    this.pago = pago;
    this.numParcelas = numParcelas;
    this.parcelaAtual = parcelaAtual;
    this.idParcela = idParcela;
    this.categoria = categoria;
    this.conta = conta;
  }
  
}
