package com.kaiser.financ.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotEmpty(message = "Campo Obrigatário")
  @Size(min = 1, max = 80, message = "Campo deve estar entre 1 e 80 caracteres")
  private String nome;

  @NotEmpty(message = "Campo Obrigatário")
  @Size(min = 1, max = 80, message = "Campo deve estar entre 1 e 80 caracteres")
  private String sobrenome;

  private Date dtNascimento;

  @Size(max = 80, message = "Campo deve ter no máximo 30 caracteres")
  private String cidade;

  @Size(max = 80, message = "Campo deve ter no máximo 30 caracteres")
  private String estado;

  @Size(max = 80, message = "Campo deve ter no máximo 80 caracteres")
  private String descricao;
  
}
