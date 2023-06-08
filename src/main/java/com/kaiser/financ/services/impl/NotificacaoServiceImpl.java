package com.kaiser.financ.services.impl;

import com.kaiser.financ.domain.Notificacao;
import com.kaiser.financ.dto.NotificacaoDTO;
import com.kaiser.financ.repositories.NotificacaoRepository;
import com.kaiser.financ.services.NotificacaoService;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoServiceImpl
    extends CrudServiceImpl<Notificacao, NotificacaoRepository, NotificacaoDTO>
    implements NotificacaoService {

  @Override
  protected void updateData(Notificacao newObj, Notificacao obj) {
    // TODO
  }

  @Override
  public Notificacao fromDTO(NotificacaoDTO objDto) {
    return null; // TODO
  }

  @Override
  public NotificacaoDTO toDTO(Notificacao obj) {
    return null; // TODO
  }
}
