package com.kaiser.financ.services.impl;

import com.kaiser.financ.dtos.NotificacaoDTO;
import com.kaiser.financ.entities.NotificacaoEntity;
import com.kaiser.financ.repositories.NotificacaoRepository;
import com.kaiser.financ.services.NotificacaoService;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoServiceImpl
    extends CrudServiceImpl<NotificacaoEntity, NotificacaoRepository, NotificacaoDTO>
    implements NotificacaoService {

  @Override
  protected void updateData(NotificacaoEntity savedObj, NotificacaoEntity newObj) {
    savedObj.setDescricao(newObj.getDescricao());
    savedObj.setLido(newObj.isLido());
    savedObj.setDtLeitura(newObj.getDtLeitura());
  }

  @Override
  public NotificacaoEntity fromDTO(NotificacaoDTO objDto) {
    return new NotificacaoEntity(
        objDto.getId(),
        objDto.getDescricao(),
        objDto.getDtCriacao(),
        objDto.getDtLeitura(),
        null,
        objDto.isLido());
  }

  @Override
  public NotificacaoDTO toDTO(NotificacaoEntity obj) {
    return new NotificacaoDTO(obj);
  }

  @Override
  public void marcarComoLido(Integer id) {
    NotificacaoEntity notificacao = find(id);
    notificacao.setLido(true);
    notificacao.setDtLeitura(new Date());
    repo.save(notificacao);
  }

  @Override
  public void insertNotificacao(NotificacaoEntity obj) {
    repo.save(obj);
  }

  @Override
  public long contarNaoLidas() {
    return repo.countByUsuarioAndLidoFalse(usuarioService.userLoggedIn());
  }
}
