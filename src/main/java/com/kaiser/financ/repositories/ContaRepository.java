package com.kaiser.financ.repositories;

import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ContaRepository extends CrudRepository<Conta> {

  @Transactional(readOnly = true)
  Page<Conta> findByUsuarioAndDescricaoContaining(
      Usuario usuario, String search, Pageable pageRequest);
}
