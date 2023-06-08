package com.kaiser.financ.dto;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.domain.Usuario;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
}
