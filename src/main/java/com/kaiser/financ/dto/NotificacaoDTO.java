package com.kaiser.financ.dto;

import com.kaiser.financ.domain.Notificacao;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificacaoDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer id;
  private String descricao;
  private Date dtCriacao;
  private Date dtLeitura;
  private UsuarioDTO usuario;
  private boolean lido;

  public NotificacaoDTO(Notificacao notificacao) {
    this.id = notificacao.getId();
    this.descricao = notificacao.getDescricao();
    this.dtCriacao = notificacao.getDtCriacao();
    this.dtLeitura = notificacao.getDtLeitura();
    this.lido = notificacao.isLido();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    NotificacaoDTO other = (NotificacaoDTO) obj;
    return Objects.equals(id, other.id);
  }
}
