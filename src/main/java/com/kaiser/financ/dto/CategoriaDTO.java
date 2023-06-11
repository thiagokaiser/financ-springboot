package com.kaiser.financ.dto;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.domain.Usuario;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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

  private Usuario usuario;

  public CategoriaDTO() {}

  public CategoriaDTO(Categoria obj) {
    this.id = obj.getId();
    this.descricao = obj.getDescricao();
    this.cor = obj.getCor();
    this.usuario = obj.getUsuario();
  }

}
