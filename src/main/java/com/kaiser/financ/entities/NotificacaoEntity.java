package com.kaiser.financ.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Notificacao")
public class NotificacaoEntity extends BaseEntity {
  @NotBlank(message = "Campo Obrigatário")
  private String descricao;

  private Date dtCriacao;
  private Date dtLeitura;
  private boolean lido;

  public NotificacaoEntity() {}

  public NotificacaoEntity(
      Integer id, String descricao, Date dtCriacao, Date dtLeitura, UsuarioEntity usuario, boolean lido) {
    super(id, usuario);
    this.descricao = descricao;
    this.dtCriacao = dtCriacao;
    this.dtLeitura = dtLeitura;
    this.lido = lido;
  }
  
}
