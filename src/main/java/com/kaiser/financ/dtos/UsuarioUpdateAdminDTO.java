package com.kaiser.financ.dtos;

import com.kaiser.financ.services.validators.UsuarioUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@UsuarioUpdate
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateAdminDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotEmpty(message = "Campo Obrigatário")
  @Email(message = "Email inválido")
  private String email;

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

  private String imagemPerfil;

}
