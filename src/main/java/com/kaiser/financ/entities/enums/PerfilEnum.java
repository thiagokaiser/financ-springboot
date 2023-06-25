package com.kaiser.financ.entities.enums;

public enum PerfilEnum {
  ADMIN(1, "ROLE_ADMIN"),
  USER(2, "ROLE_USER");

  private final int cod;
  private final String descricao;

  PerfilEnum(int cod, String descricao) {
    this.cod = cod;
    this.descricao = descricao;
  }

  public static PerfilEnum toEnum(Integer cod) {

    if (cod == null) {
      return null;
    }

    for (PerfilEnum x : PerfilEnum.values()) {
      if (cod.equals(x.getCod())) {
        return x;
      }
    }

    throw new IllegalArgumentException("Id inválido: " + cod);
  }

  public int getCod() {
    return cod;
  }

  public String getDescricao() {
    return descricao;
  }
}
