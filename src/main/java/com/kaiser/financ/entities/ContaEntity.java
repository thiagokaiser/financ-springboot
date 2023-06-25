package com.kaiser.financ.entities;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Conta")
public class ContaEntity extends BaseEntity {
  private String descricao;

  public ContaEntity() {
    super();
  }

  public ContaEntity(Integer id, String descricao, UsuarioEntity usuario) {
    super(id, usuario);
    this.descricao = descricao;
  }

}
