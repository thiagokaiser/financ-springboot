package com.kaiser.financ.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaiser.financ.domain.Notificacao;
import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.repositories.NotificacaoRepository;
import com.kaiser.financ.services.NotificacaoService;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {
    
    @Autowired
    private NotificacaoRepository repository;
        
    @Override
    public Notificacao insert(Usuario usuario) {
        Notificacao notificacao = new Notificacao();
        notificacao.setDescricao("teste");
        notificacao.setUsuario(usuario);
        
        return repository.save(notificacao);
    }

}
