package com.kaiser.financ.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Notificacao extends Domain {
  @NotBlank(message = "Campo Obrigatário")
  private String descricao;

  private Date dtCriacao;
  private Date dtLeitura;
  private boolean lido;

  public Notificacao() {}

  public Notificacao(
      Integer id, String descricao, Date dtCriacao, Date dtLeitura, Usuario usuario, boolean lido) {
    super(id, usuario);
    this.descricao = descricao;
    this.dtCriacao = dtCriacao;
    this.dtLeitura = dtLeitura;
    this.lido = lido;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Date getDtCriacao() {
    return dtCriacao;
  }

  public void setDtCriacao(Date dtCriacao) {
    this.dtCriacao = dtCriacao;
  }

  public Date getDtLeitura() {
    return dtLeitura;
  }

  public void setDtLeitura(Date dtLeitura) {
    this.dtLeitura = dtLeitura;
  }

  public boolean isLido() {
    return lido;
  }

  public void setLido(boolean lido) {
    this.lido = lido;
  }
}
