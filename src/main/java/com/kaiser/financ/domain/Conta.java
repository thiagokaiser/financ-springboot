package com.kaiser.financ.domain;

import javax.persistence.Entity;

@Entity
public class Conta extends Domain {
  private String descricao;

  public Conta() {
    super();
  }

  public Conta(Integer id, String descricao, Usuario usuario) {
    super(id, usuario);
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
}
