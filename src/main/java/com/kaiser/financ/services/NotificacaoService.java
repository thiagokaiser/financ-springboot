package com.kaiser.financ.services;

import com.kaiser.financ.dtos.NotificacaoDTO;
import com.kaiser.financ.entities.NotificacaoEntity;

public interface NotificacaoService extends CrudService<NotificacaoEntity, NotificacaoDTO> {

  void marcarComoLido(Integer id);

  void insertNotificacao(NotificacaoEntity obj);

  long contarNaoLidas();
}
