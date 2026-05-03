package com.kaiser.financ.repositories;

import com.kaiser.financ.entities.NotificacaoEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoRepository extends CrudRepository<NotificacaoEntity> {

  long countByUsuarioAndLidoFalse(UsuarioEntity usuario);
}
