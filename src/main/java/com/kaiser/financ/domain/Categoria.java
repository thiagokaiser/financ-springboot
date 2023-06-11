package com.kaiser.financ.domain;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Categoria extends Domain {
  private String descricao;
  private String cor;

  public Categoria() {}

  public Categoria(Integer id, String descricao, String cor, Usuario usuario) {
    super(id, usuario);
    this.descricao = descricao;
    this.cor = cor;
  }
}
