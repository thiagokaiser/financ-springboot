package com.kaiser.financ.entities;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Categoria")
public class CategoriaEntity extends BaseEntity {
  private String descricao;
  private String cor;

  public CategoriaEntity() {}

  public CategoriaEntity(Integer id, String descricao, String cor, UsuarioEntity usuario) {
    super(id, usuario);
    this.descricao = descricao;
    this.cor = cor;
  }
}
