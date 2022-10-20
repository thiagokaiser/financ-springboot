package com.kaiser.financ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaiser.financ.domain.Notificacao;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer>{

}
