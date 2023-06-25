package com.kaiser.financ.repositories;

import com.kaiser.financ.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

  @Transactional(readOnly = true)
  UsuarioEntity findByEmail(String email);
}
