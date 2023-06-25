package com.kaiser.financ.services.impl;

import com.kaiser.financ.dtos.NotificacaoDTO;
import com.kaiser.financ.entities.NotificacaoEntity;
import com.kaiser.financ.repositories.NotificacaoRepository;
import com.kaiser.financ.services.NotificacaoService;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoServiceImpl
    extends CrudServiceImpl<NotificacaoEntity, NotificacaoRepository, NotificacaoDTO>
    implements NotificacaoService {

  @Override
  protected void updateData(NotificacaoEntity newObj, NotificacaoEntity obj) {
    // TODO
  }

  @Override
  public NotificacaoEntity fromDTO(NotificacaoDTO objDto) {
    return null; // TODO
  }

  @Override
  public NotificacaoDTO toDTO(NotificacaoEntity obj) {
    return null; // TODO
  }
}
