package com.kaiser.financ.dtos;

import com.kaiser.financ.services.validators.UsuarioInsert;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@UsuarioInsert
public class UsuarioNewDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotEmpty(message = "Campo Obrigatário")
  @Size(min = 1, max = 80, message = "Campo deve estar entre 1 e 80 caracteres")
  private String nome;

  @NotEmpty(message = "Campo Obrigatário")
  @Size(min = 1, max = 80, message = "Campo deve estar entre 1 e 80 caracteres")
  private String sobrenome;

  @NotEmpty(message = "Campo Obrigatário")
  @Email(message = "Email inválido")
  private String email;

  @NotEmpty(message = "Campo Obrigatário")
  @Size(min = 6, max = 20, message = "Campo deve estar entre 6 e 20 caracteres")
  private String senha;

  private String telefone1;
  private String telefone2;
  private String telefone3;
  
}
