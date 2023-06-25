package com.kaiser.financ.repositories;

import com.kaiser.financ.entities.CategoriaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoriaRepository extends CrudRepository<CategoriaEntity> {

  @Transactional(readOnly = true)
  Page<CategoriaEntity> findByUsuarioAndDescricaoContaining(
      UsuarioEntity usuario, String descricao, Pageable pageRequest);
}
