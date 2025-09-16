package com.kaiser.financ.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotEmpty(message = "Campo Obrigatário")
  @Size(min = 6, max = 20, message = "Campo deve estar entre 6 e 20 caracteres")
  private String newPassword;

  private String token;

}
