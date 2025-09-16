package com.kaiser.financ.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
  protected static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  @NotNull(message = "Campo Obrigatário")
  private UsuarioEntity usuario;

  public BaseEntity() {}

  public BaseEntity(Integer id, UsuarioEntity usuario) {
    this.id = id;
    this.usuario = usuario;
  }
  

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    BaseEntity other = (BaseEntity) obj;
    if (id == null) {
      return other.id == null;
    } else return id.equals(other.id);
  }
}
