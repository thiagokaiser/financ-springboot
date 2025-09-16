package com.kaiser.financ.dtos;

import com.kaiser.financ.entities.CategoriaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer id;

  @NotEmpty(message = "Campo Obrigatário")
  @Size(min = 3, max = 80, message = "Campo deve estar entre 3 e 80 caracteres")
  private String descricao;

  private String cor;

  private UsuarioEntity usuario;

  public CategoriaDTO() {}

  public CategoriaDTO(CategoriaEntity obj) {
    this.id = obj.getId();
    this.descricao = obj.getDescricao();
    this.cor = obj.getCor();
    this.usuario = obj.getUsuario();
  }

}
