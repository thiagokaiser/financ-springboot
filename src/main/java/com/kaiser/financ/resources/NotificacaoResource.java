package com.kaiser.financ.resources;

import com.kaiser.financ.domain.Notificacao;
import com.kaiser.financ.dto.NotificacaoDTO;
import com.kaiser.financ.services.NotificacaoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/notificacoes")
public class NotificacaoResource extends CrudResource<NotificacaoService, Notificacao, NotificacaoDTO> {
	
}
