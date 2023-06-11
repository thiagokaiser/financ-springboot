package com.kaiser.financ.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notificacao extends Domain {
  @NotBlank(message = "Campo Obrigatário")
  private String descricao;

  private Date dtCriacao;
  private Date dtLeitura;
  private boolean lido;

  public Notificacao() {}

  public Notificacao(
      Integer id, String descricao, Date dtCriacao, Date dtLeitura, Usuario usuario, boolean lido) {
    super(id, usuario);
    this.descricao = descricao;
    this.dtCriacao = dtCriacao;
    this.dtLeitura = dtLeitura;
    this.lido = lido;
  }
  
}
