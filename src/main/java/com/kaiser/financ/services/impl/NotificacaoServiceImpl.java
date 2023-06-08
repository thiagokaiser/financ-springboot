package com.kaiser.financ.services.impl;

import com.kaiser.financ.domain.Notificacao;
import com.kaiser.financ.dto.NotificacaoDTO;
import com.kaiser.financ.repositories.NotificacaoRepository;
import com.kaiser.financ.services.NotificacaoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {
    
    @Autowired
    private NotificacaoRepository repository;

	@Override
	public Notificacao insert(Notificacao obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notificacao find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notificacao fromDTO(@Valid NotificacaoDTO objDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Notificacao obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Notificacao> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Notificacao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction,
			String search) {
		// TODO Auto-generated method stub
		return null;
	}

}
