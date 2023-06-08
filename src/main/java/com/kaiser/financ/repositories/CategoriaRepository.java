package com.kaiser.financ.repositories;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria> {

  @Transactional(readOnly = true)
  Page<Categoria> findByUsuarioAndDescricaoContaining(
      Usuario usuario, String descricao, Pageable pageRequest);
}
