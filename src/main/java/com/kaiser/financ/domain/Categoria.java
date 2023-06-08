package com.kaiser.financ.domain;

import javax.persistence.Entity;

@Entity
public class Categoria extends Domain {
  private String descricao;
  private String cor;

  public Categoria() {}

  public Categoria(Integer id, String descricao, String cor, Usuario usuario) {
    super(id, usuario);
    this.descricao = descricao;
    this.cor = cor;
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
}
