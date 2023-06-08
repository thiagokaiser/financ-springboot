package com.kaiser.financ.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
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

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public Conta getConta() {
    return conta;
  }

  public void setConta(Conta conta) {
    this.conta = conta;
  }
}
