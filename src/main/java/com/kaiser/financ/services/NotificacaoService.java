package com.kaiser.financ.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.kaiser.financ.domain.Notificacao;
import com.kaiser.financ.dto.NotificacaoDTO;

public interface NotificacaoService {

    Notificacao insert(Notificacao obj);

	Notificacao find(Integer id);

	Notificacao fromDTO(@Valid NotificacaoDTO objDto);

	void update(Notificacao obj);

	void delete(Integer id);

	List<Notificacao> findAll();

	Page<Notificacao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String search);

}
