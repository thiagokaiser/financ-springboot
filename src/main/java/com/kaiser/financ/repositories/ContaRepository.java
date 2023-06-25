package com.kaiser.financ.repositories;

import com.kaiser.financ.entities.ContaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ContaRepository extends CrudRepository<ContaEntity> {

  @Transactional(readOnly = true)
  Page<ContaEntity> findByUsuarioAndDescricaoContaining(
      UsuarioEntity usuario, String search, Pageable pageRequest);
}
