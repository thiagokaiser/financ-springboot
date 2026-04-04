package com.kaiser.financ.controllers;

import com.kaiser.financ.dtos.NotificacaoDTO;
import com.kaiser.financ.entities.NotificacaoEntity;
import com.kaiser.financ.jobs.NotificacaoJob;
import com.kaiser.financ.services.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/notificacoes")
public class NotificacaoController
    extends CrudController<NotificacaoService, NotificacaoEntity, NotificacaoDTO> {

  @Autowired
  private NotificacaoService notificacaoService;

  @Autowired
  private NotificacaoJob notificacaoJob;

  @PatchMapping("/{id}/lido")
  public ResponseEntity<Void> marcarComoLido(@PathVariable Integer id) {
    notificacaoService.marcarComoLido(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/executar-job")
  public ResponseEntity<Void> executarJob() {
    notificacaoJob.verificarDespesasPendentes();
    return ResponseEntity.noContent().build();
  }
}
