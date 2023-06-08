package com.kaiser.financ.repositories;

import com.kaiser.financ.domain.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer>{

}
