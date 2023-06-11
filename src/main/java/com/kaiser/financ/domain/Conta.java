package com.kaiser.financ.domain;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Conta extends Domain {
  private String descricao;

  public Conta() {
    super();
  }

  public Conta(Integer id, String descricao, Usuario usuario) {
    super(id, usuario);
    this.descricao = descricao;
  }

}
