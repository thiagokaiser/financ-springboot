package com.kaiser.financ.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotEmpty(message = "Campo Obrigatário")
  @Email(message = "Email inválido")
  private String email;

}
