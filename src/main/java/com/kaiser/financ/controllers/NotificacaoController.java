package com.kaiser.financ.controllers;

import com.kaiser.financ.dtos.NotificacaoDTO;
import com.kaiser.financ.entities.NotificacaoEntity;
import com.kaiser.financ.services.NotificacaoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/notificacoes")
public class NotificacaoController
    extends CrudController<NotificacaoService, NotificacaoEntity, NotificacaoDTO> {}
